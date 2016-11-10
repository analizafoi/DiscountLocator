package hr.foi.air.discountlocator.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Arrays;

import hr.foi.air.discountlocator.data.entities.Discount;
import hr.foi.air.discountlocator.data.entities.Store;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Ivan on 24.10.2016..
 */

public class AirWebServiceCaller {

    private Retrofit retrofit;
    private final String baseUrl = "http://cortex.foi.hr/mtl/courses/air/";
    private AirWebServiceListener mAirWebServiceHandler;

    // constructor
    public AirWebServiceCaller(AirWebServiceListener airWebServiceListener){
        OkHttpClient client = new OkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    // get all records from a web service
    public void getAll(String method, final Type entityType){

        AirWebService serviceCaller = retrofit.create(AirWebService.class);
        Call<AirWebServiceResponse> call;

        if(entityType == Store.class){
            call = serviceCaller.getStores(method);
        } else {
            call = serviceCaller.getDiscounts(method);
        }

        if(call != null){
            call.enqueue(new Callback<AirWebServiceResponse>() {
                @Override
                public void onResponse(Response<AirWebServiceResponse> response, Retrofit retrofit) {
                    try {
                        if(response.isSuccess()){
                            if(entityType == Store.class){
                                System.out.println("Got stores...");
                                handleStores(response);
                            } else if(entityType == Discount.class){
                                System.out.println("Got discounts...");
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
            mAirWebServiceHandler.onDataArrived(Arrays.asList(storeItems), Store.class);
        }
    }

    private void handleDiscounts(Response<AirWebServiceResponse> response) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // response JSON format
                .create();
        Discount[] discountItems = gson.fromJson(response.body().getItems(), Discount[].class);
        if(mAirWebServiceHandler != null){
            mAirWebServiceHandler.onDataArrived(Arrays.asList(discountItems), Discount.class);
        }
    }
}
