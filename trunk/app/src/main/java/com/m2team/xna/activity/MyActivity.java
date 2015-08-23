package com.m2team.xna.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.m2team.xna.R;
import com.m2team.xna.utils.Common;
import com.m2team.xna.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MyActivity extends NavigationLiveo implements NavigationLiveoListener {

    public List<String> mListNameItem;
    String[] menuValues;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String color = Common.getSharedPrefStringValue(this, Constant.SHARE_PREF_KEY_THEME_COLOR);
        if (color.equals(getString(R.string.green)))
            setTheme(R.style.nLiveoDrawer_Green);
        else if (color.equals(getString(R.string.red)))
            setTheme(R.style.nLiveoDrawer_Red);
        else if (color.equals(getString(R.string.blue)))
            setTheme(R.style.nLiveoDrawer_Blue);
        else if (color.equals(getString(R.string.orange)))
            setTheme(R.style.nLiveoDrawer);
        else if (color.equals(getString(R.string.lime)))
            setTheme(R.style.nLiveoDrawer_Lime);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText(getString(R.string.app_name));
        this.mUserName.setTextSize(16);
        this.mUserEmail.setText(getString(R.string.app_slogan));
        this.mUserPhoto.setImageResource(R.drawable.ic_launcher);
        this.mUserBackground.setImageResource(R.drawable.bg_menu);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.click_back_message),
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2500);

    }

    @Override
    public void onInt(Bundle savedInstanceState) {
        this.setNavigationListener(this);
        //Creation of the list items is here
        // set listener {required}

        //First item of the position selected from the list
        this.setDefaultStartPositionNavigation(0);
        menuValues = getResources().getStringArray(R.array.menu_values);
        Arrays.sort(menuValues);
        List<Integer> mListIconItem = new ArrayList<>();
        mListNameItem = new ArrayList<>();
        mListNameItem.add(getString(R.string.STAFF_CHOICE));
        mListIconItem.add(R.drawable.ic_action_important);
        for (int i = 0; i < menuValues.length; i++) {
            mListNameItem.add(menuValues[i]);
            mListIconItem.add(R.drawable.ic_action_camera);
        }

        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
        //mListHeaderItem.add(4);

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        /*mSparseCounterItem.put(0, 7);
        mSparseCounterItem.put(1, 123);
        mSparseCounterItem.put(6, 250);*/

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.settings, R.drawable.ic_action_settings);
        // this.setColorIconItemNavigation(R.color.material_blue_500);
        //this.setColorNameItemNavigation(R.color.material_deep_orange_300);
        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {
        boolean hasConnection = Common.hasInternetConnection(this);
        if (hasConnection) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            String url = getUrlForFragment(mListNameItem.get(position));
            ContentFragment mFragment = ContentFragment.newInstance(url, position);

            if (mFragment != null) {
                mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment, ContentFragment.TAG).commit();
            }
        } else {
            Toast.makeText(this, getString(R.string.error_connectivity), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {
        //hide the menu when the navigation is opens
        MenuItem item = menu.findItem(R.id.menu_refresh);
        if (item != null)
            item.setVisible(!visible);
    }

    @Override
    public void onClickUserPhotoNavigation(View v) {
        //user photo onClick
        Toast.makeText(this, R.string.open_user_profile, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickFooterItemNavigation(View v) {
        //footer onClick
        startActivity(new Intent(this, PreferenceSetting.class));
    }

    private String getUrlForFragment(String menuValue) {
        String url = Constant.STAFF_CHOICE;
        if (menuValue.equalsIgnoreCase(getString(R.string.STAFF_CHOICE))) {
            url = Constant.STAFF_CHOICE;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.LANDSCAPE))) {
            url = Constant.LANDSCAPE;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.PORTRAIT))) {
            url = Constant.PORTRAIT;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.NATURE))) {
            url = Constant.NATURE;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.STREET_LIFE))) {
            url = Constant.STREET_LIFE;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.MACRO))) {
            url = Constant.MACRO;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.ARCHITECTURE))) {
            url = Constant.ARCHITECTURE;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.TRAVEL))) {
            url = Constant.TRAVEL;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.ANIMAL))) {
            url = Constant.ANIMAL;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.BW))) {
            url = Constant.BW;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.SPORT))) {
            url = Constant.Sport;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Abstract))) {
            url = Constant.Abstract;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Advertisement))) {
            url = Constant.Advertisement;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Conceptual))) {
            url = Constant.Conceptual;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Experimental))) {
            url = Constant.Experimental;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Fashion))) {
            url = Constant.Fashion;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Journalism))) {
            url = Constant.Journalism;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Nude))) {
            url = Constant.Nude;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Other))) {
            url = Constant.Other;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Panorama))) {
            url = Constant.Panorama;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Product))) {
            url = Constant.Product;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Stage))) {
            url = Constant.Stage;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Still_Life))) {
            url = Constant.Still_Life;
        } else if (menuValue.equalsIgnoreCase(getString(R.string.Wedding))) {
            url = Constant.Wedding;
        }
        return url;
    }

}
