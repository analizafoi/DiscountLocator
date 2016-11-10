package hr.foi.air.discountlocator.webservice;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Ivan on 24.10.2016..
 */

public interface AirWebService {
    @FormUrlEncoded
    @POST("stores.php")
    Call<AirWebServiceResponse> getStores(@Field("method") String method);

    @FormUrlEncoded
    @POST("discounts.php")
    Call<AirWebServiceResponse> getDiscounts(@Field("method") String method);
}
