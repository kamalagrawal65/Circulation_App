package lib.iitmandi.misscall1;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.sql.Time;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by dibya on 28-06-2016.
 */
class JSONTask extends AsyncTask<String, String,String> {

    String input = "";
    String cond = "";
    URL url;
    String CallingNumber=null;
//    String urlsms="http://192.168.32.1/missCall/sms.php";
    String urlsms="http://library.iitmandi.ac.in/missCall/sms.php";
//    String urltxtguru = "https://www.txtguru.in/imobile/api.php?username=iitmandiac_in&password=49392127&source=UPDATE";
    @Override
    protected String doInBackground(String... params) {
        cond = params[0];

        try {
            setQuery(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("check","1");
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            Log.i("check","2");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            OutputStream os = con.getOutputStream();
            Log.i("check","3");
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(input);
            writer.flush();
            writer.close();
            os.close();
            Log.i("check","4");
            con.connect();

            BufferedReader Reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            Log.i("check","5");
//            con.disconnect();

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            Log.i("check","6");
            while((line = Reader.readLine()) != null)
            {
                Log.i("check","loop");
                stringBuilder.append(line + "\n");
            }
            Reader.close();
            Log.i("check","7");
            Log.i("string builder",stringBuilder.toString());
            String text = stringBuilder.toString();
            return text;
        }
        catch (Exception e){
            String err = (e.getMessage()==null)?"failed":e.getMessage();
            Log.i("catch",err);
            return null;
        }

    }

    private void setQuery(String... params) throws UnsupportedEncodingException {
        switch (cond){
            case Query.SEND_NUMBER:
                String phoneNumber = params[1];
                CallingNumber = phoneNumber;
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                input += "&"+URLEncoder.encode("phno", "UTF-8") + "=" + URLEncoder.encode(phoneNumber.substring(3), "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
            case Query.MISSED_CALLS:
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
//            case Query.SEND_SMS:
//                String phno = params[1];
//                String sms = params[2];
//                input = "";
//                try {
//                    url = new URL(urltxtguru+"&dmobile="+phno+"&message="+sms);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//
//                break;
            case Query.USER_PROFILE:
                String position = params[1];
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                input += "&"+URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(position, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case Query.ALL_DATA:
                String name = params[1];
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                input += "&"+URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case Query.USER_PROFILE_2:
                String bid = params[1];
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                input += "&"+URLEncoder.encode("bid", "UTF-8") + "=" + URLEncoder.encode(bid, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case Query.GET_CATEGORY:
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case Query.GET_NUMBERS:
                String groups = params[1];
                input = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(cond, "UTF-8");
                input += "&"+URLEncoder.encode("groups", "UTF-8") + "=" + URLEncoder.encode(groups, "UTF-8");
                try {
                    url = new URL(urlsms);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        result(s);
    }

    private void result(String s) {
        switch(cond){
            case Query.SEND_NUMBER:
                result1(s);
                break;
            case Query.MISSED_CALLS:
                result2(s);
                break;
//            case Query.SEND_SMS:
//                result3(s);
//                break;
            case Query.USER_PROFILE:
                result3(s);
                break;
            case Query.ALL_DATA:
                result4(s);
                break;
            case Query.USER_PROFILE_2:
                result5(s);
                break;
            case Query.GET_CATEGORY:
                result6(s);
                break;
            case Query.GET_NUMBERS:
                result7(s);
                break;
            default:
                break;
        }
    }

    private void result7(String s) {
//        String str = s.replaceAll(",", "-");
        smsSent.numbers(s);

    }

    private void result6(String s) {
        String[] str = s.split(",");
        smsSent.addCheck(str);

    }

    private void result5(String s) {
        String firstname = "",surname = "",phone = "",tname = "";
        try {
            JSONObject main = new JSONObject(s);
            phone = main.getString("phone");
            tname = main.getString("tname");
            firstname = main.getString("fname");
            surname = main.getString("sname");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        userProfile.name = tname +" "+ firstname +" "+ surname;
        userProfile.phone = phone;
        userProfile.message = result1(s);

        Intent intent = new Intent(allUsers.context, userProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        allUsers.context.startActivity(intent);
    }


    private void result4(String s) {
        allUsers.json = s;
        allUsers.setlist();
    }

    private void result3(String s) {
        Log.i("result3",s);
        String firstname = "",surname = "",phone = "",titlename = "";
        try {
            JSONObject main = new JSONObject(s);
            phone = main.getString("phone");
            titlename = main.getString("tname");
            firstname = main.getString("fname");
            surname = main.getString("sname");
//            if(titlename == "" && firstname == "" && surname == ""){
//
//   firstname = "Not";
//                surname = "Registered";
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userProfile.name = titlename+" "+firstname +" "+ surname;
        userProfile.phone = phone;
        userProfile.message = result1(s);

        missedCalls.context.startActivity(new Intent(missedCalls.context, userProfile.class));
    }

//    private void result3(String s) {
//        Log.i("txtguru","result3");
//    }

    private void result2(String s) {
        missCallList.json = s;
        missedCalls.listView.setAdapter(new missCallList(missedCalls.context));
    }

    private String result1(String s) {
        String message = "",phno = CallingNumber;
        String nametitle="",firstname="",surname="";
        if(s != null && !s.isEmpty()) {
            Log.i("received", s);

        try {
            JSONObject main = new JSONObject(s);
            nametitle = main.getString("tname");
            firstname = main.getString("fname");
            surname = main.getString("sname");


            message = "Dear "+nametitle+" "+firstname+" "+surname;
            JSONArray all = main.getJSONArray("data");
            if(all.length()==0){
                message += " no books ";
            }
            for (int i=0; i < all.length(); i++){
                JSONObject row = all.getJSONObject(i);
                String title = row.getString("title");
                String date_due = row.getString("date_due");
                String date = date_due.substring(8,10)+"-"+date_due.substring(5,7)+"-"+date_due.substring(0,4);

                if(all.length()==1){
                    if(i==0){
                        message += " book ";
                    }
                    message += title+" (due date:"+date+") ";
                }
                else if(all.length()>1){
                    if(i==0){
                        message += " books ";
                    }
                    message += (i+1)+". "+title+" (due date:"+date+") ";
                }

            }
            message += "has been issued. Thanks, Central Library, IIT Mandi";

//            if(nametitle == "" && firstname == "" && surname == ""){
//                message = "Your mobile number has not been registered";
//            }

            byte[] str = null;
            str = message.getBytes();
            try {
                message= new String(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

//            message = message.replaceAll(" ", "+");
        } catch (JSONException e) {
            e.printStackTrace();
        }
            if(nametitle+firstname+surname == ""){
                message = "Sorry. This mobile number is not registered. Please contact Central Library, Thanks";
            }

            Log.i("message",message);

            if(phno != null && !phno.isEmpty()) {
                try {
                    new txtguru().execute(phno, URLEncoder.encode(message, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            Log.i("received", "null");
        }
        return message;
    }


}

