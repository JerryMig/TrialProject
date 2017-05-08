package project.jerry.snapask.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.jerry.snapask.controller.activity.BaseActivity;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public abstract class BaseFragment extends Fragment {

    private FragmentReceiver mReceiver;
    protected Context mContext;
    protected View mRootView;

    public class FragmentReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            BaseFragment.this.onReceive(context, intent);
        }
    }

    protected void onReceive(Context context, Intent intent) {

    }

    protected void registerEvent(String event) {
        if (mReceiver == null) {
            mReceiver = new FragmentReceiver();
        }

        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(activity);
            localBroadcastManager.registerReceiver(mReceiver, new IntentFilter(event));
        }
    }

    protected void unregisterReceiver() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null && mReceiver != null) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(activity);
            localBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    protected void registerReceivers() {

    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceivers();
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getLayoutId();

}
