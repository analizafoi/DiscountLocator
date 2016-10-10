package hr.foi.air.discountlocator.loaders;

import java.util.ArrayList;

import hr.foi.air.core.DataLoadedListener;
import hr.foi.air.core.DataLoader;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

/**
 * Created by Ivan on 10.10.2016..
 */

public class DbDataLoader extends DataLoader{
    @Override
    public void loadData(DataLoadedListener dataLoadedListener) {
        super.loadData(dataLoadedListener);
        try{
            stores = (ArrayList<Store>) Store.getAll();
            discounts = (ArrayList<Discount>) Discount.getAll();

            mDataLoadedListener.onDataLoaded(stores, discounts);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
