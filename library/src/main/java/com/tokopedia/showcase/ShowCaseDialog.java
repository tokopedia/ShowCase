package com.tokopedia.showcase;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

public class ShowCaseDialog extends DialogFragment {
    private static final String ARG_BUILDER = "BUILDER";

    private ArrayList<ShowCaseObject> tutorsList;
    private int currentTutorIndex;
    private ShowCaseBuilder builder;

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
                ShowCaseDialog.this.close();
            }
        });

        setCancelable(builder.isClickable());
    }

    public void next(){
        currentTutorIndex++;
        if (currentTutorIndex >= tutorsList.size()) {
            this.close();
        }
        else {
            ShowCaseDialog.this.show(getActivity(), tutorsList, currentTutorIndex);
        }
    }

    public void previous(){
        currentTutorIndex--;
        if (currentTutorIndex < 0) {
            currentTutorIndex = 0;
        }
        else {
             ShowCaseDialog.this.show(getActivity(),tutorsList, currentTutorIndex);
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
            this.view = view;
            this.title = title;
            this.text = text;
            this.showCaseContentPosition = showCaseContentPosition;
            this.tintBackgroundColor = tintBackgroundColor;
        }
        ShowCaseContentPosition showCaseContentPosition;
        View view;
        String title;
        String text;
        int tintBackgroundColor;
    }

    public void show (Activity activity, final ArrayList<ShowCaseObject> tutorList) {
        show (activity, tutorList, 0);
    }

    public void show (Activity activity, final ArrayList<ShowCaseObject> tutorList, int indexToShow) {
        this.tutorsList = tutorList;
        if (indexToShow < 0 || indexToShow >= tutorList.size()) {
            indexToShow = 0;
        }
        currentTutorIndex = indexToShow;

        final View view = tutorList.get(currentTutorIndex).view;
        final String title = tutorList.get(currentTutorIndex).title;
        final String text = tutorList.get(currentTutorIndex).text;
        final ShowCaseContentPosition showCaseContentPosition = tutorList.get(currentTutorIndex).showCaseContentPosition;
        final int tintBackgroundColor = tutorList.get(currentTutorIndex).tintBackgroundColor;
        FragmentManager fm = activity.getFragmentManager();
        if (!isVisible()) {
            show(fm, this.getClass().getName());
        }

        if (view == null) {
            layoutShowTutorial(null, title, text, showCaseContentPosition, tintBackgroundColor);
        }
        else {
            view.post(new Runnable() {
                @Override
                public void run() {
                    layoutShowTutorial(view, title, text, showCaseContentPosition, tintBackgroundColor);
                }
            });
        }
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
