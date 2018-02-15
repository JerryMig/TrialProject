package project.jerry.snapask.controller.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.R;
import project.jerry.snapask.controller.activity.LauncherActivity;
import project.jerry.snapask.model.ClassDataListMethod;
import project.jerry.snapask.model.DataListFactory;
import project.jerry.snapask.model.data.ClassData;
import project.jerry.snapask.util.RefreshUIListener;
import project.jerry.snapask.view.ClassViewAdapter;
import project.jerry.snapask.view.ScrollItemAnimator;
import project.jerry.snapask.view.VerticalSpaceDecor;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class ClassViewFragment extends BaseRecyclerViewFragment {

    private final String TAG = this.getClass().getSimpleName();

    private ClassViewAdapter mAdapter;
    private ClassDataListMethod mMethod;
    private RefreshUIListener mRefreshListener = new RefreshUIListener() {
        @Override
        public void refreshUI() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter != null && ensureMethod()) {
                        List<ClassData> dataList= mMethod.getDataFromDb();
                        ArrayList<ClassData> duplicates = new ArrayList<ClassData>(dataList);
                        duplicates.addAll(dataList);
                        mAdapter.refreshData(duplicates);
                    }
                }
            });
        }
    };

    private View.OnClickListener mRecyclerClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.bottom_pic:
                    LauncherActivity activity = (LauncherActivity) getActivity();
                    activity.activityTransition(v);
                    break;
            }
        }
    };

    private boolean ensureMethod() {
        if (mMethod == null) {
            Log.d(TAG, "ensureMethod failed in ClassViewFragment.");
            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMethod();
        initData();
    }

    private void initMethod() {
        mMethod = (ClassDataListMethod) DataListFactory.getInstance(mContext.getApplicationContext())
                .getDataMethod(DataListFactory.DataType.CLASS, mRefreshListener);
    }

    private void initData() {
        if (ensureMethod()) {
            mMethod.requestData();
        }
    }

    @NonNull
    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ClassViewAdapter(getContext(), mRecyclerClickLister);
        return mAdapter;
    }

    @Override
    protected int getOrientation() {
        return LinearLayoutManager.VERTICAL;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecor() {
        return new VerticalSpaceDecor(getResources().getDimensionPixelSize(R.dimen.small_margin));
    }

}
