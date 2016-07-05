package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.kosalgeek.asynctask.AsyncResponse;

public class Faculty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {
    private String user,JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user=getIntent().getStringExtra("uname");
        Toast.makeText(getApplicationContext(), user, Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.faculty, menu);
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


        if (id == R.id.Status) {
            Intent intent=new Intent(getApplicationContext() ,Updatepresence.class);
            intent.putExtra("uname",user);
            startActivity(intent);
        }else if (id == R.id.myallot) {
            Intent intent=new Intent(getApplicationContext() ,MyAllotment.class);
            intent.putExtra("uname",user);
            startActivity(intent);
        }
        else if (id == R.id.Viewnotice) {
            Intent intent=new Intent(getApplicationContext() ,Notice.class);
            startActivity(intent);
        }

        else if (id == R.id.change3) {
            Intent intent=new Intent(getApplicationContext() ,Updatepassword.class);
            intent.putExtra("type","f");
            startActivity(intent);

        }
        else if (id == R.id.reset) {
           /*HashMap postData = new HashMap();

            postData.put("id", "123");
            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(Faculty.this, postData);
            loginTask.execute(Config.URL_resetpresent);*/

            getJSON();
            Toast.makeText(getApplicationContext(),"calling json",Toast.LENGTH_LONG).show();

        }

        else if (id == R.id.logout3) {
            Intent intent=new Intent(getApplicationContext() ,stepin.class);
            startActivity(intent);}




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //public void reset(){
        //HashMap postData = new HashMap();

        //postData.put("id", "123");
        //PostResponseAsyncTask loginTask = new PostResponseAsyncTask(Faculty.this, postData);
        //loginTask.execute(Config.URL_resetpresent);
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Faculty.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
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
    public void processFinish(String s) {
        Toast.makeText(getApplicationContext(),s ,Toast.LENGTH_LONG).show();
        if(s.equals("success")){
            Toast.makeText(getApplicationContext(),"All status updated to Default",Toast.LENGTH_LONG).show();
        }

    }
}
