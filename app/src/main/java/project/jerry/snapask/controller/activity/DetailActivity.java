package project.jerry.snapask.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import project.jerry.snapask.R;
import project.jerry.snapask.util.ImageLoader;
import project.jerry.snapask.view.ClassViewAdapter;
import project.jerry.snapask.view.ElasticDragDismissRelativeLayout;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class DetailActivity extends BaseActivity {

    ElasticDragDismissRelativeLayout.ElasticDragDismissCallback mDragCallback;
    ElasticDragDismissRelativeLayout mDraggableFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initImage();
        initFakeRecyclerView();
        initDragView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDraggableFrame != null) {
            mDraggableFrame.addListener(mDragCallback);
        }
    }

    @Override
    protected void onPause() {
        if (mDraggableFrame != null) {
            mDraggableFrame.removeListener(mDragCallback);
        }
        super.onPause();
    }

    private void initImage() {
        String url = getIntent().getStringExtra(getString(R.string.photo_url));
        ImageView view = (ImageView) findViewById(R.id.image_view);
        ImageLoader.getInstance()
                .loadImageFromUrl(view, url, false, getResources().getColor(R.color.light_gray));
    }

    private void initFakeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fake_recycler);
        recyclerView.setAdapter(new ClassViewAdapter(this, null));
    }

    private void initDragView() {
        mDraggableFrame = (ElasticDragDismissRelativeLayout) findViewById(R.id.drag_view);
        mDragCallback = new ElasticDragDismissRelativeLayout.ElasticDragDismissCallback() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                finishAfterTransition();
            }
        };
    }
}
