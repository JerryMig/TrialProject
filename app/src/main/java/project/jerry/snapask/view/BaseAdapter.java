package project.jerry.snapask.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    BaseAdapter(Context context) {
        mContext = context;
    }

    protected abstract int getLayoutIdForPosition(int position);
    protected abstract int getTotalItemCount();
    protected abstract RecyclerView.ViewHolder getViewHolder(View view);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(viewType, null);
        return getViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        return getTotalItemCount();
    }
}
