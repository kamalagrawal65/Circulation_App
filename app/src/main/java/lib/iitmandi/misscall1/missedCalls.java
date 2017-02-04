package lib.iitmandi.misscall1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by dibya on 28-06-2016.
 */
public class missedCalls extends AppCompatActivity {
    public static ListView listView;
    public static Context context;
//    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missedcalls);

        //getSupportActionBar().setTitle("Missed Call Log");

        context = this;
        new JSONTask().execute(Query.MISSED_CALLS);

        listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                toast = Toast.makeText(context,position+"",Toast.LENGTH_SHORT);
//                toast.show();
                new JSONTask().execute(Query.USER_PROFILE,position+"");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        listView.setAdapter(new missCallList(this));
    }


}
