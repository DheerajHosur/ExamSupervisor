package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;

public class Clerk  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AsyncResponse {
    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.clerk, menu);
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

        if (id == R.id.addStaff) {
            Intent intent=new Intent(context ,AddStaff.class);
            startActivity(intent);
        }else if (id == R.id.removeStaff) {
            Intent intent=new Intent(context ,Removestaff.class);
            startActivity(intent);
        }
        else if (id == R.id.allot) {
            Intent intent=new Intent(context ,Allotment.class);
            startActivity(intent);

        }
        else if (id == R.id.sendnotice) {
            Intent intent=new Intent(context ,Notice.class);
            startActivity(intent);

        }
        else if (id == R.id.change) {
            Intent intent=new Intent(context ,Updatepassword.class);
            intent.putExtra("type","c");
            startActivity(intent);

        }
        else if (id == R.id.reset) {
           getJSON();


        }
        else if (id == R.id.view) {
            Intent intent=new Intent(context ,ViewClerk.class);
            startActivity(intent);

        }
        else if (id == R.id.AddDate) {
            Intent intent=new Intent(context ,ExamDate.class);
            startActivity(intent);

        }
        else if (id == R.id.sendnotice) {
            Intent intent=new Intent(context ,SendMessage.class);
            startActivity(intent);

        }
        else if (id == R.id.logout) {
            Intent intent=new Intent(context ,stepin.class);
            startActivity(intent);}/*else if (id == R.id.nav_slideshow) {

        }  else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Clerk.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //JSON_STRING = s;
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                //showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_resetpresent);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void processFinish(String output) {
        if(output.equals("Success")){
            Toast.makeText(getApplicationContext(), "Reset Successful!", Toast.LENGTH_LONG).show();
        }

    }
}
