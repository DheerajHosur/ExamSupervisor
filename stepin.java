package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class stepin extends AppCompatActivity  implements AsyncResponse {
    Context context;
    public  EditText etUsername;

    public String user, last,curdate;
    EditText reason;
    Date date, currentdate;
    private String JSON_STRING;
    public Integer month,day, year,curmonth,curday,curyear;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepin);
        context= this;

        etUsername = (EditText)findViewById(R.id.editText);
        final EditText etPassword = (EditText)findViewById(R.id.editText2);
        Button btnLogin = (Button)findViewById(R.id.button);
        Calendar c = Calendar.getInstance();
        curyear = c.get(Calendar.YEAR);
        curmonth = c.get(Calendar.MONTH);
        curday = c.get(Calendar.DAY_OF_MONTH);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = etUsername.getText().toString();
                //Toast.makeText(getApplicationContext(), user, Toast.LENGTH_LONG).show();
                getJSON();
                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("txtUsername", etUsername.getText().toString());
                postData.put("txtPassword", etPassword.getText().toString());
                etUsername.setText("");
                etPassword.setText("");
                PostResponseAsyncTask loginTask =
                        new PostResponseAsyncTask(stepin.this, postData);
                loginTask.execute(Config.URL_LOGIN);
            }
        });


    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            //Toast.makeText(getApplicationContext(), JSON_STRING, Toast.LENGTH_LONG).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                last = jo.getString("last");
                String[] items1 = last.split("-");
                year=Integer.parseInt(items1[0]);
                month=Integer.parseInt(items1[1]);
                day=Integer.parseInt(items1[2]);
                //Toast.makeText(getApplicationContext(),Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day) , Toast.LENGTH_LONG).show();
            }
            setStatus();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void setStatus(){
        if(curyear>year || curmonth>month || curday>day){
            HashMap<String,String> hashMap = new HashMap<>();
            HashMap postData = new HashMap();
            postData.put("id",user );
           // Toast.makeText(getApplicationContext(), postData.toString(), Toast.LENGTH_LONG).show();
            PostResponseAsyncTask loginTask =
                    new PostResponseAsyncTask(stepin.this, postData);
            loginTask.execute(Config.URL_setpresent);
            //Toast.makeText(getApplicationContext(),"All Status has been set to Present",Toast.LENGTH_LONG).show();
            }

    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(stepin.this,"Fetching Data","Wait...",false,false);
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
    public void processFinish(String output)
    {
        //Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        if(output.equals("P"))
        {
            Toast.makeText(this, "Welcome Principal!!", Toast.LENGTH_LONG).show();
            Intent i=new Intent(context ,Prince.class);
            startActivity(i);


        }
        else if(output.equals("H"))
        {
            Toast.makeText(this, "Welcome HOD!!", Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,HOD.class);
            startActivity(i);


        }
        else if (output.equals("C"))
        {
            Toast.makeText(this, "Welcome Clerk!!", Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,Clerk.class);
            startActivity(i);


        }
        else if (output.equals("S"))
        {
            Toast.makeText(this, "Welcome Faculty!!", Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,Faculty.class);
            i.putExtra("uname",user);
            startActivity(i);


        }

        else{
            Toast.makeText(this, "Unauthorized Access!!", Toast.LENGTH_LONG).show();}

    }}



