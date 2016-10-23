package hr.foi.air.discountlocator.adapters;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.discountlocator.R;

/**
 * Created by Ivan on 23.10.2016..
 */

public class DiscountViewHolder extends ChildViewHolder {

    @BindView(R.id.discount_name)
    TextView mDiscountName;
    @BindView(R.id.discount_description)
    TextView mDiscountDescription;
    @BindView(R.id.discount_value)
    TextView mDiscountValue;

    StoreRecyclerAdapter mAdapter;


    private Discount mDiscount;
    View mItemView;

    public DiscountViewHolder(View itemView, StoreRecyclerAdapter adapter) {
        super(itemView);
        mItemView = itemView;
        mAdapter = adapter;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Discount discount){
        mDiscount = discount;
        mDiscountName.setText(discount.getName());
        mDiscountDescription.setText(discount.getDescription());
        mDiscountValue.setText(discount.getDiscount() + "%");
    }

}