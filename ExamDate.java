package com.example.v7.examinarsallotment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.Calendar;
import java.util.HashMap;

public class ExamDate extends AppCompatActivity  implements AsyncResponse {
    public Button  buttonAdd, buttonMore,choosedate,choosetime;
    public EditText halls,relevers,extra;
    Calendar c;
    public TextView examdate,examtime;
    public int year, month, day,hour,minu;
    Context context=this;
    //private TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exam_date);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        halls=(EditText)findViewById(R.id.examhall);
        extra=(EditText)findViewById(R.id.extra);
        relevers=(EditText)findViewById(R.id.relevers);
       examdate=(TextView)findViewById(R.id.textView8);
        examtime=(TextView)findViewById(R.id.textView81);
choosedate=(Button)findViewById(R.id.btnexmdate);
        choosetime=(Button)findViewById(R.id.btnexmtime);
//final String str=staffID.getText().toString().trim();
        // components from main.xml
        //resultText = (TextView) findViewById(R.id.result)

        buttonAdd=(Button)findViewById(R.id.button5);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String date1=Examdate.getText().toString().trim();


                    add();
                }
        });
        buttonMore=(Button)findViewById(R.id.button6);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examdate.setText("Choose Exam Date :");
                examtime.setText("Choose Exam Time :");
                halls.setText("");
                halls.setHint("Enter Total No. of Blocks");
                extra.setText("");
                extra.setHint("Extra Facutlty needed");
                relevers.setText("");
                relevers.setHint("No. of Relevers needed");


            }
        });
        choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);

            }
        });
        choosetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(111);

            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }
        else if (id == 111) {
            return new TimePickerDialog(this,myTimeListener,hour,minu,false);

        }


        return null;

    }

    // set time picker as current time

private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
examtime.setText(hourOfDay+":"+minute);
    }
};


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
        examdate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

    }

    @Override
    public void processFinish(String output)
    {
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        if(output.equals("Date Added"))
        { Toast.makeText(this, "Date Added", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Couldn't Add Staff", Toast.LENGTH_LONG).show();

            //Intent i=new Intent(getApplicationContext(),Sample.class);
            //startActivity(i);
        }
    }
    public void add(){
        HashMap postData = new HashMap();
        postData.put("button3", "Login");
        postData.put("mobile", "android");
        postData.put("exdates", examdate.getText().toString());
        postData.put("extimes",examtime.getText().toString());
        postData.put("halls", halls.getText().toString());
        postData.put("extra", extra.getText().toString());
        postData.put("relev", relevers.getText().toString());

        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(ExamDate.this, postData);
        loginTask.execute(Config.URL_Exam);
    }
}
