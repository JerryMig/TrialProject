package project.jerry.snapask.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.R;
import project.jerry.snapask.util.JsoupUtil;
import project.jerry.snapask.util.ToolTip;
import project.jerry.snapask.view.banner.BannerView;
import project.jerry.snapask.view.shimmer.ProgressTransition;

/**
 * Created by Jerry on 2017/11/19.
 */

public class TestViewFragment extends BaseFragment {

    private LinearLayout mContainer;
    private Button button;
    private TextView text;
    private LinearLayout container;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    private boolean visible;
    private String[] titles = {"VIEW 1", "VIEW 2", "VIEW 3", "VIEW 4", "VIEW 5"};
    private boolean oneTime;
    private int progress = 0;

    private BannerView bannerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bannerView = (BannerView) view.findViewById(R.id.banner);
        banners();


        mContainer = (LinearLayout) view.findViewById(R.id.transitions_container);
        button = (Button) view.findViewById(R.id.button);
        text = (TextView) view.findViewById(R.id.text);
        container = (LinearLayout) view.findViewById(R.id.container);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progress_bar_2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                v.post(new Runnable() {
                    @Override
                    public void run() {
                        new ToolTip.Builder(getContext(), ToolTip.BELOW_ANCHOR).setText("this is to try view").show(button);
                    }
                });


                progress += 20;
                setProgress(progress);
//                if (!oneTime) {
//                    TransitionManager.beginDelayedTransition(mContainer);
//                }
//                visible = !visible;
//                text.setVisibility(visible ? View.VISIBLE : View.GONE);
//                oneTime = true;
            }
        });
    }

    private void setProgress(int value) {
        ProgressTransition progressTransition = new ProgressTransition();
        progressTransition.setStartDelay(200);
        progressTransition.setDuration(1000);

        TransitionManager.beginDelayedTransition(mContainer, progressTransition);
        value = Math.max(0, Math.min(100, value));
        progressBar.setProgress(100);
//        progressBar2.setProgress(80);
    }

    private void banners() {
        List<String> urls = new ArrayList<>();
        urls.add("https://timedotcom.files.wordpress.com/2016/12/emma-stone-lalaland.jpg");
        urls.add("https://pbs.twimg.com/profile_images/839721704163155970/LI_TRk1z_400x400.jpg");
        urls.add("https://pbs.twimg.com/profile_images/848009415206858752/cDMhhlfF_400x400.jpg");
        urls.add("https://timedotcom.files.wordpress.com/2018/01/violet-paley.jpg");
        urls.add("https://upload.wikimedia.org/wikipedia/commons/a/af/Chrome-512.png");
        bannerView.setBanners(urls);

        JsoupUtil.parseUrl("");

    }

}
