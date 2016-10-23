package hr.foi.air.discountlocator.adapters;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;

/**
 * Created by Ivan on 23.10.2016..
 */

public class ExpandableStoreItem extends Store implements Parent<Discount> {

    private List<Discount> discounts;

    public ExpandableStoreItem(Store store) {
        super(store);
        this.discounts = store.getDiscountList();

    }

    @Override
    public List<Discount> getChildList() {
        return discounts;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}