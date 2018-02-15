package project.jerry.snapask.view.shimmer;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by Migme_Jerry on 2017/5/23.
 */

public class ShimmerViewHolder extends RecyclerView.ViewHolder {

    public ShimmerViewHolder(View itemView) {
        super(itemView);
        ShimmerFrameLayout layout = (ShimmerFrameLayout) itemView;
        layout.setAngle(ShimmerFrameLayout.MaskAngle.CW_0);
        layout.setBaseAlpha((float) 0.75);
        layout.setDuration(800);
        layout.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);
        layout.setTilt(5);
        layout.setAutoStart(false);
    }
}
