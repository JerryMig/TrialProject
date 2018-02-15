package project.jerry.snapask.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import project.jerry.snapask.R;
import project.jerry.snapask.view.shimmer.ShimmerRecyclerView;

/**
 * Created by Migme_Jerry on 2017/5/23.
 */

public class ShimmerRecyclerViewFragment extends BaseFragment {

    private static final String TAG = "ShimmerRecyclerViewFragment";
    private ShimmerRecyclerView mRecyclerView;

    public static ShimmerRecyclerViewFragment newInstance() {
        return new ShimmerRecyclerViewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_shimmer_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (ShimmerRecyclerView) view.findViewById(R.id.shimmer_recycler_view);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.showShimmerAdapter();

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.hideShimmerAdapter();
            }
        }, 10000);

    }
}
