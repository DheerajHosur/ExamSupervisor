package com.example.v7.examinarsallotment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Notice extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private EditText notice;
    public TextView lastdate;
    Calendar c;
    public int year, month, day;
    private Button buttonDelete,choose;
    private String JSON_STRING,Phone[]=new String[500],noticetext;
    Integer n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
         c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
         notice=(EditText)findViewById(R.id.ednotice);
        Button s=(Button) findViewById(R.id.btnnotice);
        choose=(Button)findViewById(R.id.choose);
        lastdate=(TextView)findViewById(R.id.lastdate);
        listView=(ListView)findViewById(R.id.listView3);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                //String str=name.getText().toString();
                postData.put("s", "Login");
                //postData.put("mobile", "android");
                postData.put("notice", notice.getText().toString());
                postData.put("date",lastdate.getText().toString());
                noticetext=notice.getText().toString()+" : "+lastdate.getText().toString();


                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(Notice.this, postData);
                loginTask.execute(Config.URL_Notice);
                notice.setText("");


                getNotice();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);

            }
        });
        getJSON();



    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }


        return null;

    }

    // set time picker as current time




    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };


    private void showDate(int year, int month, int day) {
        lastdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

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
                String note = jo.getString("notice");

                HashMap<String,String> employees = new HashMap<>();
                employees.put("notice",note);

                list.add(employees);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Notice.this, list, R.layout.noticelist,
                new String[]{"notice"},
                new int[]{R.id.textView11});

        listView.setAdapter(adapter);
    }

    private void showNumbers(){
        JSONObject jsonObject = null;
       // ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), JSON_STRING, Toast.LENGTH_LONG).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                Phone[i] = jo.getString("Phone");
                n=n+1;
                //HashMap<String,String> employees = new HashMap<>();
                //employees.put("Phone",Phone);

                //list.add(employees);
            }

            sendMessage();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*ListAdapter adapter = new SimpleAdapter(
                Notice.this, list, R.layout.noticelist,
                new String[]{"notice"},
                new int[]{R.id.textView11});

        listView.setAdapter(adapter);*/
    }
    private void sendMessage(){
        Toast.makeText(getApplicationContext(), Integer.toString(n), Toast.LENGTH_LONG).show();
        try {
        for(int i=0;i<n;i++){
        String messageText = noticetext;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Phone[i], null, messageText + "/n Note: Failing in updating, will be considered as Present by default!! ", null, null);}
        Toast.makeText(getApplicationContext(), "Notification Sent", Toast.LENGTH_LONG).show();}
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Couldn't Send notification", Toast.LENGTH_LONG).show();
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Notice.this,"Fetching Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_Notice);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void getNotice(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Notice.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;

                showNumbers();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_Numbers);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void processFinish(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }
}
