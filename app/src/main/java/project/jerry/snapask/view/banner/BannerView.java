package project.jerry.snapask.view.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by Jerry on 2018/1/24.
 */

public class BannerView extends RelativeLayout {

    private int AUTO_DELAY = 1000;
    private final int HANDLER_AUTO_SCROLL = 1;

    private int mCurrentIndex;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private BannerAdapter mBannerAdapter;
    private IndicatorView mIndicatorView;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            handlerMessage(msg);
            return false;
        }
    });

    private List<String> mBannerUrls;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addRecyclerView();
        sendMessageToScroll();
    }

    private void addRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBannerAdapter = new BannerAdapter();
        mRecyclerView.setAdapter(mBannerAdapter);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        // On scroll listener for refreshing indicators
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d("jerry_", "onScrollStateChanged: " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    handleScrollStateChanged();
                }
            }
        });

        // Listener for touch events to stop auto scrolling
        mRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeMessages(HANDLER_AUTO_SCROLL);
                        break;
                    case MotionEvent.ACTION_UP:
                        sendMessageToScroll();
                        break;
                }
                return false;
            }
        });
        addView(mRecyclerView);
    }

    private void addIndicatorView() {
        if (mBannerUrls != null && !mBannerUrls.isEmpty()) {
            if (mIndicatorView != null) {
                removeView(mIndicatorView);
            } else {
                mIndicatorView = new IndicatorView(getContext());
            }
            mIndicatorView.addDots(mBannerUrls.size());
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(mIndicatorView, params);
        }
    }

    private void handleScrollStateChanged() {
        if (mBannerUrls != null && !mBannerUrls.isEmpty()) {
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            int last = mLinearLayoutManager.findLastVisibleItemPosition();
            if (first % mBannerUrls.size() == last % mBannerUrls.size()) {
                mCurrentIndex = first;
                refreshIndicator();
            }
        }
    }

    private void refreshIndicator() {
        mIndicatorView.toggleOn(mCurrentIndex);
    }

    private void handlerMessage(Message msg) {
        if (msg.what == HANDLER_AUTO_SCROLL) {
            mRecyclerView.smoothScrollToPosition(++mCurrentIndex);
            sendMessageToScroll();
        }
    }

    private void sendMessageToScroll() {
        mHandler.sendEmptyMessageDelayed(HANDLER_AUTO_SCROLL, AUTO_DELAY);
    }

    public void setBanners(List<String> banners) {
        mBannerUrls = banners;
        mBannerAdapter.setBannerList(banners);
        addIndicatorView();
    }

}
