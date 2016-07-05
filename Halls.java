package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Halls extends AppCompatActivity implements AsyncResponse {
    public EditText Staf,hall,dates,times;
    public Button btnAllot;
    public String JSON_STRING,JSON_STRING1, date1,time1,z;
    Integer[] blk=new Integer[500];
    Integer[] extra=new Integer[500];
    Integer[] relever=new Integer[500];
    Integer[] s=new Integer[500];
   String[] exdate=new String[500];
    String[] extime=new String[500];
   String[] t=new String[5];
    String[] du=new String[5];
    Integer[] s2,c2,ha2;
    Integer c,v,f=0,d,flag=1,tag=1;
   public   int a,x,y,h;
    ProgressDialog loading;
    public PostResponseAsyncTask loginTask1;
    List<Integer> solution = new ArrayList<>();
    List<Integer> halls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halls);


       btnAllot =(Button)findViewById(R.id.button3);
       // Staf.setText(Integer.toString(x));
        btnAllot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Extra ext=new Extra();
                //ext.getData();
                getData1();
                //Toast.makeText(getApplicationContext(),"After GetData",Toast.LENGTH_LONG).show();
            }
        });
        getJSON();

    }

    private void updateDuty(final Integer q)
    {
            HashMap postData1 = new HashMap();

            postData1.put("sid", Integer.toString(q));

            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(Halls.this, postData1);
            loginTask.execute(Config.URL_getDuty);
        }



    // Implementing Fisher–Yates shuffle
    private void showData(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //Toast.makeText(this, "I am in ShowData", Toast.LENGTH_LONG).show();
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            int i;
            for( i= 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

               exdate[i]=jo.getString("exdate");
                extime[i]=jo.getString("extime");
                blk[i]=jo.getInt("Block");
                extra[i]=jo.getInt("Extra");
                relever[i]=jo.getInt("Relever");
                /*exdate=jo.getString("exdate");
                extime=jo.getString("extime");
                blk=jo.getInt("Block");
                extra=jo.getInt("Extra");
                relever=jo.getInt("Relever");
                allot();*/
            }
            y=i;
            //Toast.makeText(this, Integer.toString(y), Toast.LENGTH_LONG).show();
            allot();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void allot(){
        for (int j = 0; j < y; j++) {

            date1 = exdate[j];
            time1 = extime[j];
            //AllotEmp();
            // String staff = Staf.getText().toString();
            //  String hal = blk[j];
            // final int n = Integer.parseInt(staff);
            h = blk[j];


            ArrayList<Integer> dataList = new ArrayList<Integer>();
            for (int i = 0; i < x; i++)
            {
                dataList.add(s[i]);
            }
            Collections.shuffle(dataList);
            s2 = new Integer[dataList.size()];
            for (int i = 0; i < dataList.size(); i++)
            {
                s2[i] = dataList.get(i);
                //  Toast.makeText(getApplicationContext(), String.valueOf(s2[i]), Toast.LENGTH_LONG).show();
            }


            //Toast.makeText(getApplicationContext(), "This is first array", Toast.LENGTH_LONG).show();

            ArrayList<Integer> dataList1 = new ArrayList<Integer>();
            for (int i = 0; i < h; i++)
            {
                dataList1.add(i);
            }
            Collections.shuffle(dataList1);
             ha2 = new Integer[dataList1.size()];
            for (int i = 0; i < dataList1.size(); i++)
            {
                ha2[i] = dataList1.get(i);
                //  Toast.makeText(getApplicationContext(), String.valueOf(ha2[i]), Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(getApplicationContext(), "This is second array", Toast.LENGTH_LONG).show();
            if (x >= h)
            {
                a = h;
            } else
            {
                a = x;

            }
            //Toast.makeText(getApplicationContext(), Integer.toString(a), Toast.LENGTH_LONG).show();
            for (int i = 0; i < a; i++)
            { //c2[i]=s2[i];
               // f=f+1;
                if(flag==1||tag==1||tag==2){
                v=i;
                }
                else{
                  v=i+1;
                }

                //Toast.makeText(this,  Integer.toString(flag)+","+Integer.toString(tag), Toast.LENGTH_LONG).show();
                    //check(c);
               //if(flag==1||tag==1||tag==2)
               // {
              //      Toast.makeText(this,  Integer.toString(s2[v])+","+Integer.toString(ha2[i]), Toast.LENGTH_LONG).show();
                HashMap postData = new HashMap();
                postData.put("button3", "Login");
                postData.put("mobile", "android");
                postData.put("sid", Integer.toString(s2[v]));
                postData.put("hid", Integer.toString(ha2[i]));
                postData.put("dates", date1);
                postData.put("times", time1);


                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(Halls.this, postData);
                    loginTask.execute(Config.URL_Allot);
               // }
                c = s2[v];
                updateDuty(c);
                d = i;

            }

        }
        //Intent i = new Intent(getApplicationContext(), Clerk.class);
        //startActivity(i);
    }
    private void check(final Integer x){
        class Check extends AsyncTask<Void,Void,String> {

            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // loading = ProgressDialog.show(Halls.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               loading.dismiss();
                JSON_STRING = s;
                checked();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                 z=Integer.toString(x);
                String s = rh.sendGetRequestParam(Config.URL_check, z);
                return s;
            }
        }
        Check gd = new Check();
        gd.execute();
    }
    private void checked(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //Toast.makeText(this, "I am in Try", Toast.LENGTH_LONG).show();
            jsonObject = new JSONObject(JSON_STRING);
            //Toast.makeText(this, JSON_STRING, Toast.LENGTH_LONG).show();
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            int i;
            flag=0;
            tag=0;
            //Toast.makeText(this,  JSON_STRING, Toast.LENGTH_LONG).show();
            //for( i= 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(0);
                t[0]= jo.getString("type");
                du[0]=jo.getString("duty");
                if((t[0].equals("Assistant Professor"))&&(Integer.parseInt(du[0])<=4))
                {

                   flag=1;
                    //Toast.makeText(this,  Integer.toString(flag)+","+Integer.toString(tag), Toast.LENGTH_LONG).show();

                }
            else if((t[0].equals("Associate Professor"))&&(Integer.parseInt(du[0])<=3))
                {
                    tag=1;
                    //Toast.makeText(this,  Integer.toString(flag)+","+Integer.toString(tag), Toast.LENGTH_LONG).show();


                }
                else if((t[0].equals("Junior Professor"))&&(Integer.parseInt(du[0])<=8))
                {
                    tag=2;
                  //  Toast.makeText(this,  Integer.toString(flag)+","+Integer.toString(tag), Toast.LENGTH_LONG).show();

                }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
   private void getData1() {
        class GetData extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Halls.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_getData);
                return s;
            }
        }
        GetData gd = new GetData();
        gd.execute();

    }



    private void showEmployee(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            //Toast.makeText(this, "I am in Try", Toast.LENGTH_LONG).show();
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            int i;
            for( i= 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                s[i]= jo.getInt(Config.TAG_ID);
            }
            x=i;
           // Toast.makeText(this, Integer.toString(x), Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Halls.this,"Fetching Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_StafPresent);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void processFinish(String output)
    {
      //Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        if(output.equals("successData Saved"))
        { //Toast.makeText(this, "Staff Alloted", Toast.LENGTH_LONG).show();
        }
       else if(output.equals("Success"))
        {
            check(c);
        }
        else
        {
            Toast.makeText(this, "Couldn't Allot Staff", Toast.LENGTH_LONG).show();

            //Intent i=new Intent(getApplicationContext(),Sample.class);
            //startActivity(i);
        }
    }
}
