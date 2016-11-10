package hr.foi.air.discountlocator.adapters.viewholders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.discountlocator.DiscountDetailsActivity;
import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.adapters.StoreAdapter;
import hr.foi.air.discountlocator.data.entities.Discount;

/**
 * Created by Ivan on 18.10.2016..
 */

public class DiscountViewHolder extends ChildViewHolder {

    @BindView(R.id.discount_name)
    TextView mDiscountName;
    @BindView(R.id.discount_description)
    TextView mDiscountDescription;
    @BindView(R.id.discount_value)
    TextView mDiscountValue;

    private Discount mDiscount;
    View mItemView;
    StoreAdapter mAdapter;

    public DiscountViewHolder(View itemView, StoreAdapter adapter) {
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

    @OnClick
    public void discountSelected(){
        Bundle args = new Bundle();
        args.putInt("id", mDiscount.getId());

        Intent intent = new Intent(itemView.getContext(), DiscountDetailsActivity.class);
        intent.putExtras(args);
        itemView.getContext().startActivity(intent);

    }

}