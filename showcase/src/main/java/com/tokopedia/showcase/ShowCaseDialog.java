package com.tokopedia.showcase;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;

import java.util.ArrayList;

public class ShowCaseDialog extends DialogFragment {
    private static final String ARG_BUILDER = "BUILDER";
    public static final int DELAY_SCROLLING = 500;
    public static final String TAG = ShowCaseDialog.class.getSimpleName();

    private ArrayList<ShowCaseObject> tutorsList;
    private int currentTutorIndex = -1;
    private ShowCaseBuilder builder;
    private String tag;

    boolean hasViewGroupHandled = false;

    private OnShowCaseStepListener listener;
    public interface OnShowCaseStepListener{
        /**
         * @param previousStep
         * @param nextStep
         * @param showCaseObject
         * @return true if already fully handled show case step inthis function
         */
        boolean onShowCaseGoTo(int previousStep, int nextStep, ShowCaseObject showCaseObject);
    }

    public void setShowCaseStepListener(OnShowCaseStepListener listener) {
        this.listener = listener;
    }

    static ShowCaseDialog newInstance(ShowCaseBuilder builder) {
        final Bundle args = new Bundle();
        final ShowCaseDialog fragment = new ShowCaseDialog();
        args.putParcelable(ARG_BUILDER, builder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs(getArguments());
        setRetainInstance(true);
    }

    private void getArgs(Bundle args) {
        builder = (ShowCaseBuilder) args.get(ARG_BUILDER);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (builder.isClickable()) {
                    previous();
                }
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = new ShowCaseLayout(getActivity(), builder);
        initViews(((ShowCaseLayout) view));
        return view;
    }

    private void initViews(ShowCaseLayout view) {
        view.setShowCaseListener(new ShowCaseListener() {
            @Override
            public void onPrevious() {
                previous();
            }

            @Override
            public void onNext() {
                next();
            }

            @Override
            public void onComplete() {
                if (!TextUtils.isEmpty(tag)) {
                    ShowCasePreference.setShown(getActivity(),tag, true);
                }
                ShowCaseDialog.this.close();
            }
        });

        setCancelable(builder.isClickable());
    }

    public void next(){
        if ((currentTutorIndex + 1 ) >= tutorsList.size()) {
            this.close();
        }
        else {
            ShowCaseDialog.this.show(getActivity(),tag, tutorsList, currentTutorIndex + 1);
        }
    }

    public void previous(){
        if ((currentTutorIndex - 1) < 0) {
            currentTutorIndex = 0;
        }
        else {
             ShowCaseDialog.this.show(getActivity(),tag, tutorsList, currentTutorIndex - 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setDimAmount(0f);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public static class ShowCaseObject {

        View view;
        String title;
        String text;
        ShowCaseContentPosition showCaseContentPosition;
        int tintBackgroundColor;
        ViewGroup scrollView;

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

        public View getView() {
            return view;
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

        public ViewGroup getScrollView() {
            return scrollView;
        }
    }

    public boolean hasShown (Activity activity, String tag){
        return ShowCasePreference.hasShown(activity, tag);
    }

    public void show (Activity activity, @Nullable String tag, final ArrayList<ShowCaseObject> tutorList) {
        show (activity, tag, tutorList, 0);
    }

    public void show (final Activity activity, @Nullable String tag, final ArrayList<ShowCaseObject> tutorList, int indexToShow) {
        this.tutorsList = tutorList;
        this.tag = tag;
        if (indexToShow < 0 || indexToShow >= tutorList.size()) {
            indexToShow = 0;
        }
        int previousIndex = currentTutorIndex;
        currentTutorIndex = indexToShow;

        hasViewGroupHandled = false;
        if (listener!= null) {
            hasViewGroupHandled = listener.onShowCaseGoTo(previousIndex, currentTutorIndex,tutorList.get(currentTutorIndex) );
        }

        // has been handled by listener
        if (hasViewGroupHandled) return;

        final ShowCaseObject showCaseObject = tutorList.get(currentTutorIndex);
        final ViewGroup viewGroup = showCaseObject.getScrollView();
        if (viewGroup!= null) {
            final View viewToFocus = showCaseObject.getView();
            if (viewToFocus != null) {
                hideLayout();
                viewGroup.post(new Runnable() {
                    @Override
                    public void run() {
                        if (viewGroup instanceof ScrollView) {
                            ScrollView scrollView = (ScrollView) viewGroup;
                            scrollView.smoothScrollTo(0, viewToFocus.getTop());
                            scrollView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showLayout(activity, showCaseObject);
                                }
                            }, DELAY_SCROLLING);
                        }
                        else if (viewGroup instanceof NestedScrollView){
                            NestedScrollView scrollView = (NestedScrollView) viewGroup;
                            scrollView.smoothScrollTo(0, viewToFocus.getTop());
                            scrollView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showLayout(activity, showCaseObject);
                                }
                            }, DELAY_SCROLLING);
                        }
                    }
                });
                hasViewGroupHandled = true;
            }
            else {
                hasViewGroupHandled = false;
            }
        }

        if (!hasViewGroupHandled) {
            showLayout(activity, tutorsList.get(currentTutorIndex) );
        }
    }

    public void showLayout(Activity activity, ShowCaseObject showCaseObject){
        final View view = showCaseObject.view;
        final String title = showCaseObject.title;
        final String text = showCaseObject.text;
        final ShowCaseContentPosition showCaseContentPosition = showCaseObject.showCaseContentPosition;
        final int tintBackgroundColor = showCaseObject.tintBackgroundColor;

        FragmentManager fm = activity.getFragmentManager();
        if (!isVisible()) {
            if (!isAdded()) {
                show(fm, TAG);
            }
            else if (isHidden()){
                FragmentTransaction ft = fm.beginTransaction();
                ft.show(ShowCaseDialog.this);
                ft.commit();
            }
        }

        if (view == null) {
            layoutShowTutorial(null, title, text, showCaseContentPosition, tintBackgroundColor);
        } else {
            view.post(new Runnable() {
                @Override
                public void run() {
                    layoutShowTutorial(view, title, text, showCaseContentPosition, tintBackgroundColor);
                }
            });
        }
    }

    public void hideLayout(){
        final ShowCaseLayout layout = (ShowCaseLayout) ShowCaseDialog.this.getView();
        if (layout == null) {
            return;
        }
        layout.hideTutorial();
    }


    private void layoutShowTutorial(View view,String title, String text,
                                    ShowCaseContentPosition showCaseContentPosition,
                                    int tintBackgroundColor){
        final ShowCaseLayout layout = (ShowCaseLayout) ShowCaseDialog.this.getView();
        if (layout == null) {
            return;
        }
        layout.showTutorial(view, title, text, currentTutorIndex, tutorsList.size(),
                showCaseContentPosition, tintBackgroundColor);
    }

    public void close() {
        dismiss();
        final ShowCaseLayout layout = (ShowCaseLayout) ShowCaseDialog.this.getView();
        if (layout == null) {
            return;
        }
        layout.closeTutorial();
    }

}
