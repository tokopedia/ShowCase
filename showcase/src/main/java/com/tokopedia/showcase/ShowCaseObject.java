package com.tokopedia.showcase;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hendry on 4/13/2017.
 */

public class ShowCaseObject {

    private View view;
    protected String title;
    protected String text;
    protected ShowCaseContentPosition showCaseContentPosition;
    protected int tintBackgroundColor;
    private ViewGroup scrollView;

    public ShowCaseObject(@Nullable View view, @Nullable String title, String text ) {
        this(view, title, text, ShowCaseContentPosition.UNDEFINED);
    }
    public ShowCaseObject(@Nullable View view, @Nullable String title,
                          String text, ShowCaseContentPosition showCaseContentPosition) {
        this(view, title, text, showCaseContentPosition, 0);
    }

    public ShowCaseObject(@Nullable View view, @Nullable String title,
                          String text, ShowCaseContentPosition showCaseContentPosition,
                          int tintBackgroundColor) {
        this(view, title, text, showCaseContentPosition, tintBackgroundColor, null);
    }
    public ShowCaseObject(@Nullable View view, @Nullable String title,
                          String text, ShowCaseContentPosition showCaseContentPosition,
                          int tintBackgroundColor, ViewGroup scrollView) {
        this.view = view;
        this.title = title;
        this.text = text;
        this.showCaseContentPosition = showCaseContentPosition;
        this.tintBackgroundColor = tintBackgroundColor;
        this.scrollView = scrollView;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getTintBackgroundColor() {
        return tintBackgroundColor;
    }

    public ShowCaseContentPosition getShowCaseContentPosition() {
        return showCaseContentPosition;
    }

    private int[] location;
    private int radius;
    public ShowCaseObject withCustomTarget(int[] location, int radius){
        this.location = location;
        this.radius = radius;
        return this;
    }

    public int[] getLocation() {
        return location;
    }

    public int getRadius() {
        return radius;
    }

    public View getView() {
        return view;
    }
    public ViewGroup getScrollView() {
        return scrollView;
    }
}