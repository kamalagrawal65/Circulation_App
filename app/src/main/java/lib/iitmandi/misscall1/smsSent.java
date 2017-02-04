package lib.iitmandi.misscall1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.Inflater;

import static android.text.TextUtils.substring;

/**
 * Created by dibya on 28-06-2016.
 */
public class smsSent extends AppCompatActivity {

    String checked;
    EditText to;
    static EditText sms;
    static String[] str1;
    static Context context;
    static LinearLayout featuresTable;
    static String[] numbers;
//    CheckBox std,fac,stf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_sent);
        //getSupportActionBar().setTitle("Send Message");
        context = getApplicationContext();
        new JSONTask().execute(Query.GET_CATEGORY);

        featuresTable = (LinearLayout) findViewById(R.id.check);
        to = (EditText) findViewById(R.id.to);
        sms = (EditText) findViewById(R.id.sms);
//        std = (CheckBox) findViewById(R.id.student);
//        stf = (CheckBox) findViewById(R.id.staff);
//        fac = (CheckBox) findViewById(R.id.faculty);

//        sms.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    if(event.getRawX() >= (sms.getRight() - sms.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//                        send();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

    }

    public static void addCheck(String[] str){

        str1 = new String[str.length];
        str1 = str;
        for(int i=0;i<str.length;i++){
            CheckBox feature1 = new CheckBox(context);
            feature1.setId(i);
            feature1.setText(str[i]);
            feature1.setTextColor(Color.DKGRAY);
            feature1.setPadding(0, 0, 10, 0);
            featuresTable.addView(feature1);
        }
    }

    public void send(View v){
        checked = "";

        for(int i=0;i<str1.length;i++){
            CheckBox c = (CheckBox)findViewById(i);
            if(c.isChecked()){
                checked += " "+c.getText().toString()+",";
            }
        }



        if(checked != ""){
            checked = checked.substring(0,checked.length()-1);
//            Toast.makeText(this,"checked",Toast.LENGTH_SHORT).show();
            open(v);
        }
        else{
            try {
                new txtguru().execute("91"+to.getText().toString(), URLEncoder.encode(sms.getText().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to send this sms to"+checked+"?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                Toast.makeText(getApplicationContext(),"You clicked yes button",Toast.LENGTH_LONG).show();

                //send group sms
                new JSONTask().execute(Query.GET_NUMBERS,checked);

            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void numbers(String str) {
        if(str.length()>0) {
            numbers = new String[(str.length() / 1300) + 1];
            int start = 0;
            while (str.length()-start >= 1299) {
                Log.i("start",start+"");
                numbers[start / 1300] = substring(str, start, start + 1299);
                start = start + 1300;
            }
            numbers[start / 1300] = substring(str, start, str.length() - 1);

            sendGroupSms();
        }
    }

    private static void sendGroupSms() {

        for(int i=0;i<numbers.length;i++){
            Log.i("number"+i,numbers[i]);
            try {
                new txtguru().execute(numbers[i],URLEncoder.encode(sms.getText().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }
}
