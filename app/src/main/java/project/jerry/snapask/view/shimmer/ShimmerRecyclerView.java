package project.jerry.snapask.view.shimmer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import project.jerry.snapask.view.shimmer.ShimmerAdapter;

/**
 * Created by Migme_Jerry on 2017/5/23.
 */

public class ShimmerRecyclerView extends RecyclerView {

    public enum LayoutMangerType {
        LINEAR_VERTICAL, LINEAR_HORIZONTAL, GRID
    }

    private LayoutMangerType mLayoutMangerType = LayoutMangerType.LINEAR_VERTICAL;

    private boolean mCanScroll;

    private LayoutManager mShimmerLayoutManager;
    private LayoutManager mActualLayoutManager;

    private ShimmerAdapter mShimmerAdapter;
    private Adapter mActualAdapter;

    public ShimmerRecyclerView(Context context) {
        super(context);
        init();
    }

    public ShimmerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShimmerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mShimmerAdapter = new ShimmerAdapter();
    }

    public void showShimmerAdapter() {
        mCanScroll = false;

        // initialize layout manager here for correct timing
        if (mShimmerLayoutManager == null) {
            initShimmerManager();
        }

        setLayoutManager(mShimmerLayoutManager);
        setAdapter(mShimmerAdapter);
    }

    public void hideShimmerAdapter() {
        mCanScroll = true;
        setLayoutManager(mActualLayoutManager);
        setAdapter(mActualAdapter);
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != mShimmerAdapter) {
            mActualAdapter = adapter;
        }
        super.setAdapter(adapter);
    }

    public void setLayoutManager(LayoutManager manager) {
        if (manager != mShimmerLayoutManager) {
            mActualLayoutManager = manager;
        }
        super.setLayoutManager(manager);
    }

    private void initShimmerManager() {
        if (mActualLayoutManager != null
                && mActualLayoutManager instanceof LinearLayoutManager) {
            mLayoutMangerType = LayoutMangerType.LINEAR_VERTICAL;
        }
        switch (mLayoutMangerType) {
            case LINEAR_VERTICAL:
                mShimmerLayoutManager = new LinearLayoutManager(getContext()) {
                    public boolean canScrollVertically() {
                        return mCanScroll;
                    }
                };
                break;
//            case LINEAR_HORIZONTAL:
//                mShimmerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
//                    public boolean canScrollHorizontally() {
//                        return mCanScroll;
//                    }
//                };
//                break;
//            case GRID:
//                mShimmerLayoutManager = new GridLayoutManager(getContext(), mGridCount) {
//                    public boolean canScrollVertically() {
//                        return mCanScroll;
//                    }
//                };
//                break;
        }
    }

}
