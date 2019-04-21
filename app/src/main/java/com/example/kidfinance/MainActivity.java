package com.example.kidfinance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String account_file_name = "account.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load Navigation Bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationViewHeader = (View) navigationView.getHeaderView(0);

        // Load Account Info onto the Navigation Bar
        navigation_bar_account_details(navigationViewHeader);

        // Start the fragment you want here
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SetTargetFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
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

        if (id == R.id.nav_achievement) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AchievementFragment()).commit();
        }
        else if (id == R.id.nav_award) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AwardFragment()).commit();
        }
        else if (id == R.id.nav_progress) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgessFragment()).commit();
        }
        else if (id == R.id.nav_record) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecordFragment()).commit();
        }
        else if (id == R.id.nav_setTarget) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SetTargetFragment()).commit();
        }
        else if (id == R.id.nav_incomeExpense) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IncomeExpenseFragment()).commit();
        }
        else if (id == R.id.nav_setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // To display Account Details in Navigation Bar
    public void navigation_bar_account_details(View navigationViewHeader) {
        // Setup Account Icon and Name
        ImageView account_icon_view = (ImageView) navigationViewHeader.findViewById(R.id.navigation_icon);
        TextView account_name_view = (TextView) navigationViewHeader.findViewById(R.id.navigation_name);

        if (fileExists(getApplicationContext(), account_file_name) == true) {
            // If account.txt exists in local storage:
            // Load the account information to top of navigation bar one by one
            String json = loadTextFile(account_file_name);
            JsonObject all = new JsonParser().parse(json).getAsJsonObject();

            String account_name = all.get("account_name").getAsString();
            Uri icon_uri = Uri.parse(all.get("account_icon_uri").getAsString());

            account_name_view.setText(account_name);
            account_icon_view.setImageURI(icon_uri);
        }
        else {
            Toast.makeText(getApplicationContext(), "Cannot detect/read account.txt in local storage!", Toast.LENGTH_LONG).show();
        }
    }

    // File I/O for storing achievement info
    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);

        if (file == null || !file.exists()) {
            return false;
        }

        return true;
    }

    public String loadTextFile(String fileName) {
        String text = "";
        try {
            FileInputStream inStream = getApplicationContext().openFileInput(fileName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = inStream.read(buffer))!=-1) {
                stream.write(buffer,0,length);
            }
            stream.close();
            inStream.close();
            text = stream.toString();
            Toast.makeText(getApplicationContext(),"Loaded",Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            return e.toString();
        }

        return text;
    }
}
