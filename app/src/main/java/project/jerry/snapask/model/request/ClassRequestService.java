package project.jerry.snapask.model.request;

import java.util.List;

import project.jerry.snapask.model.data.ClassData;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public interface  ClassRequestService {
    @GET("11a0j5")
    Observable<HttpResult<List<ClassData>>> getClassData();
}
