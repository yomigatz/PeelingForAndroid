package com.yomigatz.peeling.peeling.skinapplicator;

import android.content.res.TypedArray;
import android.graphics.Color;

import com.zxy.skin.demo.widget.CustomView;
import com.zxy.skin.sdk.applicator.SkinViewApplicator;

public class SkinCustomViewApplicator extends SkinViewApplicator {

    public SkinCustomViewApplicator() {
        super();
        addAttributeApplicator("lineColor", new IAttributeApplicator<CustomView>() {
            @Override
            public void onApply(CustomView view, TypedArray typedArray, int typedArrayIndex) {
                view.setLineColor(typedArray.getColor(typedArrayIndex, Color.BLACK));
            }
        });
    }
}
