package com.wisefn.dls.dls_hyungjun.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wisefn.dls.dls_hyungjun.R;
import com.wisefn.dls.dls_hyungjun.Retrofit.Constants;

import com.wisefn.dls.dls_hyungjun.bean.CustomerSrchList;
import com.wisefn.dls.dls_hyungjun.fragment.HomeFragment;
import com.wisefn.dls.dls_hyungjun.fragment.Option1Fragment;
import com.wisefn.dls.dls_hyungjun.fragment.Option2Fragment;
import com.wisefn.dls.dls_hyungjun.fragment.Option3Fragment;
import com.wisefn.dls.dls_hyungjun.fragment.Option4Fragment;

import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView nav_hd_name;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    public static int countBackFlag;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backpressedTime = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_OPT_1 = "option_1";
    private static final String TAG_OPT_2 = "option_2";
    private static final String TAG_OPT_3 = "option_3";
    private static final String TAG_OPT_4 = "option_4";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public static String LOGINSTATE = Constants.LOGIN.LOGINFAIL;
    private static String USER_ID = null;
    private static String LOGIN_ID;

    //cutomersearchlistFLAG
    public static String CSL = Constants.DATABASE.CUSTOMERSRCHLIST_N;
    public static ArrayList<CustomerSrchList.CustomerSrchListData> customerSrchListDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(LOGINSTATE == Constants.LOGIN.LOGINFAIL){
            openLoginActivity();
        }

        initMainActivity(savedInstanceState);

    }

    private void initMainActivity(Bundle savedInstanceState){

        countBackFlag = 0;

        Log.e("MainActivity", "start");

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 9271);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 9271 && resultCode == 3917) {
            USER_ID = data.getStringExtra("data_id");
            LOGIN_ID = data.getStringExtra("login_id");
            Log.e("Intent test", USER_ID);
            Toast.makeText(getApplicationContext(), USER_ID + LOGINSTATE, Toast.LENGTH_SHORT).show();

            setNav_hd_name(USER_ID);
        }
    }

    private void setNav_hd_name(String name){
        View nav_header_view = navigationView.getHeaderView(0);
        nav_hd_name = (TextView) nav_header_view.findViewById(R.id.nav_hd_name);
        nav_hd_name.setText(name + " 님");
    }

//    private void loadNavHeader() {
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
//    }

    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {

            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                Option1Fragment option1Fragment = new Option1Fragment();
                return option1Fragment;

            case 2:
                Option2Fragment option2Fragment = new Option2Fragment();
                return option2Fragment;

            case 3:
                Option3Fragment option3Fragment = new Option3Fragment();
                return option3Fragment;

            case 4:
                Option4Fragment option4Fragment = new Option4Fragment();
                return option4Fragment;

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
//        toolbar.setTitle(activityTitles[navItemIndex]);

    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_option_1:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_OPT_1;
                        break;
                    case R.id.nav_option_2:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_OPT_2;
                        break;
                    case R.id.nav_option_3:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_OPT_3;
                        break;
                    case R.id.nav_option_4:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_OPT_4;
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(CURRENT_TAG != TAG_HOME){
                homeButtonClicked(R.id.nav_home);
            } else {

                long tempTime = System.currentTimeMillis();
                long intervalTime = tempTime - backpressedTime;

                if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                    LOGINSTATE = Constants.LOGIN.LOGINFAIL;
                    super.onBackPressed();
                } else {
                    backpressedTime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번 더 뒤로가기를 누르시면 \n 종료됩니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    public void homeButtonClicked(int btn_id){
        switch (btn_id) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_option_1:
                navItemIndex = 1;
                CURRENT_TAG = TAG_OPT_1;
                break;
            case R.id.nav_option_2:
                navItemIndex = 2;
                CURRENT_TAG = TAG_OPT_2;
                break;
            case R.id.nav_option_3:
                navItemIndex = 3;
                CURRENT_TAG = TAG_OPT_3;
                break;
            case R.id.nav_option_4:
                navItemIndex = 4;
                CURRENT_TAG = TAG_OPT_4;
                break;
            default:
                navItemIndex = 0;
        }

        loadHomeFragment();
    }

    public void opt2clicked(){
        navItemIndex = 1;
        CURRENT_TAG = TAG_OPT_1;
        loadHomeFragment();
    }

    public static String getLOGIN_ID(){
        return LOGIN_ID;
    }
}
