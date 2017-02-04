package lib.iitmandi.misscall1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button startBtn,stopBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean started;
//    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("Missed Call");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008080")));
        initialize();
    }

    public void initialize(){
        sharedPreferences = getSharedPreferences("missCall",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        started = sharedPreferences.getBoolean("start", false);
//        toast = Toast.makeText(this, "App Running : " + started, Toast.LENGTH_SHORT);
//        toast.show();
        startBtn = (Button) findViewById(R.id.start);
        stopBtn = (Button) findViewById(R.id.stop);
        if(started){
            startBtn.setTextColor(Color.argb(255,34,139,34));
        }
        else{
            stopBtn.setTextColor(Color.RED);
        }
    }

    public void start(View v){
        editor.putBoolean("start",true).apply();
        startBtn.setTextColor(Color.argb(255,34,139,34));
        stopBtn.setTextColor(Color.BLACK);
    }
    public void stop(View v){
        editor.putBoolean("start",false).apply();
        startBtn.setTextColor(Color.BLACK);
        stopBtn.setTextColor(Color.RED);
    }
    public void missedCall(View v){
//        toast.cancel();
//        toast = Toast.makeText(this,"Missed Calls",Toast.LENGTH_SHORT);
//        toast.show();
        Intent intent = new Intent(this,missedCalls.class);
        startActivity(intent);
    }
    public void smsSent(View v){
//        toast.cancel();
//        toast = Toast.makeText(this,"sms sent",Toast.LENGTH_SHORT);
//        toast.show();
        Intent intent = new Intent(this,smsSent.class);
        startActivity(intent);
    }
    public void allUsers(View v){
//        toast.cancel();
//        toast = Toast.makeText(this,"all users",Toast.LENGTH_SHORT);
//        toast.show();
        Intent intent = new Intent(this,allUsers.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
