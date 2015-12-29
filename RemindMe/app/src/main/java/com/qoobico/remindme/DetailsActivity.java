package com.qoobico.remindme;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.qoobico.remindme.fragment.Contact;


public class DetailsActivity extends Activity {

    public final static String ID = "ID";
    public Contact mContact;

    public TextView mName, mPhone, mEmail, mCity;
    public View mCircle;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve data
        mContact = Contact.getItem(getIntent().getIntExtra(ID, 0));

        // Views
        mName = (TextView) findViewById(R.id.DETAILS_name);
        mPhone = (TextView) findViewById(R.id.DETAILS_phone);
        mCity = (TextView) findViewById(R.id.DETAILS_city);
        mEmail = (TextView) findViewById(R.id.DETAILS_email);
        mCircle = findViewById(R.id.DETAILS_circle);

        // Data population
        mName.setText(mContact.get(Contact.Field.NAME));
        mPhone.setText(mContact.get(Contact.Field.PHONE));
        mCity.setText(mContact.get(Contact.Field.CITY));
        mEmail.setText(mContact.get(Contact.Field.EMAIL));
        GradientDrawable bgShape = (GradientDrawable) mCircle.getBackground();
        bgShape.setColor(Color.parseColor(mContact.get(Contact.Field.COLOR)));

        initToolbar();
        initNavigationView();

    }

    public void initToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });
//        toolbar.inflateMenu(R.menu.menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.view_navigation_open, R.string.view_navigation_close);
        toggle.setDrawerIndicatorEnabled(true);
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

}
