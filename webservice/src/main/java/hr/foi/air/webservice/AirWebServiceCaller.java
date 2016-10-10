package hr.foi.air.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Arrays;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.webservice.responses.AirWebServiceResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Ivan on 10.10.2016..
 */

public class AirWebServiceCaller {

    AirWebServiceHandler mAirWebServiceHandler;
    Retrofit retrofit;

    private final String baseUrl = "http://cortex.foi.hr/mtl/courses/air/";

    public AirWebServiceCaller(AirWebServiceHandler airWebServiceHandler){
        this.mAirWebServiceHandler = airWebServiceHandler;

        //To verify what's sending over the network, use Interceptors
        OkHttpClient client = new OkHttpClient();

        // for debuggint use HttpInterceptor
        //client.interceptors().add(new HttpInterceptor());

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public void getAll(String method, final Type entityType){

        AirWebService serviceCaller = retrofit.create(AirWebService.class);
        Call<AirWebServiceResponse> call = serviceCaller.getStores(method);

        if(call != null){
            call.enqueue(new Callback<AirWebServiceResponse>() {
                @Override
                public void onResponse(Response<AirWebServiceResponse> response, Retrofit retrofit) {
                    try {
                        if(response.isSuccess()){
                            if(entityType == Store.class){
                                System.out.println("store");
                                handleStores(response);
                            } else if(entityType == Discount.class){
                                System.out.println("discount");
                                handleDiscounts(response);
                            } else
                            {
                                System.out.println("Unrecognized class");
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void handleStores(Response<AirWebServiceResponse> response) {
        Gson gson = new Gson();
        Store[] storeItems = gson.fromJson(response.body().getItems(), Store[].class);
        if(mAirWebServiceHandler != null){
            mAirWebServiceHandler.onDataArrived(Arrays.asList(storeItems), true, response.body().getTimeStamp());

        }

    }

    private void handleDiscounts(Response<AirWebServiceResponse> response) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // response JSON format
                .create();

        Discount[] discountItems = gson.fromJson(response.body().getItems(), Discount[].class);
        if(mAirWebServiceHandler != null){
            mAirWebServiceHandler.onDataArrived(Arrays.asList(discountItems), true, response.body().getTimeStamp());
        }

    }


}
