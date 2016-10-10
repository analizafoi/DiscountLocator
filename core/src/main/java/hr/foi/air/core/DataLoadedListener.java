package hr.foi.air.core;

import java.util.ArrayList;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

/**
 * Created by Ivan on 10.10.2016..
 */

public interface DataLoadedListener {
    void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts);
}
