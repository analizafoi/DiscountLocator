package hr.foi.air.discountlocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.DataLoadedListener;
import hr.foi.air.core.DataLoader;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Discount_Table;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.helper.MockData;
import hr.foi.air.discountlocator.loaders.DbDataLoader;
import hr.foi.air.discountlocator.loaders.WsDataLoader;

public class MainActivity extends AppCompatActivity  implements DataLoadedListener {

    @BindView(R.id.discount_list)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

    }

    @OnClick(R.id.test_button)
    public void buttonClicked(View view){

        DataLoader dataLoader;
        if(Store.getAll().isEmpty()){
            System.out.println("Loading web data");
            dataLoader = new WsDataLoader();
        } else {
            System.out.println("Loading local data");
            dataLoader = new DbDataLoader();
        }
        dataLoader.loadData(this);

    }

    @Override
    public void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        System.out.println("Data is here... ");
        String[] listItems = new String[discounts.size()];

        for (int i = 0; i < discounts.size(); i++) {
            listItems[i] = discounts.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }
}
