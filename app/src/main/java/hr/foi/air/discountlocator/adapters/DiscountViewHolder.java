package hr.foi.air.discountlocator.adapters;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import hr.foi.air.core.CurrentActivity;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.fragments.DiscountDetailsFragment;

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

    @OnClick
    public void discountSelected(){
        Bundle args = new Bundle();
        args.putInt("id", mDiscount.getId());

        DiscountDetailsFragment ddf = new DiscountDetailsFragment();
        ddf.setArguments(args);

        FragmentTransaction ft = CurrentActivity.getActivity().getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ddf);
        ft.commit();
    }

    @OnLongClick
    public boolean discountLongSelected(){
        // AlertDialog import android.app.AlertDialog
        final AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        final int parentPosition = getParentAdapterPosition();

        alertDialog.setTitle(itemView.getContext().getString(R.string.removal_question));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, itemView.getContext().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Store parentStore = Store.getStoreById(mDiscount.getStoreId());
                // delete in database
                mDiscount.delete();
                // delete in list items
                mAdapter.getParentList().get(getParentAdapterPosition()).getChildList().remove(getChildAdapterPosition());
                // redraw list and remove this item
                mAdapter.notifyChildRemoved(getParentAdapterPosition(), getChildAdapterPosition());
                mAdapter.notifyDataSetChanged();

                if(mAdapter.getParentList().get(parentPosition).getChildList().size() == 0){
                    mAdapter.notifyParentRemoved(parentPosition);
                    parentStore.delete();
                    mAdapter.getParentList().remove(parentPosition);
                    mAdapter.notifyDataSetChanged();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, itemView.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        return true;
    }

}