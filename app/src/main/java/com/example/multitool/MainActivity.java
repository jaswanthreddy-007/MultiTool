package com.example.multitool;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.multitool.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.multitool.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ImageView iv5;
    EditText et5;
    TextView textView5;
    EditText et;
    TextView tv;
    TextView tv2;
    TextView textView;
    ImageView iv;
    TextView tv4;
    TextView tv5;
    TextView tv8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



    }
    public void getv(View view) {
        et5 = findViewById(R.id.et5);

        iv5 = findViewById(R.id.iv5);
        textView5 = findViewById(R.id.textView5);
        String text = et5.getText().toString();
        String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+text;
        Glide.with(MainActivity.this)
                .load(url)
                .apply(new RequestOptions().override(500,500))
                .into(iv5);
    }

    public void get(View view) {
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        textView = findViewById(R.id.textView);
        iv = findViewById(R.id.iv);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv8 = findViewById(R.id.tv8);
        String city = et.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4e407af694e11e4b962324ccbb168d58";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {

                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject weather1 = weather.getJSONObject(0);

                    JSONObject obj1 = response.getJSONObject("main");
                    String temp = obj1.getString("temp");
                    String desc = weather1.getString("description");
                    String  icid = weather1.getString("icon");

                    String town =  response.getString("name");


                    JSONObject windy = response.getJSONObject("wind");
                    String wind = windy.getString("speed");

                    String url2 = "https://wttr.in/"+city+".png";

                    Glide.with(MainActivity.this)
                            .load(url2)
                            .apply(new RequestOptions().override(200,200))
                            .into(iv);
                    float tempi = Float.parseFloat(temp);
                    tempi-=273.5;
                    DecimalFormat df2 = new DecimalFormat("#.0");
                    float tempi2 = Float.valueOf(df2.format(tempi));
                    tv8.setText("Temp");
                    tv.setText(tempi2+" °c");
                    tv2.setText(desc);
                    tv5.setText(town);
                    float ws = Float.parseFloat(wind);
                    DecimalFormat df = new DecimalFormat("#.0");
                    float wsp = Float.valueOf(df.format(ws));

                    tv4.setText("Wind : "+ wsp +" m/s - " );

                }

                catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                Glide.with(MainActivity.this)
                        .load(R.drawable.saddy)
                        .apply(new RequestOptions().override(300,300))
                        .into(iv);
                tv2.setText(null);
                tv4.setText(null);
                tv5.setText(null);
                tv.setText("??");
                Toast.makeText(MainActivity.this, "enter valid location", Toast.LENGTH_SHORT).show();
            }


        });
        queue.add(request);

    }





    public void getcomp(View view)
    {
        TextView tv1 = findViewById(R.id.tv50);
        TextView tv2 = findViewById(R.id.tv51);
        TextView tv3 = findViewById(R.id.tv52);
        TextView tv4 = findViewById(R.id.tv53);

        String urlcom = "https://kontests.net/api/v1/code_chef";

        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, urlcom, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int flag = 0;
                    JSONObject obj = null;
                        for(int i=0;i<response.length();i++)
                        {
                             obj = response.getJSONObject(i);
                            String timing = obj.getString("in_24_hours");
                            if(timing=="Yes")
                            {
                                flag=1;
                                break;
                            }

                        }
                        if(flag!=0)
                        {
                            tv1.setText("No UpComing Events 😢😢");
                            tv2.setText(null);
                            tv3.setText(null);
                            tv4.setText(null);

                        }
                        else {
                            String contname = obj.getString("name");
                            String link = obj.getString("url");
                            String time = obj.getString("start_time");
                            tv1.setText("UpComing Events are ... ");
                            tv2.setText("Name : "+contname);
                            tv3.setMovementMethod(LinkMovementMethod.getInstance());

                            tv3.setText(" Contest Link : "+link);
                            tv3.setMovementMethod(LinkMovementMethod.getInstance());

                            tv4.setText("Time : "+time);



                        }


                }


                catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                tv1.setText("Network Problem !! 😢😢");
                tv2.setText(null);
                tv3.setText(null);
                tv4.setText(null);
            }
        } );
        queue2.add(request2);

    }




}