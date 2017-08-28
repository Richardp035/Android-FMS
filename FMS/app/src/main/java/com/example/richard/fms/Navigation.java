package com.example.richard.fms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton crp = (FloatingActionButton) findViewById(R.id.cpr);
        crp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "\u00a9 Zig ZAG \u00a9", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        WebView wview = (WebView) findViewById(R.id.tv3);
        wview.setBackgroundColor(Color.TRANSPARENT);
        String text;
        text = "<html><body style=\"background-color:transparent;\"><p style=\"text-align:justify;font-family:sans-serif;font-size:12px;\">";
        text+= "&nbsp;&nbsp;&nbsp;&nbsp;To checkout in all four season (winter, rainy, and summer) what all seeds can be use and the type" +
                "of seeds that can be used in different type of soil (Clay, Loam, and Sand). To provide them the " +
                "information of the fertilizer that is used for increasing the soil immunity and growth of plant to the customer." +
                " The farmer association bank is used to provide loan for the farmers as material and money." +
                " The farmer can buy product like water pump, tractors and instrument" +
                " used for farming as a material and seeds and fertilizer can be obtained using money loan." +
                " Farmer association (society) inventory can" +
                " inform the stock details of the seeds and fertilizer, user can register and can" +
                " order the product they needed bill will be generated." +
                "Location of the farmer association which contain stock will be indicated.";
        text+= "</p></body></html>";
        wview.loadData(text, "text/html", "utf-8");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent reg=new Intent(this,Navigation.class);
            startActivity(reg);
        } else if (id == R.id.nav_seeds) {
            Intent seed=new Intent(this,seed.class);
            startActivity(seed);

        } else if (id == R.id.nav_fertilizer) {
            Intent fertilizer=new Intent(this,fertilizer.class);
            startActivity(fertilizer);

        } else if (id == R.id.nav_login) {
            Intent reg=new Intent(this,Login.class);
            startActivity(reg);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
