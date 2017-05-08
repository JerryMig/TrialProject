package project.jerry.snapask.model;

import android.content.Context;

import project.jerry.snapask.util.RefreshUIListener;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public class DataListFactory {

    private static DataListFactory sInstance;
    private Context mContext;

    public static DataListFactory getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataListFactory(context);
        }
        return sInstance;
    }

    private DataListFactory(Context context) {
        mContext = context.getApplicationContext();
    }

    public BaseDataListMethod getDataMethod(DataType type, RefreshUIListener listener) {
        switch (type) {
            case CLASS:
                return new ClassDataListMethod(mContext, listener);
            case OTHER:
                break;
        }
        return null;
    }

    public enum DataType {
        CLASS, OTHER
    }

}
