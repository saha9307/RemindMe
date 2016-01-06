package com.modernexpo.task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.modernexpo.remindme.R;
import com.modernexpo.task.authorization.AccountAuthenticator;
import com.modernexpo.task.authorization.AuthorizationActivity;
import com.modernexpo.task.fragment.Contact;
import com.modernexpo.task.fragment.DataManager;

/**
 * Created by oleksandr.pachkovsky on 22.12.2015.
 */

public class MainActivity extends AppCompatActivity{

    private static final int MAIN_LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private AccountManager mAccountManager;
    private Account mAccount;
    public static String token = "";

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);
        ContentResolver resolver = getContentResolver();

        VerificationAccount();

        rv = (RecyclerView) findViewById(R.id.rv); // layout reference

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true); // to improve performance
        rv.setAdapter(new DataManager()); // the data manager is assigner to the RV
        rv.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.ID, Contact.CONTACTS[position].getId());

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                // the context of the activity
                                MainActivity.this,

                                // For each shared element, add to this method a new Pair item,
                                // which contains the reference of the view we are transitioning *from*,
                                // and the value of the transitionName attribute
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_circle),
                                        getString(R.string.transition_name_circle)),
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_name),
                                        getString(R.string.transition_name_name)),
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_phone),
                                        getString(R.string.transition_name_phone))
                        );
                        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
                    }
                }));
        initToolbar();
        initNavigationView();
        //initTabs();


    }

    public void initToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.actionNotificationItem:
                        //showNotificationTab();
                }
                return true;
            }
        });
    }

    private void VerificationAccount(){
        mAccountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = mAccountManager.getAccountsByType(AccountAuthenticator.ACCOUNT_TYPE);
        if (accounts.length == 0)
        {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            intent.putExtra(AuthorizationActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
            startActivity(intent);
        }
        else
        {
            mAccount = accounts[0];
            mAccountManager.getAuthToken(mAccount, AccountAuthenticator.ACCOUNT_TYPE, null, this,
                    mGetAuthTokenCallback, null);
        }
    }

   /*
   private void initTabs() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(MainActivity.this, getSupportFragmentManager(), rv);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }
    */

//    private void showNotificationTab(){
//        //call notification activity
//        viewPager.setCurrentItem(Constants.TAB_TWO);
//    }



    private AccountManagerCallback<Bundle> mGetAuthTokenCallback =
            new AccountManagerCallback<Bundle>() {
                @Override
                public void run(final AccountManagerFuture<Bundle> arg0) {
                    try {
                        token = (String) arg0.getResult().get(AccountManager.KEY_AUTHTOKEN);
                    } catch (Exception e) {
                        // handle error
                    }
                }
            };

}
