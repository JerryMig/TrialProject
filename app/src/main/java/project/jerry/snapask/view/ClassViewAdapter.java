package project.jerry.snapask.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.R;
import project.jerry.snapask.model.data.ClassData;
import project.jerry.snapask.util.AnimUtils;
import project.jerry.snapask.util.ImageLoader;
import project.jerry.snapask.util.TransitionUtils;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class ClassViewAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private static final int EXPAND = 0x1;
    private static final int COLLAPSE = 0x2;
    private static final int COMMENT_LIKE = 0x3;
    private static final int REPLY = 0x4;

    private final int NO_EXPANDED = RecyclerView.NO_POSITION;

    private ArrayList<ClassData> mDataList;
    private View.OnClickListener mListener;
    private int mExpandedPosition = -1;
    private RecyclerView mRecyclerView;
    private Transition expandCollapse;
    private CommentAnimator commentAnimator;

    public ClassViewAdapter(Context context, View.OnClickListener listener) {
        super(context);
        mListener = listener;
        initExpandCollapse();
    }

    private void initExpandCollapse() {
        expandCollapse = new AutoTransition();
        expandCollapse.setDuration(180);
        expandCollapse.setInterpolator(AnimUtils.getFastOutSlowInInterpolator(mContext));
        expandCollapse.addListener(new TransitionUtils.TransitionListenerAdapter() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d(TAG, "onTransitionStart: ");
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                commentAnimator.setAnimateMoves(true);
            }
        });
    }

    public void refreshData(List<ClassData> dataList) {
        mDataList = new ArrayList<>(dataList);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

        commentAnimator = new CommentAnimator();
        mRecyclerView.setItemAnimator(commentAnimator);
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
        final ClassViewHolder viewHolder = new ClassViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

//                ClassData removedData1 = mDataList.remove(position);
//                ClassData removedData2 = mDataList.remove(position + 1);
//                ClassData removedData3 = mDataList.remove(position + 2);
//                notifyItemRangeRemoved(position, 3);

//                mDataList.add(3, removedData1);
//                mDataList.add(4,removedData2);
//                mDataList.add(5, removedData3);
//                notifyItemRangeInserted(3, 3);

//                mDataList.add(position - 4, removedData);
//                notifyItemInserted(position - 4);

                TransitionManager.beginDelayedTransition(mRecyclerView, expandCollapse);
                commentAnimator.setAnimateMoves(false);

                // collapse any expended view
                if (mExpandedPosition != NO_EXPANDED) {
                    Log.d(TAG, "onClick - collapse any expended view : position is " + mExpandedPosition);
                    notifyItemChanged(mExpandedPosition, COLLAPSE);
                }

                // expand view
                if (mExpandedPosition != position) {
                    mExpandedPosition = position;
                    Log.d(TAG, "onClick - expand view : position is " + position);
                    notifyItemChanged(position, EXPAND);
                    viewHolder.itemView.requestFocus();
                } else {
                    // this means the user clicks on the same view item
                    mExpandedPosition = NO_EXPANDED;
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (holder instanceof ClassViewHolder) {
            bindPartialCommentChange(
                    (ClassViewHolder) holder, position, payloads);
        } else {
            onBindViewHolder(holder, position);
        }
    }

    private void bindPartialCommentChange(ClassViewHolder viewHolder, int position, List<Object> payloads) {
        if (payloads != null) {
            if (payloads.contains(COLLAPSE)
                    || payloads.contains(EXPAND)) {
                Log.d(TAG, "bindPartialCommentChange - position is : " + position);
                setExpanded(viewHolder);
            } else {
                onBindViewHolder(viewHolder, position);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("BaseAdapter", "onBindViewHolder is called. ");
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

            viewHolder.mDescription.setVisibility(View.GONE);
            viewHolder.mTopicImage.setVisibility(View.GONE);
            viewHolder.mGradientView.setVisibility(View.GONE);
//            setExpanded(viewHolder);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        runEnterAnimation(holder);
    }

    private int animateLayout = -1;
    private void runEnterAnimation(final RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (position > animateLayout) {
            animateLayout++;
            int screenHeight = AnimUtils.getScreenHeight(holder.itemView.getContext());
            holder.itemView.setTranslationY(screenHeight);
            holder.itemView.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    private void setExpanded(ClassViewHolder holder) {
        final int position = holder.getAdapterPosition();
        final boolean isExpanded = position == mExpandedPosition;
        Log.d(TAG, "setExpanded: now position is " + position + " isExpanded: " + isExpanded);
        holder.itemView.setActivated(isExpanded);
        holder.mDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.mTopicImage.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.mGradientView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

//        new ToolTip.Builder(holder.mAskingPersonPic.getContext(), ToolTip.ABOVE_ANCHOR)
//                .setAnchorPosition(ToolTip.BELOW_ANCHOR).showAsDropDown(mRecyclerView, holder.mAskingPersonPic);

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
        ImageView mGradientView;
        TextView mDescription;
        ImageView mStatus;

        private ClassViewHolder(View itemView) {
            super(itemView);
            Log.d("BaseAdapter", "ClassViewHolder is created. ");
            mAskingPersonPic = (ImageView) itemView.findViewById(R.id.asking_profile_pic);
            mAskingUserName = (TextView) itemView.findViewById(R.id.asking_user_name);
            mSubject = (TextView) itemView.findViewById(R.id.subject);
            mTime = (TextView) itemView.findViewById(R.id.post_time);
            mTopicImage = (ImageView) itemView.findViewById(R.id.bottom_pic);
            mGradientView = (ImageView) itemView.findViewById(R.id.gradient_view);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mStatus = (ImageView) itemView.findViewById(R.id.status_image);
        }
    }

    /**
     * Few things to consider:
     * 1. Recycler view item animator runs on all affected item, so animation will appear in a one-by-one fashion.
     * 2. Setting (setItemAnimator) to mull works as well, but it overrides the default animator that comes with Recycler View.
     * 3. Setting an animator that disables animateMove but keeps the rest is in need.
     * 4. Using transition with beginDelayedTransition ensures all items in Recycler View move simultaneously.
     */
    class CommentAnimator extends DefaultItemAnimator {

        private boolean animateMoves = false;

        void setAnimateMoves(boolean animateMoves) {
            this.animateMoves = animateMoves;
        }

        @Override
        public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            Log.d(TAG, "animateAppearance is called");
            return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
        }

        @Override
        public void onAddStarting(RecyclerView.ViewHolder item) {
            Log.d(TAG, "onAddStarting is called");
            super.onAddStarting(item);
        }

        @Override
        public boolean animateAdd(RecyclerView.ViewHolder holder) {
            Log.d(TAG, "animateAdd in CommentAnimator");
            return super.animateAdd(holder);
        }

        @Override
        public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            Log.d(TAG, "animateChange is called");
            return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
        }

        @Override
        public boolean animateMove(
                RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            // This is to disable move animation
            if (!animateMoves) {
                dispatchMoveFinished(holder);
                return false;
            }
            return super.animateMove(holder, fromX, fromY, toX, toY);
        }
    }

}
