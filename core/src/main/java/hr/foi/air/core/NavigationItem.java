package hr.foi.air.core;

import android.app.Fragment;

public interface NavigationItem {
    public String getItemName();
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
};
