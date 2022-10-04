package com.tokopedia.showcase.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tokopedia.showcase.ShowCaseBuilder;
import com.tokopedia.showcase.ShowCaseContentPosition;
import com.tokopedia.showcase.ShowCaseDialog;
import com.tokopedia.showcase.ShowCaseObject;
import com.tokopedia.showcase.ViewHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Toolbar toolbar;

    private FloatingActionButton fab;

    private ShowCaseDialog showCaseDialog;
    private LinearLayoutManager llm;

    public static final String SHOWCASE_TAG = "sample_showcase_tag";
    private View buttonShowCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initShowCaseDialog();
    }

    private void initShowCaseDialog() {
        showCaseDialog = new ShowCaseBuilder()
                .setPackageName(getPackageName())
                .titleTextColorRes(android.R.color.white)
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
                .useArrow(true)
                .useSkipWord(true)
                .build();
    }

    private void initViews() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScrollShowCaseActivity.class);
                startActivity(intent);
            }
        });

        buttonShowCase = findViewById(R.id.button_show_case);
        buttonShowCase.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        llm = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(llm);

        SampleAdapter adapter = new SampleAdapter(Util.getSampleData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        // logic to make this dialog only shown first time
        // uncomment below to make it work
        /*if (ShowCasePreference.hasShown(this,SHOWCASE_TAG)) {
            return;
        }*/

        final ArrayList <ShowCaseObject> showCaseList = new ArrayList<>();


        showCaseList.add(new ShowCaseObject(
                toolbar,
                null,
                "Above is the <b>toolbar</b>.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere."));

        // using title
        // use position to left
        showCaseList.add(new ShowCaseObject(
                fab,
                "This is example Title",
                "This description point to <font color=\"#FF0000\"> Floating Action Button </font> on the right.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere.",
                ShowCaseContentPosition.LEFT));

        // without using views
        showCaseList.add(new ShowCaseObject(
                null,
                null,
                "This is example without anchored View.<br/><br/>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est? Dicet pro me ipsa virtus nec dubitabit isti vestro beato M. Sic enim censent, oportunitatis esse beate vivere."));

        int completelyVisiblePosition = llm.findFirstCompletelyVisibleItemPosition();
        View itemView = llm.findViewByPosition(completelyVisiblePosition);

        if (itemView!= null) {
            // use background white
            showCaseList.add(new ShowCaseObject(
                    itemView,
                    null,
                    "This is item in the recyclerView",
                    ShowCaseContentPosition.UNDEFINED,
                    Color.WHITE));

            showCaseList.add(new ShowCaseObject(
                    itemView.findViewById(R.id.iv_icon),
                    null,
                    "This is icon"));

            showCaseList.add(new ShowCaseObject(
                    itemView.findViewById(R.id.tv_title),
                    "Color Description",
                    "This describe the color. The white background is written in code. If it is not defined, default will be transparent",
                    ShowCaseContentPosition.UNDEFINED,
                    Color.WHITE));

            showCaseList.add(new ShowCaseObject(
                    itemView.findViewById(R.id.iv_fav),
                    null,
                    "This is icon (2)",
                    ShowCaseContentPosition.LEFT,
                    Color.WHITE));
        }

        int[] location = new int[2];
        buttonShowCase.getLocationInWindow(location);

        int xStart = location[0];
        int yStart = location[1]- ViewHelper.getStatusBarHeight(this);
        int xEnd = location[0]+ buttonShowCase.getWidth();
        int yEnd = location[1]+buttonShowCase.getHeight()- ViewHelper.getStatusBarHeight(this);
        int xCenter = ( xStart + xEnd ) /2;
        int yCenter = ( yStart + yEnd ) /2;
        int radius = buttonShowCase.getWidth() * 2 / 3;

        showCaseList.add(
                new ShowCaseObject(
                        findViewById(android.R.id.content),
                        "Show case using circle custom target",
                        "This is highlighted using custom target",
                        ShowCaseContentPosition.UNDEFINED,
                        Color.WHITE)
                        .withCustomTarget(new int[]{ xCenter, yCenter}
                                , radius) );

        showCaseList.add(
                new ShowCaseObject(
                        findViewById(android.R.id.content),
                        "Show case using rectangle custom target",
                        "This is highlighted using custom target",
                        ShowCaseContentPosition.UNDEFINED,
                        Color.WHITE)
                        .withCustomTarget(new int[]{ xStart - 20, yStart - 20, xEnd + 20, yEnd + 20}) );

        // make the dialog show
        showCaseDialog.show(MainActivity.this   ,SHOWCASE_TAG,  showCaseList);

    }

}
