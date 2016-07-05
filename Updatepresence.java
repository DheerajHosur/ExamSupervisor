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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Updatepresence extends AppCompatActivity implements AsyncResponse {
    Button btnThumbsup,btnThumbsdown,btnsubmit;
    String user, last,curdate;
    EditText reason;
    Date date, currentdate;
    private String JSON_STRING,dateParts1[]=new String[3],dateParts[]=new String[3];
    public Integer month,day, year,curmonth,curday,curyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepresence);
        user=getIntent().getStringExtra("uname");

        Calendar c = Calendar.getInstance();
        curyear = c.get(Calendar.YEAR);
        curmonth = c.get(Calendar.MONTH);
       curday = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        curdate = df.format(c.getTime());

        btnsubmit=(Button)findViewById(R.id.submit1);
        btnsubmit.setVisibility(View.INVISIBLE);
        reason=(EditText)findViewById(R.id.textView101);
        reason.setVisibility(View.INVISIBLE);
        btnThumbsup=(Button)findViewById(R.id.button8);
        btnThumbsdown=(Button)findViewById(R.id.button9);
        btnThumbsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curyear <= year && curmonth <= month && curday <= day) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    HashMap postData = new HashMap();
                    postData.put("id", user);
                    Toast.makeText(getApplicationContext(), postData.toString(), Toast.LENGTH_LONG).show();
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(Updatepresence.this, postData);
                    loginTask.execute(Config.URL_present);
                } else {
                    Toast.makeText(getApplicationContext(), "Dotn Worry!! Your status has been set to Present", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnThumbsdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curyear<=year && curmonth<=month && curday<=day){
                btnsubmit.setVisibility(View.VISIBLE);
                reason.setVisibility(View.VISIBLE);}
                else{
                    Toast.makeText(getApplicationContext(),"Sorry!! Time's UP. Your status has been already set to Present",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<>();
                HashMap postData = new HashMap();
                postData.put("id", user);
                postData.put("reason", reason.getText().toString());
                Toast.makeText(getApplicationContext(), postData.toString(), Toast.LENGTH_LONG).show();
                PostResponseAsyncTask loginTask =
                        new PostResponseAsyncTask(Updatepresence.this, postData);
                loginTask.execute(Config.URL_Notpresent);
            }
        });
        getJSON();
        //setStatus();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), JSON_STRING, Toast.LENGTH_LONG).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                last = jo.getString("last");
                String[] items1 = last.split("-");
                year=Integer.parseInt(items1[0]);
                month=Integer.parseInt(items1[1]);
                day=Integer.parseInt(items1[2]);
                Toast.makeText(getApplicationContext(),Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day) , Toast.LENGTH_LONG).show();
            }
            //setStatus();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void setStatus(){
        if(curyear>year && curmonth>month && curday>day){
            HashMap<String,String> hashMap = new HashMap<>();
            HashMap postData = new HashMap();
            postData.put("id",user );
            Toast.makeText(getApplicationContext(), postData.toString(), Toast.LENGTH_LONG).show();
            PostResponseAsyncTask loginTask =
                    new PostResponseAsyncTask(Updatepresence.this, postData);
            loginTask.execute(Config.URL_setpresent);
            Toast.makeText(getApplicationContext(),"Status has been set to Present",Toast.LENGTH_LONG).show();}
        else{

        }
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Updatepresence.this,"Fetching Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_lastdate);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void processFinish(String s) {
        if(s.equals("success")){
            Toast.makeText(getApplicationContext(),"Status Updated",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Status Unchanged",Toast.LENGTH_LONG).show();
        }

    }
}
