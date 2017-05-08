package project.jerry.snapask.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.R;
import project.jerry.snapask.model.data.ClassData;
import project.jerry.snapask.util.ImageLoader;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class ClassViewAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<ClassData> mDataList;
    private View.OnClickListener mListener;

    public ClassViewAdapter(Context context, View.OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    public void refreshData(List<ClassData> dataList) {
        mDataList = new ArrayList<>(dataList);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.view_class_item;
    }

    @Override
    protected int getTotalItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    protected ClassViewHolder getViewHolder(View view) {
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClassViewHolder viewHolder = (ClassViewHolder) holder;
        ClassData data = getItemForPosition(position);
        if (ensureData(data)) {
            loadImage(viewHolder.mAskingPersonPic, data.getAskingPerson().getProfilePic(), true, R.mipmap.ic_launcher);
            viewHolder.mAskingUserName.setText(data.getAskingPerson().getUserName());
            viewHolder.mSubject.setText(data.getSubject().getAbbreviation());
            viewHolder.mTime.setText(data.getTime());
            loadImage(viewHolder.mTopicImage, data.getClassPic(), false, R.color.light_gray);
            viewHolder.mTopicImage.setOnClickListener(mListener);
            viewHolder.mDescription.setText(data.getDescription());
            viewHolder.mStatus.setImageResource(data.getStatus().equals("Finish")
                    ? R.drawable.green_light : R.drawable.red_light);
        }
    }
    
    private boolean ensureData(ClassData data) {
        if (data == null || data.getAnsweringPerson() == null
                || data.getSubject() == null || data.getAskingPerson() == null) {
            Log.d(TAG, "ensureData failed");
            return false;
        }
        return true;
    }
    
    private void loadImage(ImageView imageView, String url, boolean toCircle, int defaultPic) {
        imageView.setTag(R.id.image_loading, url);
        ImageLoader.getInstance().loadImageFromUrl(imageView, url, toCircle, defaultPic);
    }

    private ClassData getItemForPosition(int position) {
        if (mDataList != null) {
            return mDataList.get(position);
        } else {
            Log.d(TAG, "getItemForPosition - mDataList is null");
        }
        return null;
    }


    private class ClassViewHolder extends RecyclerView.ViewHolder {
        ImageView mAskingPersonPic;
        TextView mAskingUserName;
        TextView mSubject;
        TextView mTime;
        ImageView mTopicImage;
        TextView mDescription;
        ImageView mStatus;

        private ClassViewHolder(View itemView) {
            super(itemView);
            mAskingPersonPic = (ImageView) itemView.findViewById(R.id.asking_profile_pic);
            mAskingUserName = (TextView) itemView.findViewById(R.id.asking_user_name);
            mSubject = (TextView) itemView.findViewById(R.id.subject);
            mTime = (TextView) itemView.findViewById(R.id.post_time);
            mTopicImage = (ImageView) itemView.findViewById(R.id.bottom_pic);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mStatus = (ImageView) itemView.findViewById(R.id.status_image);
        }
    }

}
