package hr.foi.air.discountlocator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.ButterKnife;
import hr.foi.air.discountlocator.fragments.DiscountListFragment;
import hr.foi.air.discountlocator.helper.Util;

public class MainActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Util util = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        util.setLanguage(this);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        //DiscountListFragment mDiscountListFragment = new DiscountListFragment();
        //FragmentManager mFragmentManager = getFragmentManager();
        //FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.fragment_container, mDiscountListFragment);
        //mFragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_app_preference:
                Intent intent = new Intent(this, AppPreferenceActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        util.setLanguage(this);
        this.recreate();
    }
}
