package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Allotment extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener,AsyncResponse {

    private ListView listView;
    private Button buttonNext,buttonPresent;
    private String JSON_STRING;
    private Integer n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allotment);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
        buttonNext = (Button) findViewById(R.id.next);

//buttonPresent=(Button)findViewById(R.id.present);
       // buttonPresent.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        getJSON();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,id);
                employees.put(Config.TAG_NAME,name);
                list.add(employees);
                n++;
            }
            //n=list.size();
            //Toast.makeText(getApplicationContext(),n, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Allotment.this, list, R.layout.allot_list,
                new String[]{Config.TAG_ID,Config.TAG_NAME},
                new int[]{R.id.id, R.id.name});
               // n=adapter.getCount();
        listView.setAdapter(adapter);
        //Toast.makeText(getApplicationContext(), n, Toast.LENGTH_LONG).show();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Allotment.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent intent = new Intent(this, SelectedStaff.class);
        listView.getChildAt(position).setBackgroundColor(Color.GREEN);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        HashMap postData = new HashMap();
        postData.put("id", empId);
        PostResponseAsyncTask loginTask =
                new PostResponseAsyncTask(Allotment.this, postData);
        loginTask.execute(Config.URL_present);


    }


    @Override
    public void onClick(View v) {


        if(v == buttonNext){
            Intent i=new Intent(Allotment.this,Halls.class);
            startActivity(i);
        }

    }


    @Override
    public void processFinish(String s) {

    }
}
