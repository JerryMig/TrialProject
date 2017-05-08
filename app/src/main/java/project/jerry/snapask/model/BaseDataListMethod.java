package project.jerry.snapask.model;

import project.jerry.snapask.util.RefreshUIListener;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public abstract class BaseDataListMethod<T> {

    private RefreshUIListener mListener;

    public BaseDataListMethod(RefreshUIListener listener) {
        mListener = listener;
    }

    // This is for Step 3 - After data is saved to Db
    // , inform listener to retrieve them from Db
    void notifyUIUpdate() {
        if (mListener != null) {
            mListener.refreshUI();
        }
    }

    public abstract T requestData();
    public abstract T getDataFromDb();

}
