package project.jerry.snapask.view.shimmer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import project.jerry.snapask.R;

/**
 * Created by Migme_Jerry on 2017/5/23.
 */

public class ShimmerAdapter extends RecyclerView.Adapter<ShimmerViewHolder> {

    private int mItemCount = 6;
    private LayoutInflater mLayoutInflater;

    public ShimmerAdapter() {

    }

    public ShimmerAdapter(int mItemCount) {
        this.mItemCount = mItemCount;
    }

    @Override
    public ShimmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_shimmer, parent, false);
        return new ShimmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShimmerViewHolder holder, int position) {
        if (holder != null) {
            ShimmerFrameLayout layout = (ShimmerFrameLayout) holder.itemView;
            layout.startShimmerAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setMinItemCount(int itemCount) {
        mItemCount = itemCount;
    }
}
