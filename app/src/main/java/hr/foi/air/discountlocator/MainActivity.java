package hr.foi.air.discountlocator;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.ButterKnife;
import hr.foi.air.core.CurrentActivity;
import hr.foi.air.discountlocator.fragments.DiscountListFragment;
import hr.foi.air.discountlocator.helper.Util;
import hr.foi.air.discountlocator.map.MapFragment;

public class MainActivity extends AppCompatActivity  implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        NavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener{

    private Util util = new Util();
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentActivity.setActivity(this);

        CheckExtrasForNotificationData(getIntent());
        util.setLanguage(this);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        //this listener has to be after the mDrawerToggle is initialized
        mToolbar.setNavigationOnClickListener(navigationClick);

        NavigationManager nm = NavigationManager.getInstance();
        nm.setDependencies(this, mDrawer, mNavigationView, R.id.dynamic_group);
        nm.addItem(new DiscountListFragment());
        nm.addItem(new MapFragment());
        nm.showDefaultFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CheckExtrasForNotificationData(intent);
    }

    private void CheckExtrasForNotificationData(Intent i)
    {
        Bundle data = i.getExtras();

        if (data != null) {
            String b = data.containsKey("body") ? data.getString("body") : "";
            if (!b.isEmpty())
            {
                showMyDialog("Message", b);
            }
        }
    }

    private void showMyDialog(String t, String b) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.notification_dialog, null);

        dialog.setView(dialogView);

        dialog.setTitle(t);
        TextView tv = (TextView) dialogView.findViewById(R.id.message);
        tv.setText(b);
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            //Handle click on static options
            case R.id.menu_about:
                mDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(this, R.string.menu_about, Toast.LENGTH_LONG).show();
                //some code goes here
                break;

            //Handle clicks on other (dynamicaly added drawer) items
            default:
                NavigationManager.getInstance().selectNavigationItem(item);
                break;
        }

        return true;
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        mDrawerToggle.setDrawerIndicatorEnabled(mFragmentManager.getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(mFragmentManager.getBackStackEntryCount() != 0){
            // there is something on the stack, I'm in the fragment
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }
            else{
                mFragmentManager.popBackStack();
            }
        } else {
            // I'm on the landing page, close the drawer or exit
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }
            else{
                super.onBackPressed();
            }
        }
    }

    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getFragmentManager().getBackStackEntryCount() == 0) {
                mDrawer.openDrawer(GravityCompat.START);
            }
            else{
                onBackPressed();
            }
        }
    };
}
