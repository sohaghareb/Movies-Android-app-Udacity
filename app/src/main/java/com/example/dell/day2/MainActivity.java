package com.example.dell.day2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements  MainActivity_1.OnMovieSelectedListener ,
        MainActivity_1.OnFragmentInteractionListener,MainActivity_2.OnFragmentInteractionListener {

    protected void onCreate(Bundle savedInstanceState) {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMovieSelected(Movie movie) {
        MainActivity_2 fragent_b= (MainActivity_2) getFragmentManager().findFragmentById(R.id.b);
        if(fragent_b!=null)//tablet
        {
            fragent_b.updateInfo(movie);
            Toast.makeText(this, "i am a tablet ", Toast.LENGTH_LONG).show();
        }
        else{//handset
            Intent i = new Intent(this, Main2Activity.class);
            Intent user = i.putExtra("movie", movie);
            startActivity(i);
        }

    }
}
