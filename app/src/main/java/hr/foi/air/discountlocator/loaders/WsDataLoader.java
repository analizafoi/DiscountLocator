package hr.foi.air.discountlocator.loaders;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.core.DataLoadedListener;
import hr.foi.air.core.DataLoader;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.webservice.AirWebService;
import hr.foi.air.webservice.AirWebServiceCaller;
import hr.foi.air.webservice.AirWebServiceHandler;

/**
 * Created by Ivan on 10.10.2016..
 */

public class WsDataLoader extends DataLoader{
    private boolean storesArrived = false;
    private boolean discountsArrived = false;

    @Override
    public void loadData(DataLoadedListener dataLoadedListener) {
        super.loadData(dataLoadedListener);

        AirWebServiceCaller storesWs = new AirWebServiceCaller(storesHandler);
        AirWebServiceCaller discountsWs = new AirWebServiceCaller(discountsHandler);

        storesWs.getAll("getAll", Store.class);
        discountsWs.getAll("getAll", Discount.class);

    }

    //TODO: As an exercise, change the architecture so that you have only one AirWebServiceHandler

    AirWebServiceHandler storesHandler = new AirWebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok, long timestamp) {
            if(ok){
                List<Store> stores = (List<Store>) result;
                for(Store store : stores){
                    store.save();
                }
                storesArrived = true;
                checkDataArrival();
            }
        }
    };

    AirWebServiceHandler discountsHandler = new AirWebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok, long timestamp) {
            if(ok){
                List<Discount> discounts = (List<Discount>) result;
                for(Discount discount : discounts){
                    discount.save();
                }
                discountsArrived = true;
                checkDataArrival();
            }
        }
    };


    private void checkDataArrival(){
        if(storesArrived && discountsArrived){
            mDataLoadedListener.onDataLoaded((ArrayList<Store>) Store.getAll(), (ArrayList<Discount>) Discount.getAll());
        }
    }
}
