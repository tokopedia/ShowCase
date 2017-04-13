package com.tokopedia.showcase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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

    public static Bitmap getCroppedBitmap(Bitmap bitmap, int centerLocation[], int radius) {
        Bitmap output = Bitmap.createBitmap(2*radius,
                2*radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        Rect sourceRect = new Rect(centerLocation[0] - radius,
                centerLocation[1] - radius,
                centerLocation[0] + radius,
                centerLocation[1] + radius);
        Rect destRect = new Rect(0,0,2*radius, 2*radius);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(radius, radius,
                radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, sourceRect, destRect, paint);
        return output;
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = context.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }
}
