package hr.foi.air.core;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;

public interface NavigationItem {
    public String getItemName();
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
    public Drawable getIcon(Context context);
    public void setReadyForDataListener(ReadyForDataListener readyForDataListener);
};
