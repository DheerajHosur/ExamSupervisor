package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyAllotment extends AppCompatActivity implements View.OnClickListener {

    public ListView listView;
    private Button buttonDelete,buttonView;
    private String JSON_STRING;
    private Spinner Spr,spr2;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_allotment);
        id=getIntent().getStringExtra("uname");
        listView = (ListView) findViewById(R.id.listView21);



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
                //String id = jo.getString(Config.TAG_ID);
               // String name = jo.getString(Config.TAG_NAME);
                String hid = jo.getString(Config.TAG_hid);
                String date1 = jo.getString(Config.TAG_date1);
                String time1 = jo.getString(Config.TAG_time1);


                HashMap<String,String> employees = new HashMap<>();
               // employees.put(Config.TAG_NAME,name);
                employees.put(Config.TAG_hid,hid);
                employees.put(Config.TAG_date1,date1);
                employees.put(Config.TAG_time1,time1);
                list.add(employees);
            }
            Collections.reverse(list);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MyAllotment.this, list, R.layout.myallotlist,
                new String[]{Config.TAG_hid,Config.TAG_date1,Config.TAG_time1,},
                new int[]{R.id.Block1,R.id.Examedate1,R.id.examtime1,});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyAllotment.this,"Fetching Data","Please Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Config.URL_GET_Myallot,id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }




    @Override
    public void onClick(View v) {


        if(v == buttonView){
            getJSON();
        }
    }


}
