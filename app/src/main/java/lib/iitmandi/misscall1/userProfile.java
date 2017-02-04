package lib.iitmandi.misscall1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dibya on 04-07-2016.
 */
public class userProfile extends AppCompatActivity {

    public static String name;
    public static String phone;
    public static String message;
    TextView tname,tphone;
    EditText emessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        //getSupportActionBar().setTitle("Profile");

        tname = (TextView)findViewById(R.id.name);
        tphone = (TextView)findViewById(R.id.phone);
        emessage = (EditText)findViewById(R.id.message);

        tname.setText(name);
        tphone.setText(phone);
        emessage.setText(message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send) {
            Log.i("send clicked","true");
            message = (emessage.getText().toString());
            Log.i("clicked sms",message);
            try {
                new txtguru().execute("91"+phone, URLEncoder.encode(message, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
