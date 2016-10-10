package hr.foi.air.core;

import java.util.ArrayList;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

/**
 * Created by Ivan on 10.10.2016..
 */

public abstract class DataLoader {

    public ArrayList<Store> stores;
    public ArrayList<Discount> discounts;

    protected DataLoadedListener mDataLoadedListener;

    public void loadData(DataLoadedListener dataLoadedListener){
        this.mDataLoadedListener = dataLoadedListener;
    }

    public boolean dataLoaded(){
        if(stores == null || discounts == null){
            return false;
        }
        else{
            return true;
        }
    }
    
}
