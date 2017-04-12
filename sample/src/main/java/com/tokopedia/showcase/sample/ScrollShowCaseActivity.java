package com.tokopedia.showcase.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tokopedia.showcase.ShowCaseBuilder;
import com.tokopedia.showcase.ShowCaseContentPosition;
import com.tokopedia.showcase.ShowCaseDialog;
import com.tokopedia.showcase.ShowCasePreference;

import java.util.ArrayList;

public class ScrollShowCaseActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private ShowCaseDialog showCaseDialog;

    public static final String SHOWCASE_TAG = "scroll_showcase_tag";
    private View text1;
    private View text2;
    private View text3;
    private View text4;
    private View text5;
    private View text6;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_showcase);

        initViews();
        initShowCaseDialog();
    }

    private void initShowCaseDialog() {
        showCaseDialog = new ShowCaseBuilder()
                .textColorRes(android.R.color.white)
                .shadowColorRes(R.color.shadow)
                .titleTextSizeRes(R.dimen.text_title)
                .textSizeRes(R.dimen.text_normal)
                .spacingRes(R.dimen.spacing_normal)
                .backgroundContentColorRes(R.color.blue)
                .circleIndicatorBackgroundDrawableRes(R.drawable.selector_circle_green)
                .prevStringRes(R.string.previous)
                .nextStringRes(R.string.next)
                .finishStringRes(R.string.finish)
                .useCircleIndicator(true)
                .clickable(true)
                .build();
    }

    private void initViews() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.fab = (FloatingActionButton) findViewById(R.id.fab);

        View buttonShowCase = findViewById(R.id.button_show_case);
        buttonShowCase.setOnClickListener(this);

        scrollView = (NestedScrollView) findViewById(R.id.scrollView);

        text1 = findViewById(R.id.tv_example1);
        text2 = findViewById(R.id.tv_example2);
        text3 = findViewById(R.id.tv_example3);
        text4 = findViewById(R.id.tv_example4);
        text5 = findViewById(R.id.tv_example5);
        text6 = findViewById(R.id.tv_example6);
    }

    @Override
    public void onClick(View view) {
        // logic to make this dialog only shown first time
        // uncomment below to make it work
        /*if (ShowCasePreference.hasShown(this,SHOWCASE_TAG)) {
            return;
        }*/

        ArrayList <ShowCaseDialog.ShowCaseObject> showCaseList = new ArrayList<>();
        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                toolbar,
                null,
                "Above is the <b>toolbar</b>.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere."));

        // using title
        // use position to left
        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                fab,
                "This is example Title",
                "This description point to <font color=\"#FF0000\"> Floating Action Button </font> on the right.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere.",
                ShowCaseContentPosition.LEFT));

        // without using views
        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                null,
                null,
                "This is example without anchored View.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere."));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text1,
                null,
                "Text Example 1",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text2,
                null,
                "Text Example 2",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text3,
                null,
                "Text Example 3",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text4,
                null,
                "Text Example 4",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text5,
                null,
                "Text Example 5",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        showCaseList.add(new ShowCaseDialog.ShowCaseObject(
                text6,
                null,
                "Text Example 6",
                ShowCaseContentPosition.UNDEFINED,
                0,
                scrollView));

        // make the dialog show
        showCaseDialog.show(this,SHOWCASE_TAG,  showCaseList);
    }

}
