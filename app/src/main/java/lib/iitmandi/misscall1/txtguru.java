package lib.iitmandi.misscall1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dibya on 30-06-2016.
 */
public class txtguru extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String recipient = params[0];
        String message = params[1];
        String username = "iitmandiac_in";
        String password = "49392127";
        String originator = "UPDATE";
        int data = 0;

        Log.i("txtguru-recipient",recipient);
        Log.i("txtguru-sms",message);

        String requestUrl = null;
        String url1 = null;
        try {
            requestUrl = "https://www.txtguru.in/imobile/api.php?" +
                    "username=" + URLEncoder.encode(username, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8") +
                    "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
                    "&messagetype=SMS:TEXT" +
                    "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
                    "&originator=" + URLEncoder.encode(originator, "UTF-8") +
                    "&serviceprovider=GSMModem1" +
                    "&responseformat=html";
            url1 = "https://www.txtguru.in/imobile/api.php?username="+username+"&password="+password+"&source="+originator+"&dmobile="+recipient+"&message="+message;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


//        URL url = null;
//        try {
//            url = new URL(url1);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.setConnectTimeout(2000);
//            con.setReadTimeout(2000);

//            OutputStream os = con.getOutputStream();
//
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(input);
//            writer.flush();
//            writer.close();
//            os.close();
//
//            con.connect();
//
//            BufferedReader Reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            con.disconnect();
//
//            StringBuilder stringBuilder = new StringBuilder();
//            String line = null;
//
//            while((line = Reader.readLine()) != null)
//            {
//                stringBuilder.append(line + "\n");
//            }
//            String text = stringBuilder.toString();
//            return text;
//        }
//        catch (Exception e){
//            String err = (e.getMessage()==null)?"failed":e.getMessage();
//            Log.i("catch",err);
//            return null;
//        }

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(url1);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                System.out.print(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

    return data+"";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null && !s.isEmpty()) {
            Log.i("txtguru receved", s);
        }
        else{
            Log.i("txtguru receved", "null");
        }
    }
}
