package com.zxy.skin;

import android.app.Application;

import com.zxy.skin.skinapplicator.SkinCustomViewApplicator;
import com.zxy.skin.widget.CustomView;
import com.zxy.skin.sdk.SkinEngine;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.changeSkin(R.style.AppTheme);
        SkinEngine.registerSkinApplicator(CustomView.class, new SkinCustomViewApplicator());
    }
}
