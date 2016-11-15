package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import hr.foi.air.database.entities.Discount;
import hr.foi.air.discountlocator.R;

public class DiscountDetailsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtName = (TextView) getView().findViewById(R.id.discount_details_name);
        TextView txtDescription = (TextView) getView().findViewById(R.id.discount_details_description);
        TextView txtStartDate = (TextView) getView().findViewById(R.id.discount_details_start);
        TextView txtEndDate = (TextView) getView().findViewById(R.id.discount_details_end);

        Bundle data = getArguments();
        int discountId = data.getInt("id", -1);

        if(discountId != -1){
            Discount discount = Discount.getDiscountById(discountId);

            txtName.setText(discount.getName());
            txtDescription.setText(discount.getDescription());

            // import java.text.SimpleDateFormat;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            txtStartDate.setText(sdf.format(discount.getStartDate()));
            txtEndDate.setText(" -- " + sdf.format(discount.getEndDate()));
        }
    }
}
