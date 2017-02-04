package lib.iitmandi.misscall1;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dibya on 28-06-2016.
 */
public class allUsers extends AppCompatActivity {

    static String json = null;
    static ListView listView;
    static Context context;
    static String[] bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_users);
        //getSupportActionBar().setTitle("Users List");
        context = getApplicationContext();

        listView = (ListView)findViewById(R.id.list_result);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new JSONTask().execute(Query.USER_PROFILE_2,bid[position]);
            }
        });
    }


    public static void setlist(){
        String[] names = new String[0];
        if(json != null){
            try {
                JSONObject main = new JSONObject(json);
                JSONArray all = main.getJSONArray("names");
                names = new String[all.length()];
                bid = new String[all.length()];
                for(int i=0;i<all.length();i++){
                    names[i]=all.getJSONObject(i).getString("tname") + " " +all.getJSONObject(i).getString("firstname") + " " +all.getJSONObject(i).getString("surname");
                    bid[i]=all.getJSONObject(i).getString("borrowernumber");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(context,R.layout.all_row,names);
        listView.setAdapter(adapter);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        new JSONTask().execute(Query.ALL_DATA, "");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                new JSONTask().execute(Query.ALL_DATA,query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>=0) {
                    new JSONTask().execute(Query.ALL_DATA, newText);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
