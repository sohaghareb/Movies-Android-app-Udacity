package com.example.dell.day2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivity_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivity_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivity_1 extends Fragment implements AdapterView.OnItemSelectedListener{
    public String url;
    DBHelper db_helper;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    OnMovieSelectedListener my_listner;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//for the spinner
        ////////////////// added from activity 1
        TextView x=(TextView)(view);
       // Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();
        SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, 0);
        String value = preferences.getString(MyPREFERENCES, null);
        value=preferences.getString(MyPREFERENCES, null);
        // Toast.makeText(this,"now it is set  "+value,Toast.LENGTH_LONG).show();
        if(x.getText().equals("Popular")){
            /// Toast.makeText(this,"it is null",Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MyPREFERENCES, "Popular");
            editor.commit();
            //Toast.makeText(this, "done by the select ", Toast.LENGTH_SHORT).show();
            url="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b228bd72e4930ccb76ec675016595c3a";
            /////
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String data=response.getString("results");
                        Gson gson=new Gson();
                        final  ArrayList<Movie> list=gson.fromJson(data, new TypeToken<ArrayList<Movie>>() {
                        }.getType());

                        //Toast.makeText(MainActivity.this, list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        GridView z=(GridView)getView().findViewById(R.id.gridview);
                        MovieAdapter adapter;
                        adapter = new MovieAdapter(list,getActivity());
                        z.setAdapter(adapter);

                        z.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v,
                                                    int position, long id) {
//                                Intent i = new Intent(getActivity(), Main2Activity.class);
//                                Intent user = i.putExtra("movie", list.get(position));
//                                startActivity(i);
                                my_listner.onMovieSelected(list.get(position));

                            }
                        });






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("Accept","application/json");
                    return map;
                }
            };


            queue.add(request);


            /////
        }
        else if(x.getText().equals("HighestRated")){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MyPREFERENCES, "HighestRated");
            editor.commit();
            url="http://api.themoviedb.org/3/discover/movie?sort_by=ratings.desc&api_key=b228bd72e4930ccb76ec675016595c3a";
            ////
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String data=response.getString("results");
                        Gson gson=new Gson();
                        final  ArrayList<Movie> list=gson.fromJson(data, new TypeToken<ArrayList<Movie>>() {
                        }.getType());

                        //Toast.makeText(MainActivity.this, list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        GridView z=(GridView)getView().findViewById(R.id.gridview);
                        MovieAdapter adapter=new MovieAdapter(list,getActivity());
                        z.setAdapter(adapter);

                        z.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v,
                                                    int position, long id) {
//                                Intent i = new Intent(getActivity(), Main2Activity.class);
//                                Intent user = i.putExtra("movie", list.get(position));
//                                startActivity(i);
                                my_listner.onMovieSelected(list.get(position));

                            }
                        });






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("Accept","application/json");
                    return map;
                }
            };


            queue.add(request);


            ////
        }
        ///
        else {
            //Toast.makeText(this,"Favorites",Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MyPREFERENCES, "Favorites");
            editor.commit();
            GridView z=(GridView)getView().findViewById(R.id.gridview);
            final ArrayList<Movie>list=db_helper.getAllCotacts();
            final MovieAdapter adapter=new MovieAdapter(list,getActivity());
            z.setAdapter(adapter);

            z.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
//                    Intent i = new Intent(getActivity(), Main2Activity.class);
//                    Intent user = i.putExtra("movie", list.get(position));
//                    startActivity(i);
                    my_listner.onMovieSelected(list.get(position));

                }
            });


        }
        ////////////////

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainActivity_1.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivity_1 newInstance(String param1, String param2) {
        MainActivity_1 fragment = new MainActivity_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivity_1() {
        // Required empty public constructor
    }

    @Override
    public void  onActivityCreated (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getActivity(), "hhhhhh ", Toast.LENGTH_LONG).show();

        my_listner = (OnMovieSelectedListener)getActivity();
        ///////////////////////////// copied from main activity

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db_helper = new DBHelper(getActivity());

        //////spinner
        Spinner spinner = (Spinner)getView().findViewById(R.id.order_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.order_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //////////////////// using volley
        ////////////setting the url
        SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, 0);
        String value = preferences.getString(MyPREFERENCES, null);
        ///////////////////////////////////////////////////////////////// setting spinner default value
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        if(value!=null) {
            int spinnerPosition = myAdap.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
        ////////////////////////////////////////////////////////////////////////////
        //////////////////////
       // Toast.makeText(this, "hhhhhh " + value, Toast.LENGTH_SHORT).show();
        if(value==null||!value.equals("Favorites")) {
            //Toast.makeText(this, "hhhhhh "+value, Toast.LENGTH_SHORT).show();
            if (value == null || value.equals("Popular")) {
                //Toast.makeText(this, "iam popular or null", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyPREFERENCES, "Popular");
                editor.commit();
                url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b228bd72e4930ccb76ec675016595c3a";
            }
            else {
                url = "http://api.themoviedb.org/3/discover/movie?sort_by=ratings.desc&api_key=b228bd72e4930ccb76ec675016595c3a";
            }
            /////////////////////

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String data = response.getString("results");
                        Gson gson = new Gson();
                        final ArrayList<Movie> list = gson.fromJson(data, new TypeToken<ArrayList<Movie>>() {
                        }.getType());

                        //Toast.makeText(MainActivity.this, list.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        GridView z = (GridView) getView().findViewById(R.id.gridview);
                        z.setFadingEdgeLength(0);
                        MovieAdapter adapter = new MovieAdapter(list, getActivity());
                        z.setAdapter(adapter);

                        z.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v,
                                                    int position, long id) {
//                                Intent i = new Intent(getActivity(), Main2Activity.class);
//                                Intent user = i.putExtra("movie", list.get(position));
//                                startActivity(i);
                                my_listner.onMovieSelected(list.get(position));

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Accept", "application/json");
                    return map;
                }
            };


            queue.add(request);
        }
        else{
            GridView z=(GridView)getView().findViewById(R.id.gridview);
            final ArrayList<Movie>list=db_helper.getAllCotacts();
            final MovieAdapter adapter1=new MovieAdapter(list,getActivity());
            z.setAdapter(adapter1);

            z.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
//                    Intent i = new Intent(getActivity(), Main2Activity.class);
//                    Intent user = i.putExtra("movie", list.get(position));
//                    startActivity(i);
                    my_listner.onMovieSelected(list.get(position));

                }
            });

        }



        ////////////////////////////
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity_1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
///////////////////////added from activity1
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
///////////////////////////////////

 public interface OnMovieSelectedListener {
        public  void onMovieSelected(Movie movie);
    }

public void onAttache(Activity activity){
    super.onAttach(activity);
    try {
        my_listner = (OnMovieSelectedListener) activity;
    }
    catch(Exception e){

    }

}
}


