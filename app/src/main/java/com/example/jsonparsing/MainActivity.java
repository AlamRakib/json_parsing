package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;

    HashMap<String,String> hashMap;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBarId);
        listView = findViewById(R.id.listViewId);


        String url = "https://rakibalam.000webhostapp.com/apps/demo.json";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                progressBar.setVisibility(View.GONE);

                try {

                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobile");
                        String image_url = jsonObject.getString("image_url");

                        hashMap = new HashMap<>();
                        hashMap.put("name",name);
                        hashMap.put("mobile",mobile);
                        hashMap.put("image_url",image_url);
                        arrayList.add(hashMap);


                    }

                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);


    }

    public  class  MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.sample_view,viewGroup,false);

            TextView nameTextView = myView.findViewById(R.id.nameTextViewId);
            TextView mobileTextView = myView.findViewById(R.id.mobileTextViewId);
            ImageView coverImage = myView.findViewById(R.id.CoverInameId);

            HashMap<String,String> hashMap = arrayList.get(position);
            String name =hashMap.get("name");
            String mobile =hashMap.get("mobile");
            String image_url = hashMap.get("image_url");

            nameTextView.setText(name);
            mobileTextView.setText(mobile);

            Picasso.get()
                    .load(image_url)
                    .into(coverImage);

            return myView;
        }
    }
}