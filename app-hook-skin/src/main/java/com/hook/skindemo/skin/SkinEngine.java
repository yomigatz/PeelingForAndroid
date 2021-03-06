package com.hook.skindemo.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Method;

public class SkinEngine {

    //单例
    private final static SkinEngine instance = new SkinEngine();

    public static SkinEngine getInstance() {
        return instance;
    }

    private SkinEngine() {
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        //使用application的目的是，如果万一传进来的是Activity对象
        //那么它被静态对象instance所持有，这个Activity就无法释放了
    }

    private Resources mOutResource;// TODO: 资源管理器
    private Context mContext;//上下文
    private String mOutPkgName;// TODO: 外部资源包的packageName

    /**
     * TODO: 加载外部资源包
     */
    public void load(final String path) {//path 是外部传入的apk文件名
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        //取得PackageManager引用
        PackageManager mPm = mContext.getPackageManager();
        //“检索在包归档文件中定义的应用程序包的总体信息”，说人话，外界传入了一个apk的文件路径，这个方法，拿到这个apk的包信息,这个包信息包含什么？
        PackageInfo mInfo = mPm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        mOutPkgName = mInfo.packageName;//先把包名存起来
        AssetManager assetManager;//资源管理器
        try {
            //TODO: 关键技术点3 通过反射获取AssetManager 用来加载外面的资源包
            assetManager = AssetManager.class.newInstance();//反射创建AssetManager对象，为何要反射？使用反射，是因为他这个类内部的addAssetPath方法是hide状态
            //addAssetPath方法可以加载外部的资源包
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);//为什么要反射执行这个方法？因为它是hide的,不直接对外开放，只能反射调用
            addAssetPath.invoke(assetManager, path);//反射执行方法
            mOutResource = new Resources(assetManager,//参数1，资源管理器
                    mContext.getResources().getDisplayMetrics(),//这个好像是屏幕参数
                    mContext.getResources().getConfiguration());//资源配置
            //最终创建出一个 "外部资源包"mOutResource ,它的存在，就是要让我们的app有能力加载外部的资源文件
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 提供外部资源包里面的颜色
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        if (mOutResource == null) {
            return resId;
        }
        String resName = mOutResource.getResourceEntryName(resId);
        int outResId = mOutResource.getIdentifier(resName, "color", mOutPkgName);
        if (outResId == 0) {
            return resId;
        }
        return mOutResource.getColor(outResId);
    }

    /**
     * 提供外部资源包里的图片资源
     * @param resId
     * @return
     */
    public Drawable getDrawable(int resId) {//获取图片
        if (mOutResource == null) {
            return ContextCompat.getDrawable(mContext, resId);
        }
        String resName = mOutResource.getResourceEntryName(resId);
        int outResId = mOutResource.getIdentifier(resName, "drawable", mOutPkgName);
        if (outResId == 0) {
            return ContextCompat.getDrawable(mContext, resId);
        }
        return mOutResource.getDrawable(outResId);
    }

    //..... 这里还可以提供外部资源包里的String，font等等等，只不过要手动写代码来实现getXX方法
}
