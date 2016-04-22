package com.performance.ua.performancelab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.xml_backgounds:
                        BackgroundsActivity.start(MainActivity.this);
                        break;
                    case R.id.canvas:
                        CanvasApiActivity.start(MainActivity.this);
                        break;
                    case R.id.hierarchy:
                        HierarchyActivity.start(MainActivity.this);
                        break;
                    case R.id.traceview:
                        Toast.makeText(MainActivity.this, R.string.traceview_toast, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fib:
                        FibActivity.start(MainActivity.this);
                        break;
                    case R.id.main_thread:
                        FibActivity.start(MainActivity.this);
                        break;
                    case R.id.systrace:
                        ContainerActivity.start(MainActivity.this);
                        break;
                    case R.id.memory_monitor:
                        MemoryLeakActivity.start(MainActivity.this);
                        break;
                    case R.id.heap_viewer:
                        MemoryLeakActivity.start(MainActivity.this);
                        break;
                    case R.id.allocation:
                        Toast.makeText(MainActivity.this, R.string.churn_toast, Toast.LENGTH_SHORT).show();
                        MemoryChurnActivity.start(MainActivity.this);
                        break;
                    case R.id.battery:
                        Toast.makeText(MainActivity.this, R.string.battery_historian_toast, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.job_scheduler:
                        SchedulerActivity.start(MainActivity.this);
                        break;

                }
            }
        };
        findViewById(R.id.xml_backgounds).setOnClickListener(onClickListener);
        findViewById(R.id.canvas).setOnClickListener(onClickListener);
        findViewById(R.id.hierarchy).setOnClickListener(onClickListener);
        findViewById(R.id.traceview).setOnClickListener(onClickListener);
        findViewById(R.id.fib).setOnClickListener(onClickListener);
        findViewById(R.id.systrace).setOnClickListener(onClickListener);
        findViewById(R.id.memory_monitor).setOnClickListener(onClickListener);
        findViewById(R.id.heap_viewer).setOnClickListener(onClickListener);
        findViewById(R.id.allocation).setOnClickListener(onClickListener);
        findViewById(R.id.battery).setOnClickListener(onClickListener);
        findViewById(R.id.job_scheduler).setOnClickListener(onClickListener);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
