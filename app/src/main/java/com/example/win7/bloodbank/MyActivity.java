package com.example.win7.bloodbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String email, pass, str[];
    //GlobalClass obj;
    public static String myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

try {
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    View header=navigationView.getHeaderView(0);
    TextView name = (TextView)header.findViewById(R.id.myname);
    name.setText("Welcome "+SaveSharedPreferences.getUserName(MyActivity.this));

  TextView bg = (TextView)header.findViewById(R.id.mybgroup);
    bg.setText("Blood group: "+SaveSharedPreferences.getbGroup(MyActivity.this));
    TextView mail = (TextView)header.findViewById(R.id.mymail);
    mail.setText("Email: "+SaveSharedPreferences.getEmail(MyActivity.this));







   //Toast.makeText(this,"After login"+SaveSharedPreferences.getUserId(getApplicationContext()), Toast.LENGTH_SHORT).show();
   //  Toast.makeText(this,"After login"+SaveSharedPreferences.getEmail(getApplicationContext()), Toast.LENGTH_SHORT).show();
}
catch (Exception e){
    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       RecentFragment frag=new RecentFragment();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.my_content,frag,frag.getTag()).commit();

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

        if (id == R.id.nav_recent) {


            RecentFragment frag =new RecentFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.my_content, frag, frag.getTag()).commit();
        } else if (id == R.id.nav_donate) {

            DonationFragment frag =new DonationFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.my_content, frag, frag.getTag()).commit();

        } else if (id == R.id.nav_retrieve) {

        } else if (id == R.id.nav_update) {

        } else if (id == R.id.nav_cer) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder= new AlertDialog.Builder(MyActivity.this);
            builder.setTitle("Log Out Window!");
            builder.setMessage("Do you Really Want to Log Out ?");
            builder.setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SaveSharedPreferences.clearUser(getApplicationContext());
                    startActivity(new Intent(MyActivity.this,LoginActivity.class));
                    finish();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

