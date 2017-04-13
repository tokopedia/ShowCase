package com.tokopedia.showcase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ShowCaseLayout extends FrameLayout {

    // customized attribute
    private int layoutRes;
    private int textColor;
    private int shadowColor;
    private float textSize;
    private float textTitleSize;
    private int spacing;
    private int arrowMargin;
    private int arrowWidth;
    private boolean useCircleIndicator;

    private boolean isCancelable;

    private String prevString;
    private String nextString;
    private String finishString;

    private int backgroundContentColor;
    private int circleBackgroundDrawableRes;

    // View
    private ViewGroup viewGroup;
    private Bitmap bitmap;
    private View lastTutorialView;
    private Paint viewPaint;

    // listener
    private ShowCaseListener showCaseListener;

    ShowCaseContentPosition showCaseContentPosition;

    private int highlightLocX;
    private int highlightLocY;

    // determined if this is last chain
    private boolean isLast;

    // path for arrow
    private Path path;
    private Paint arrowPaint;
    private TextView textViewTitle;
    private TextView textViewDesc;
    private TextView prevButton;
    private TextView nextButton;
    private ViewGroup viewGroupIndicator;

    public ShowCaseLayout(Context context, @Nullable ShowCaseBuilder builder) {
        super(context);
        init(context, builder);
    }

    public ShowCaseLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ShowCaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ShowCaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public ShowCaseLayout(Context context,
                          AttributeSet attrs,
                          int defStyleAttr,
                          int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, null);
    }

    private void init(Context context, @Nullable ShowCaseBuilder builder) {
        setVisibility(View.GONE);

        if (isInEditMode()) {
            return;
        }

        applyAttrs(context, builder);

        //setBackground, color
        initFrame();

        // setContentView
        initContent(context);

        setClickable(this.isCancelable);
        setFocusable(this.isCancelable);

        if (this.isCancelable) {
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNextClicked();
                }
            });
        }
    }

    private void onNextClicked(){
        if (showCaseListener != null) {
            if (this.isLast) {
                ShowCaseLayout.this.showCaseListener.onComplete();
            }
            else {
                ShowCaseLayout.this.showCaseListener.onNext();
            }
        }
    }

    private void initFrame() {
        setWillNotDraw(false);

        viewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        viewPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setColor(backgroundContentColor);
        arrowPaint.setStyle(Paint.Style.FILL);

        setBackgroundColor(shadowColor);
    }

    public void setShowCaseListener(ShowCaseListener showCaseListener) {
        this.showCaseListener = showCaseListener;
    }

    public void showTutorial(View view,
                             String title,
                             String text,
                             int currentTutorIndex,
                             int tutorsListSize,
                             ShowCaseContentPosition showCaseContentPosition,
                             int tintBackgroundColor) {
        boolean isStart = currentTutorIndex == 0;

        this.isLast = currentTutorIndex == tutorsListSize - 1;
        this.showCaseContentPosition = showCaseContentPosition;

        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        if (this.lastTutorialView != null) {
            this.lastTutorialView.setDrawingCacheEnabled(false);
        }

        if (TextUtils.isEmpty(title)) {
            textViewTitle.setVisibility(View.GONE);
        }
        else {
            textViewTitle.setText(Html.fromHtml(title));
            textViewTitle.setVisibility(View.VISIBLE);
        }

        textViewDesc.setText(Html.fromHtml(text));

        if (prevButton!= null) {
            if (isStart) {
                prevButton.setVisibility(View.INVISIBLE);
            } else {
                prevButton.setVisibility(View.VISIBLE);
            }
        }

        if (nextButton!= null) {
            if (isLast) {
                nextButton.setText(finishString);
            }
            else if (currentTutorIndex < tutorsListSize - 1) { // has next
                nextButton.setText(nextString);
            }
        }

        makeCircleIndicator(!isStart || !isLast, currentTutorIndex, tutorsListSize);

        if (view == null) {
            this.lastTutorialView = null;
            this.bitmap = null;
            this.highlightLocX = 0;
            this.highlightLocY = 0;
            moveViewToCenter();
        }
        else {
            final int[] location = new int[2];
            this.lastTutorialView = view;
            view.getLocationInWindow(location);

            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
            if (tintBackgroundColor == 0) {
                this.bitmap = view.getDrawingCache();
            }
            else {
                Bitmap bitmapTemp = view.getDrawingCache();

                Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                        view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas bigCanvas = new Canvas(bigBitmap);
                bigCanvas.drawColor(tintBackgroundColor);
                Paint paint = new Paint();
                bigCanvas.drawBitmap(bitmapTemp, 0f, 0f, paint);

                this.bitmap = bigBitmap;
            }

            this.highlightLocX = location[0];
            this.highlightLocY = location[1] - getStatusBarHeight();

            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    moveViewBasedHighlight( ShowCaseLayout.this.highlightLocX,
                            ShowCaseLayout.this.highlightLocY,
                            ShowCaseLayout.this.highlightLocX + ShowCaseLayout.this.bitmap.getWidth(),
                            ShowCaseLayout.this.highlightLocY + ShowCaseLayout.this.bitmap.getHeight());

                    if (Build.VERSION.SDK_INT < 16) {
                        ShowCaseLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        ShowCaseLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    postInvalidate();
                }
            });
        }

        this.setVisibility(View.VISIBLE);
    }

    public void hideTutorial(){
        this.setVisibility(View.INVISIBLE);
    }

    private void makeCircleIndicator(boolean hasMoreOneCircle,
                                     int currentTutorIndex,
                                     int tutorsListSize){
        if (useCircleIndicator && this.viewGroupIndicator!= null) {
            if (hasMoreOneCircle) { // has more than 1 circle
                // already has circle indicator
                if (this.viewGroupIndicator.getChildCount() == tutorsListSize) {
                    for (int i = 0; i < tutorsListSize; i++) {
                        View viewCircle = this.viewGroupIndicator.getChildAt(i);
                        if (i == currentTutorIndex) {
                            viewCircle.setSelected(true);
                        }
                        else {
                            viewCircle.setSelected(false);
                        }
                    }
                }
                else { //reinitialize, the size is different
                    this.viewGroupIndicator.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    for (int i = 0; i < tutorsListSize; i++) {
                        View viewCircle = inflater.inflate(R.layout.circle_green_view,
                                            viewGroupIndicator,
                                            false);
                        viewCircle.setBackgroundResource(this.circleBackgroundDrawableRes);
                        if (i == currentTutorIndex) {
                            viewCircle.setSelected(true);
                        }
                        this.viewGroupIndicator.addView(viewCircle);
                    }
                }
            }
            else {
                this.viewGroupIndicator.removeAllViews();
            }
        }
    }

    public void closeTutorial() {
        setVisibility(View.GONE);
        if (this.bitmap != null) {
            this.bitmap.recycle();
            this.bitmap = null;
        }
        if (lastTutorialView != null) {
            this.lastTutorialView.setDrawingCacheEnabled(false);
            this.lastTutorialView = null;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recycleResources();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            return;
        }

        canvas.drawBitmap(this.bitmap, this.highlightLocX, this.highlightLocY, viewPaint);

        // drawArrow
        if (path != null) {
            canvas.drawPath(path, arrowPaint);
        }
    }

    private void applyAttrs(Context context, @Nullable ShowCaseBuilder builder) {
        // set Default value before check builder (might be null)
        this.layoutRes = R.layout.tutorial_view;

        this.textColor = Color.WHITE;
        this.textTitleSize = getResources().getDimension(R.dimen.text_title);
        this.textSize = getResources().getDimension(R.dimen.text_normal);

        this.shadowColor = ContextCompat.getColor(context, R.color.shadow);
        this.spacing = (int) getResources().getDimension(R.dimen.spacing_normal);
        this.arrowMargin = this.spacing / 3;
        this.arrowWidth = 2* this.spacing;

        this.backgroundContentColor = Color.BLACK;
        this.circleBackgroundDrawableRes = R.drawable.selector_circle_green;

        this.prevString = getContext().getString(R.string.previous);
        this.nextString = getContext().getString(R.string.next);
        this.finishString = getContext().getString(R.string.finish);

        if (builder == null) {
            return;
        }

        this.layoutRes = builder.getLayoutRes() != 0 ?
                builder.getLayoutRes()
                : this.layoutRes;

        this.textColor = builder.getTextColorRes() != 0 ?
                ContextCompat.getColor(context, builder.getTextColorRes())
                : this.textColor;

        this.textTitleSize = builder.getTitleTextSizeRes() != 0 ?
                getResources().getDimension(builder.getTitleTextSizeRes())
                : this.textTitleSize;

        this.textSize = builder.getTextSizeRes() != 0 ?
                getResources().getDimension(builder.getTextSizeRes())
                : this.textSize;

        this.backgroundContentColor = builder.getBackgroundContentColorRes() != 0 ?
                ContextCompat.getColor(context, builder.getBackgroundContentColorRes())
                : this.backgroundContentColor;

        this.shadowColor = builder.getShadowColorRes() != 0 ?
                ContextCompat.getColor(context, builder.getShadowColorRes()) :
                this.shadowColor;

        this.spacing = builder.getSpacingRes() != 0 ?
                (int) getResources().getDimension(builder.getSpacingRes())
                : this.spacing;

        this.circleBackgroundDrawableRes = builder.getCircleIndicatorBackgroundDrawableRes() != 0 ?
                builder.getCircleIndicatorBackgroundDrawableRes()
                : this.circleBackgroundDrawableRes;

        this.prevString = builder.getPrevStringRes() != 0 ?
                getContext().getString(builder.getPrevStringRes())
                : this.prevString;

        this.nextString = builder.getNextStringRes() != 0 ?
                getContext().getString(builder.getNextStringRes())
                : this.nextString;

        this.finishString = builder.getFinishStringRes() != 0 ?
                getContext().getString(builder.getFinishStringRes())
                : this.finishString;

        this.useCircleIndicator = builder.useCircleIndicator();

        this.isCancelable = builder.isClickable();
    }

    private void initContent(Context context) {
        this.viewGroup = (ViewGroup)
                LayoutInflater.from(context).inflate(this.layoutRes, this, false);

        View viewGroupTutorContent = viewGroup.findViewById(R.id.view_group_tutor_content);
        ViewHelper.setBackgroundColor(viewGroupTutorContent, this.backgroundContentColor);

        textViewTitle = (TextView) viewGroupTutorContent.findViewById(R.id.text_title);

        textViewTitle = (TextView) viewGroupTutorContent.findViewById(R.id.text_title);
        textViewTitle.setTextColor(this.textColor);
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textTitleSize);

        textViewDesc = (TextView) viewGroupTutorContent.findViewById(R.id.text_description);
        textViewDesc.setTextColor(this.textColor);
        textViewDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize);

        View line = viewGroupTutorContent.findViewById(R.id.view_line);
        line.setBackgroundColor(textColor);

        prevButton = (TextView) viewGroupTutorContent.findViewById(R.id.text_previous);
        nextButton = (TextView) viewGroupTutorContent.findViewById(R.id.text_next);

        viewGroupIndicator = (ViewGroup) viewGroupTutorContent.findViewById(R.id.view_group_indicator);

        if (prevButton!=null) {
            prevButton.setText(prevString);
            prevButton.setTextColor(this.textColor);
            prevButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize);
            prevButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showCaseListener != null) {
                        ShowCaseLayout.this.showCaseListener.onPrevious();
                    }
                }
            });
        }
        if (nextButton!=null) {
            nextButton.setTextColor(this.textColor);
            nextButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize);
            nextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNextClicked();
                }
            });
        }

        this.addView(viewGroup);
    }

    private void moveViewBasedHighlight(int highlightXstart,
                                        int highlightYstart,
                                        int highlightXend,
                                        int highlightYend) {
        if (showCaseContentPosition == ShowCaseContentPosition.UNDEFINED) {
            int widthCenter = this.getWidth() / 2;
            int heightCenter = this.getHeight() / 2;
            if (highlightYend <= heightCenter) {
                showCaseContentPosition = ShowCaseContentPosition.BOTTOM;
            } else if (highlightYstart >= heightCenter) {
                showCaseContentPosition = ShowCaseContentPosition.TOP;
            } else if (highlightXend <= widthCenter) {
                showCaseContentPosition = ShowCaseContentPosition.RIGHT;
            } else if (highlightXstart >= widthCenter) {
                showCaseContentPosition = ShowCaseContentPosition.LEFT;
            } else { // not fit anywhere
                // if bottom is bigger, put to bottom, else put it on top
                if ((this.getHeight() - highlightYend) > highlightYstart) {
                    showCaseContentPosition = ShowCaseContentPosition.BOTTOM;
                }
                else {
                    showCaseContentPosition = ShowCaseContentPosition.TOP;
                }
            }
        }

        LayoutParams layoutParams;
        switch (showCaseContentPosition) {
            case RIGHT: {
                int expectedWidth = getWidth() - highlightXend - 2 * this.spacing;

                viewGroup.measure(MeasureSpec.makeMeasureSpec(expectedWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int viewGroupHeight = viewGroup.getMeasuredHeight();

                layoutParams = new LayoutParams(
                        expectedWidth,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.RIGHT);
                layoutParams.rightMargin = this.spacing;
                layoutParams.leftMargin = this.spacing;
                layoutParams.bottomMargin = 0;

                // calculate diff top height between object and the content;
                int hightLightHeight = (highlightYend - highlightYstart);
                int diffHeight = hightLightHeight - viewGroupHeight;

                // check top margin. top should not out of window
                int expectedTopMargin = highlightYstart + diffHeight / 2;
                checkMarginTopBottom(expectedTopMargin, layoutParams, viewGroupHeight);

                this.viewGroup.setLayoutParams(layoutParams);

                int highLightCenterY = (highlightYend + highlightYstart)/2;
                path = new Path();
                path.moveTo(highlightXend + this.arrowMargin, highLightCenterY);
                path.lineTo(highlightXend+this.spacing + this.arrowMargin,
                        highLightCenterY - arrowWidth / 2);
                path.lineTo(highlightXend+this.spacing + this.arrowMargin,
                        highLightCenterY + arrowWidth / 2);
                path.close();
            }
            break;
            case LEFT: {
                int expectedWidth = highlightXstart - 2 * this.spacing;

                viewGroup.measure(MeasureSpec.makeMeasureSpec(expectedWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int viewGroupHeight = viewGroup.getMeasuredHeight();

                layoutParams = new LayoutParams(
                        expectedWidth,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.LEFT);
                layoutParams.leftMargin = this.spacing;
                layoutParams.rightMargin = this.spacing;
                layoutParams.bottomMargin = 0;

                // calculate diff top height between object and the content;
                int hightLightHeight = (highlightYend - highlightYstart);
                int diffHeight = hightLightHeight - viewGroupHeight;

                // check top margin. top should not out of window
                int expectedTopMargin = highlightYstart + diffHeight / 2;
                checkMarginTopBottom(expectedTopMargin, layoutParams, viewGroupHeight);

                this.viewGroup.setLayoutParams(layoutParams);

                int highLightCenterY = (highlightYend + highlightYstart)/2;
                path = new Path();
                path.moveTo(highlightXstart - this.arrowMargin, highLightCenterY);
                path.lineTo(highlightXstart - this.spacing - this.arrowMargin,
                        highLightCenterY - arrowWidth / 2);
                path.lineTo(highlightXstart - this.spacing - this.arrowMargin,
                        highLightCenterY + arrowWidth / 2);
                path.close();
            }
            break;
            case BOTTOM: {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.TOP);
                layoutParams.topMargin = highlightYend + this.spacing;
                layoutParams.leftMargin = this.spacing;
                layoutParams.rightMargin = this.spacing;
                layoutParams.bottomMargin = 0;

                this.viewGroup.setLayoutParams(layoutParams);

                int highLightCenterX = (highlightXend + highlightXstart) / 2;
                path = new Path();
                path.moveTo(highLightCenterX, highlightYend + this.arrowMargin);
                path.lineTo(highLightCenterX - arrowWidth / 2,
                        highlightYend + this.spacing + this.arrowMargin);
                path.lineTo(highLightCenterX + arrowWidth / 2,
                        highlightYend + this.spacing + this.arrowMargin);
                path.close();
            }
            break;
            case TOP: {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);
                layoutParams.bottomMargin = getHeight() - highlightYstart + this.spacing;
                layoutParams.topMargin = 0;
                layoutParams.leftMargin = this.spacing;
                layoutParams.rightMargin = this.spacing;

                this.viewGroup.setLayoutParams(layoutParams);

                int highLightCenterX = (highlightXend + highlightXstart) / 2;
                path = new Path();
                path.moveTo(highLightCenterX, highlightYstart - this.arrowMargin);
                path.lineTo(highLightCenterX - arrowWidth / 2,
                        highlightYstart - this.spacing - this.arrowMargin);
                path.lineTo(highLightCenterX + arrowWidth / 2,
                        highlightYstart - this.spacing - this.arrowMargin);
                path.close();
            }
            break;
            case UNDEFINED:
                moveViewToCenter();
                break;
        }
    }

    private void moveViewToCenter() {
        showCaseContentPosition = ShowCaseContentPosition.UNDEFINED;

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        layoutParams.rightMargin = this.spacing;
        layoutParams.leftMargin = this.spacing;
        layoutParams.bottomMargin = this.spacing;
        layoutParams.topMargin = this.spacing;

        this.viewGroup.setLayoutParams(layoutParams);

        this.path = null;
    }

    private void checkMarginTopBottom(int expectedTopMargin,
                                      LayoutParams layoutParams,
                                      int viewHeight){
        if (expectedTopMargin < this.spacing) {
            layoutParams.topMargin = this.spacing;
        }
        else {
            // check bottom margin. bottom should not out of window
            int prevActualHeight = expectedTopMargin + viewHeight + this.spacing;
            if (prevActualHeight > getHeight()) {
                int diff = prevActualHeight - getHeight();
                layoutParams.topMargin = expectedTopMargin - diff;
            }
            else {
                layoutParams.topMargin = expectedTopMargin;
            }
        }
    }

    private void recycleResources() {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
        if (this.lastTutorialView != null) {
            this.lastTutorialView.setDrawingCacheEnabled(false);
        }
        this.lastTutorialView = null;
        this.viewPaint = null;
    }

    private int getStatusBarHeight() {
        int height = 0;
        int resId = this.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = this.getContext().getResources().getDimensionPixelSize(resId);
        }
        return height;
    }
}