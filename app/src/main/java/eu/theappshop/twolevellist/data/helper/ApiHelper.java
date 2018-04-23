package eu.theappshop.twolevellist.data.helper;

import eu.theappshop.twolevellist.data.model.responce.GetListResponse;
import eu.theappshop.twolevellist.data.model.responce.ListResponce;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public interface ApiHelper {

    @GET("/list")
    Observable<GetListResponse<ListResponce>> getList();
}
