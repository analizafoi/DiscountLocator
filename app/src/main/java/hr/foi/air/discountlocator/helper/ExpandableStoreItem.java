package hr.foi.air.discountlocator.helper;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

import hr.foi.air.discountlocator.data.entities.Discount;
import hr.foi.air.discountlocator.data.entities.Store;

/**
 * Created by Ivan on 18.10.2016..
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