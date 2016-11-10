package hr.foi.air.discountlocator.architecture.interactor.impl;

import java.lang.reflect.Type;
import java.util.List;

import hr.foi.air.discountlocator.architecture.interactor.StoreListInteractor;
import hr.foi.air.discountlocator.architecture.listeners.StoresListener;
import hr.foi.air.discountlocator.data.entities.Discount;
import hr.foi.air.discountlocator.data.entities.Store;
import hr.foi.air.discountlocator.webservice.AirWebServiceCaller;
import hr.foi.air.discountlocator.webservice.AirWebServiceListener;


/**
 * Created by Ivan on 18.10.2016..
 */

public class StoreListInteractorImpl implements StoreListInteractor {

    private boolean dFlag = false;
    private boolean sFlag = false;
    private StoresListener mStoreListener;

    @Override
    public void loadStoreList(StoresListener storeListener) {
        this.mStoreListener = storeListener;
        if(Store.getAll().isEmpty()){
            AirWebServiceCaller serviceCaller = new AirWebServiceCaller(serviceListener);
            serviceCaller.getAll("getAll", Store.class);
            serviceCaller.getAll("getAll", Discount.class);
        } else {
            storeListener.onStoresAvailable(Store.getAll());
        }
    }

    private void checkDataArrival(){
        if(sFlag && dFlag){
            mStoreListener.onStoresAvailable(Store.getAll());
        }
    }

    AirWebServiceListener serviceListener = new AirWebServiceListener() {
        @Override
        public void onDataArrived(Object result, Type resultType) {
            if(resultType == Store.class){
                List<Store> stores = (List<Store>) result;
                for(Store store : stores){
                    store.save();
                }
                sFlag = true;
                checkDataArrival();
            } else {
                List<Discount> discounts = (List<Discount>) result;
                for(Discount discount : discounts){
                    discount.save();
                }
                dFlag = true;
                checkDataArrival();
            }
        }
    };
}
