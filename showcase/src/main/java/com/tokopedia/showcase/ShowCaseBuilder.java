package com.tokopedia.showcase;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowCaseBuilder implements Parcelable {

    private int layoutRes;
    private int titleTextColorRes;
    private int textColorRes;
    private int shadowColorRes;
    private int titleTextSizeRes;
    private int textSizeRes;
    private int spacingRes;
    private int backgroundContentColorRes;
    private int circleIndicatorBackgroundDrawableRes;
    private int prevStringRes;
    private int nextStringRes;
    private int finishStringRes;
    private boolean useCircleIndicator = true;
    private boolean clickable = false;
    private boolean useArrow = true;

    public ShowCaseBuilder customView(int customViewRes) {
        this.layoutRes = customViewRes;
        return this;
    }

    public ShowCaseBuilder textColorRes(int textColorRes) {
        this.textColorRes = textColorRes;
        return this;
    }

    public ShowCaseBuilder titleTextColorRes(int titleTextColorRes) {
        this.titleTextColorRes = titleTextColorRes;
        return this;
    }

    public ShowCaseBuilder shadowColorRes(int shadowColorRes) {
        this.shadowColorRes = shadowColorRes;
        return this;
    }

    public ShowCaseBuilder useArrow(boolean useArrow) {
        this.useArrow = useArrow;
        return this;
    }

    public ShowCaseBuilder textSizeRes(int textSizeRes) {
        this.textSizeRes = textSizeRes;
        return this;
    }

    public ShowCaseBuilder titleTextSizeRes(int titleTextSizeRes) {
        this.titleTextSizeRes = titleTextSizeRes;
        return this;
    }

    public ShowCaseBuilder spacingRes(int spacingRes) {
        this.spacingRes = spacingRes;
        return this;
    }

    public ShowCaseBuilder backgroundContentColorRes(int backgroundContentColorRes) {
        this.backgroundContentColorRes = backgroundContentColorRes;
        return this;
    }

    public ShowCaseBuilder circleIndicatorBackgroundDrawableRes(int circleIndicatorBackgroundDrawableRes) {
        this.circleIndicatorBackgroundDrawableRes = circleIndicatorBackgroundDrawableRes;
        return this;
    }

    public ShowCaseBuilder finishStringRes(int finishStringRes) {
        this.finishStringRes = finishStringRes;
        return this;
    }

    public ShowCaseBuilder prevStringRes(int prevStringRes) {
        this.prevStringRes = prevStringRes;
        return this;
    }

    public ShowCaseBuilder nextStringRes(int nextStringRes) {
        this.nextStringRes = nextStringRes;
        return this;
    }


    public ShowCaseBuilder clickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public ShowCaseBuilder useCircleIndicator(boolean useCircleIndicator) {
        this.useCircleIndicator = useCircleIndicator;
        return this;
    }

    public int getTextColorRes() {
        return textColorRes;
    }

    public int getTitleTextColorRes() {
        return titleTextColorRes;
    }

    public int getTitleTextSizeRes() {
        return titleTextSizeRes;
    }

    public int getFinishStringRes() {
        return finishStringRes;
    }

    public int getNextStringRes() {
        return nextStringRes;
    }

    public int getPrevStringRes() {
        return prevStringRes;
    }

    public boolean useCircleIndicator() {
        return useCircleIndicator;
    }

    public int getShadowColorRes() {
        return shadowColorRes;
    }

    public int getTextSizeRes() {
        return textSizeRes;
    }

    public int getBackgroundContentColorRes() {
        return backgroundContentColorRes;
    }

    public int getCircleIndicatorBackgroundDrawableRes() {
        return circleIndicatorBackgroundDrawableRes;
    }

    public boolean isUseArrow() {
        return useArrow;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public int getSpacingRes() {
        return spacingRes;
    }

    public boolean isClickable() {
        return clickable;
    }

    public ShowCaseDialog build() {
        return ShowCaseDialog.newInstance(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.layoutRes);
        dest.writeInt(this.textColorRes);
        dest.writeInt(this.shadowColorRes);
        dest.writeInt(this.titleTextSizeRes);
        dest.writeInt(this.textSizeRes);
        dest.writeInt(this.spacingRes);
        dest.writeInt(this.backgroundContentColorRes);
        dest.writeInt(this.circleIndicatorBackgroundDrawableRes);
        dest.writeInt(this.prevStringRes);
        dest.writeInt(this.nextStringRes);
        dest.writeInt(this.finishStringRes);
        dest.writeByte(this.useCircleIndicator ? (byte) 1 : (byte) 0);
        dest.writeByte(this.clickable ? (byte) 1 : (byte) 0);
    }

    public ShowCaseBuilder() {
    }

    protected ShowCaseBuilder(Parcel in) {
        this.layoutRes = in.readInt();
        this.textColorRes = in.readInt();
        this.shadowColorRes = in.readInt();
        this.titleTextSizeRes = in.readInt();
        this.textSizeRes = in.readInt();
        this.spacingRes = in.readInt();
        this.backgroundContentColorRes = in.readInt();
        this.circleIndicatorBackgroundDrawableRes = in.readInt();
        this.prevStringRes = in.readInt();
        this.nextStringRes = in.readInt();
        this.finishStringRes = in.readInt();
        this.useCircleIndicator = in.readByte() != 0;
        this.clickable = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ShowCaseBuilder> CREATOR = new Parcelable.Creator<ShowCaseBuilder>() {
        @Override
        public ShowCaseBuilder createFromParcel(Parcel source) {
            return new ShowCaseBuilder(source);
        }

        @Override
        public ShowCaseBuilder[] newArray(int size) {
            return new ShowCaseBuilder[size];
        }
    };
}
