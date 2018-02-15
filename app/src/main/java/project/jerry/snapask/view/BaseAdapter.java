package project.jerry.snapask.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseAdapter";
    protected Context mContext;

    BaseAdapter(Context context) {
        mContext = context;
    }

    protected abstract int getLayoutIdForPosition(int position);
    protected abstract int getTotalItemCount();
    protected abstract RecyclerView.ViewHolder getViewHolder(View view);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder is called");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(viewType, null);
        return getViewHolder(view);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType is called, position is " + position);
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        int count = getTotalItemCount();
        Log.d(TAG, "getItemCount is called, count is " + count);
        return count;
    }
}
