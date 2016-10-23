package hr.foi.air.discountlocator.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.R;

/**
 * Created by Ivan on 23.10.2016..
 */

public class StoreViewHolder extends ParentViewHolder {
    @BindView(R.id.store_name)
    TextView mStoreName;
    @BindView(R.id.store_description)
    TextView mStoreDescription;
    @BindView(R.id.store_image)
    ImageView mStoreImage;

    View mItemView;

    // constructor binds the ButterKnife library and makes itemView available locally
    public StoreViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, itemView);

    }

    // when the adapter is implemented this method is used to bind list elements with the recycler-view, here, we populate the Views
    public void bind(Store store){
        mStoreName.setText(store.getName());
        mStoreDescription.setText(store.getDescription());
        Picasso.with(itemView.getContext()).load(store.getImgUrl()).into(mStoreImage);
    }
}