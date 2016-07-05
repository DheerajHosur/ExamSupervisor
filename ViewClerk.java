package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ViewClerk extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener {

    public ListView listView;
    private Button buttonDelete,buttonView;
    private String JSON_STRING, Message;
private Spinner Spr,spr2;
    private String PhoneNo;
    Integer x,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clerk);
        listView = (ListView) findViewById(R.id.listView2);
        listView.setOnItemClickListener(this);
        buttonView=(Button)findViewById(R.id.button4);
        Spr=(Spinner)findViewById(R.id.spinner3);
       // buttonDelete = (Button) findViewById(R.id.delete_all);

        spr2=(Spinner)findViewById(R.id.spinner2);

        buttonView.setOnClickListener(this);
        getJSON();
        getJSON1();
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
                String name = jo.getString(Config.TAG_NAME);
                String hid = jo.getString(Config.TAG_hid);
                String date1 = jo.getString(Config.TAG_date1);
                String time1 = jo.getString(Config.TAG_time1);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_NAME,name);
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
                ViewClerk.this, list, R.layout.allotveiw,
                new String[]{Config.TAG_NAME,Config.TAG_hid,Config.TAG_date1,Config.TAG_time1,},
                new int[]{R.id.SName,R.id.Block,R.id.Examedate,R.id.examtime,});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewClerk.this,"Fetching Data","Please Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_ALLOTED);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, SendMessage.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String name1 = map.get(Config.TAG_NAME).toString();
        String hid1 = map.get(Config.TAG_hid).toString();
        String dates1 = map.get(Config.TAG_date1).toString();
        String times1 = map.get(Config.TAG_time1).toString();
        intent.putExtra("name",name1 );
        intent.putExtra("hid", hid1);
        intent.putExtra("date1",dates1 );
        intent.putExtra("time1",times1 );
        startActivity(intent);
    }
    private void showEmployee1(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //Toast.makeText(this, "I am in Try", Toast.LENGTH_LONG).show();
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            int i;
            for( i= 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                s= jo.getInt(Config.TAG_ID);
                PhoneNo=jo.getString("phone");
                Message="Dear Sir/Madam,Your Supervision Duty for Examination has been assigned. For more information login from your Eaxminers Allotment App.";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(PhoneNo, null, Message, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent" + PhoneNo, Toast.LENGTH_LONG).show();
            }

            // Toast.makeText(this, Integer.toString(x), Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void getJSON1(){
        class GetJSON1 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewClerk.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee1();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_StafDuty);
                return s;
            }
        }
        GetJSON1 gj1 = new GetJSON1();
        gj1.execute();
    }


    @Override
    public void onClick(View v) {


        if(v == buttonView){
            getJSON();
        }
    }


}
