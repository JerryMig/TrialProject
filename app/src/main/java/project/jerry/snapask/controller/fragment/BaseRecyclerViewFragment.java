package project.jerry.snapask.controller.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import project.jerry.snapask.R;
import project.jerry.snapask.view.shimmer.ShimmerRecyclerView;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public abstract class BaseRecyclerViewFragment extends BaseFragment {

    protected RecyclerView mRecyclerView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView(view);
        setShimmerEffect();
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.shimmer_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), getOrientation(), false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(getItemDecor());
        mRecyclerView.setAdapter(getAdapter());
    }

    private void setShimmerEffect() {
        if (mRecyclerView instanceof ShimmerRecyclerView) {
            final ShimmerRecyclerView recyclerView = (ShimmerRecyclerView) mRecyclerView;
            recyclerView.showShimmerAdapter();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.hideShimmerAdapter();
                }
            }, 2000);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_shimmer_fragment;
    }

    @NonNull
    protected abstract RecyclerView.Adapter getAdapter();
    protected abstract int getOrientation();
    protected abstract RecyclerView.ItemDecoration getItemDecor();

}
