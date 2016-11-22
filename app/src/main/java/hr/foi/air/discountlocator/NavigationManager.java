package hr.foi.air.discountlocator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import java.util.ArrayList;

import hr.foi.air.core.DataLoadedListener;
import hr.foi.air.core.DataLoader;
import hr.foi.air.core.NavigationItem;
import hr.foi.air.core.ReadyForDataListener;
import hr.foi.air.database.entities.Discount;
import hr.foi.air.database.entities.Store;
import hr.foi.air.discountlocator.loaders.DbDataLoader;
import hr.foi.air.discountlocator.loaders.WsDataLoader;

public class NavigationManager implements DataLoadedListener, ReadyForDataListener {

    private static NavigationManager instance;
    public ArrayList<NavigationItem> navigationItems;
    private Activity mHandlerActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private int mItemGroupId;
    private ArrayList<Store> stores;
    private ArrayList<Discount> discounts;
    private boolean dataLoadedFlag = false;
    private boolean moduleReadyForDataFlag = false;
    private NavigationItem readyForDataModule;

    // private constructor
    private NavigationManager(){
        navigationItems = new ArrayList<NavigationItem>();
        stores = new ArrayList<Store>();
        discounts = new ArrayList<Discount>();
        requestForData();
    }

    public static NavigationManager getInstance(){
        if(instance == null)
            instance = new NavigationManager();
        return instance;
    }

    public void setDependencies(Activity handlerActivity, DrawerLayout drawerLayout, NavigationView navigationView, int itemGroupId){
        this.mHandlerActivity = handlerActivity;
        this.mNavigationView = navigationView;
        this.mDrawerLayout = drawerLayout;
        this.mItemGroupId = itemGroupId;

        /*
        //Analizirajte ponašanje aplikacije ako se prijava na click događaj napravi ovdje
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectNavigationItem(menuItem);
                return false;
            }
        });
        */
    }

    public void selectNavigationItem(MenuItem menuItem){
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawer(GravityCompat.START, true);

            // uses the menu item to find the NavigationItem (interface implementator)
            NavigationItem clickedItem = navigationItems.get(menuItem.getItemId());

            this.moduleReadyForDataFlag = false;
            this.readyForDataModule = null;
            clickedItem.setReadyForDataListener(this);

            FragmentManager fragmentManager = mHandlerActivity.getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, clickedItem.getFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void addItem(NavigationItem newItem){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
        mNavigationView.getMenu().add(mItemGroupId, newItem.getPosition(), newItem.getPosition() + 1, newItem.getItemName())
                .setIcon(newItem.getIcon(mHandlerActivity))
                .setCheckable(true);
    }

    public void requestForData(){

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
        this.stores = stores;
        this.discounts = discounts;
        this.dataLoadedFlag = true;
        synchronizeAndSendData();
    }

    @Override
    public void onReadyForData(NavigationItem navigationItem) {
        this.readyForDataModule = navigationItem;
        this.moduleReadyForDataFlag = true;
        synchronizeAndSendData();
    }

    private void synchronizeAndSendData()
    {
        if (this.dataLoadedFlag == true && this.moduleReadyForDataFlag == true)
        {
            readyForDataModule.loadData(stores, discounts);
        }
    }
}
