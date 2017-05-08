package project.jerry.snapask.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.model.data.ClassData;
import project.jerry.snapask.model.database.ClassDataDAO;
import project.jerry.snapask.model.request.HttpRequest;
import project.jerry.snapask.model.request.HttpResult;
import project.jerry.snapask.util.RefreshUIListener;
import rx.Subscriber;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public class ClassDataListMethod extends BaseDataListMethod<List<ClassData>> {

    private final String TAG = this.getClass().getSimpleName();
    private final String SUCCESS = "success";

    private ClassDataDAO mClassDAO;
    private List<ClassData> mDataList;

    public ClassDataListMethod(Context context, RefreshUIListener listener) {
        super(listener);
        mClassDAO = new ClassDataDAO(context);
    }

    @Override
    public List<ClassData> requestData() {
        if (mDataList == null || mDataList.isEmpty()) {
            requestApiData();
        }
        return mDataList;
    }

    // This is step 3 - retrieve data from Db for UI purposes
    @Override
    public List<ClassData> getDataFromDb() {
        return mClassDAO.loadClassesFromDb();
    }

    // This is step 1 - making a API request with Retrofit
    private void requestApiData() {
        HttpRequest.getInstance().getClassData(new Subscriber<HttpResult<List<ClassData>>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted called in request data.");
                saveDataToDb();
                notifyUIUpdate();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError called in request data.");
            }

            @Override
            public void onNext(HttpResult<List<ClassData>> listHttpResult) {
                Log.d(TAG, "onNext called in request data.");
                if (listHttpResult.getStatus().equals(SUCCESS)) {
                    List<ClassData> list = listHttpResult.getData();
                    mDataList = new ArrayList<ClassData>(list);
                }
            }
        });
    }

    // This is step 2 - saving fetched data to database
    private void saveDataToDb() {
        if (mClassDAO != null) {
            if (!mClassDAO.saveClassDataToDb(mDataList)) {
                Log.d(TAG, "saveDataToDb failed.");
            }
        }
    }

}
