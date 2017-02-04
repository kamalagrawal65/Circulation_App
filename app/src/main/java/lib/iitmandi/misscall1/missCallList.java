package lib.iitmandi.misscall1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dibya on 28-06-2016.
 */
public class missCallList extends BaseAdapter {
    public static String json;
    ArrayList<singleRow> list;
    Context context;

    TextView name,phno,time;


    missCallList(Context context){
        this.context = context;

        list = new ArrayList<singleRow>();

        try {
            Log.i("try","true");
//            JSONObject main = new JSONObject(json);
//            Log.i("main",main+"");
            JSONArray all = new JSONArray(json);


            Log.i("all",all+"");

            for(int i=0; i < all.length(); i++) {
                JSONObject row = all.getJSONObject(i);
                String name = row.getString("name");
                String phno = row.getString("phoneNo");
                String time = row.getString("timeStamp");
                singleRow obj = new singleRow(name,phno,time);
                list.add(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_row, parent, false);

        name = (TextView)row.findViewById(R.id.name);
        phno = (TextView)row.findViewById(R.id.phno);
        time = (TextView)row.findViewById(R.id.time);

        name.setText(list.get(position).name);
        phno.setText(list.get(position).phno);
        time.setText(list.get(position).time);

        return row;
    }
}
