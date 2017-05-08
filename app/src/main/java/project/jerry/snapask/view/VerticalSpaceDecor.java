package project.jerry.snapask.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Migme_Jerry on 2017/5/8.
 */

public class VerticalSpaceDecor extends RecyclerView.ItemDecoration {

    private int mSpace;

    public VerticalSpaceDecor(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // First item needs top margin
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }
        outRect.bottom = mSpace;
    }
}
