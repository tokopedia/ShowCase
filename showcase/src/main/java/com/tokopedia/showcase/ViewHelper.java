package com.tokopedia.showcase;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by Hendry on 4/13/2017.
 */

public class ViewHelper {
    static public void getRelativePositionRec(View myView, ViewParent root, int[] location) {
        if (myView.getParent() == root) {
            location[0] += myView.getLeft();
            location[1] += myView.getTop();
        } else {
            location[0] += myView.getLeft();
            location[1] += myView.getTop();
            getRelativePositionRec((View) myView.getParent(), root, location);
        }
    }

    public static void setBackgroundColor(View v, int color){
        Drawable background = v.getBackground();
        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable)background;
            shapeDrawable.getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable)background;
            gradientDrawable.setColor(color);
        }
    }
}
