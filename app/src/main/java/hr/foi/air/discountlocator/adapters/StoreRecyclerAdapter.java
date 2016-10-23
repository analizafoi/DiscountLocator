package hr.foi.air.discountlocator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.discountlocator.R;

/**
 * Created by Ivan on 23.10.2016..
 */

public class StoreRecyclerAdapter extends ExpandableRecyclerAdapter<ExpandableStoreItem, Discount, StoreViewHolder, DiscountViewHolder> {
    private LayoutInflater mInflator;

    public StoreRecyclerAdapter(Context context, @NonNull List<ExpandableStoreItem> parentList) {
        super(parentList);
        mInflator = LayoutInflater.from(context);
    }

    // Create ViewHolders for both item types, Store (parent) and Discount (child)
    @NonNull
    @Override
    public StoreViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View storeView = mInflator.inflate(R.layout.store_list_item, parentViewGroup, false);
        return new StoreViewHolder(storeView);
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View discountView = mInflator.inflate(R.layout.discount_list_item, childViewGroup, false);
        return new DiscountViewHolder(discountView, this);
    }

    // bind ViewHolders to list items
    @Override
    public void onBindParentViewHolder(@NonNull StoreViewHolder parentViewHolder, int parentPosition, @NonNull ExpandableStoreItem parentListItem) {
        ExpandableStoreItem expandableStoreItem = (ExpandableStoreItem) parentListItem;
        parentViewHolder.bind(expandableStoreItem);
    }

    @Override
    public void onBindChildViewHolder(@NonNull DiscountViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Discount childListItem) {
        Discount discount = childListItem;
        childViewHolder.bind(discount);
    }
}