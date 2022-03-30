package com.zxy.skin.sdk;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


/**
 * @Description: 使用方可以继承该Activity，或者将内部代码拷贝到自定义的Activity
 * @author: zhaoxuyang
 * @Date: 2019/1/31
 */
public class SkinActivity extends AppCompatActivity {

    private SkinLayoutInflater mLayoutInfalter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        mLayoutInfalter.applyCurrentSkin();
        // AppCompatActivity 需要设置
        AppCompatDelegate delegate = this.getDelegate();
        if (delegate instanceof LayoutInflater.Factory2) {
            mLayoutInfalter.setFactory2((LayoutInflater.Factory2) delegate);
        }

        // 自己的逻辑
    }


    @Override
    public final LayoutInflater getLayoutInflater() {
        if (mLayoutInfalter == null) {
            mLayoutInfalter = new SkinLayoutInflater(this);
        }
        return mLayoutInfalter;
    }

    @Override
    public final Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            return getLayoutInflater();
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutInfalter.destory();
    }
}
