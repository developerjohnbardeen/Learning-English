package com.example.a4000essentialwordsbook1.NavigationClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.a4000essentialwordsbook1.R;
import com.google.android.material.navigation.NavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;


public class MainNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    //private FrameLayout frameLayout;
    private NavigationView navigationView;
    private SwitchCompat darkModeSwitch;
    private ExpandableNavigationListView navigationListView;
    private MaterialSearchBar searchBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        initializeViews();
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState);
        setDarkModeSwitchListener();
        //expandableNavigationListView();
    }

    private void initializeDefaultFragment(Bundle savedInstanceState){
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }

    private void toggleDrawer(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setEnabled(true);

        switch (item.getItemId()){
            case (R.id.nav_buy_app):
                Toast.makeText(this, "you can buy this app", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_invite_friends):
                Toast.makeText(this, "you can invite friends", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_appendix):
                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new SettingsFragment("Appendix")).commit();
                Toast.makeText(this, "you can go to Appendix", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_settings):
                deSelectCheckState();
                Toast.makeText(this, "you can go to settings", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_dark_mode_switch):
                Toast.makeText(this, "you can enable dark mode", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
        }
        return true;
    }

    private void setDarkModeSwitchListener(){
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked){
                Toast.makeText(MainNavigationActivity.this, "Dark Mode Turn Off", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainNavigationActivity.this, "Dark Mode Turn On", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void deSelectCheckState(){
        int noOfItem = navigationView.getMenu().size();
        for (int i = 0; i < noOfItem; i ++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }



    private void initializeViews(){
        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle("Hello There");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        //frameLayout = findViewById(R.id.framelayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setItemBackground(getResources().getDrawable(R.color.fui_transparent));

       // navigationListView = navigationView.findViewById(R.id.header_expandable_navigation);

        navigationView.setNavigationItemSelectedListener(this);
        switchCompat();
    }






    private void switchCompat(){
        navigationView.getMenu().findItem(R.id.nav_dark_mode_switch).setActionView(new SwitchCompat(this));
        ((SwitchCompat) navigationView.getMenu().findItem(R.id.nav_dark_mode_switch).getActionView()).setChecked(true);
        darkModeSwitch = (SwitchCompat) navigationView.getMenu().findItem(R.id.nav_dark_mode_switch).getActionView();
    }


}