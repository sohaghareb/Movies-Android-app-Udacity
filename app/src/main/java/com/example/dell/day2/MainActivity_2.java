package com.example.dell.day2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
 * {@link MainActivity_2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivity_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivity_2 extends Fragment {
    //////////////////
    ArrayList<Trailer> list_1=null;
    ArrayList<Review> list=null;
    DBHelper db_helper;
    Movie movie;
    private RequestQueue queue;
    /////////////////

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
     * @return A new instance of fragment MainActivity_2.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivity_2 newInstance(String param1, String param2) {
        MainActivity_2 fragment = new MainActivity_2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivity_2() {
        // Required empty public constructor
    }
    public void updateInfo(Movie movie){
        ///////////////////////////added
        db_helper=new DBHelper(getActivity());
        /////////////////////////////get the trailers
        queue = Volley.newRequestQueue(getActivity());
        //////////////////////// the 2nd call
        String url_website1="http://api.themoviedb.org/3/movie/"+movie.getId()+"/reviews?&api_key=b228bd72e4930ccb76ec675016595c3a";
        final Movie finalMovie1 = movie;
        final JsonObjectRequest request1=new JsonObjectRequest(Request.Method.GET, url_website1, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String data=response.getString("results");
                    Gson gson=new Gson();
                    list=gson.fromJson(data, new TypeToken<ArrayList<Review>>() {
                    }.getType());
                    ListView z=(ListView)getActivity().findViewById(R.id.trailer_list);
                    TrailerReviewAdapter adapter=new TrailerReviewAdapter(list_1,list,getActivity(), finalMovie1);
                    z.setAdapter(adapter);
                    z.setItemsCanFocus(true);


                } catch (JSONException e) {
                    // Toast.makeText(MainActivity3Activity.this, movie.getId(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




                // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(MainActivity3Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };







        String url_website="http://api.themoviedb.org/3/movie/"+movie.getId()+"/videos?&api_key=b228bd72e4930ccb76ec675016595c3a";
        final Movie finalMovie = movie;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url_website, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String data=response.getString("results");
                    Gson gson=new Gson();
                    list_1=gson.fromJson(data, new TypeToken<ArrayList<Trailer>>() {
                    }.getType());

                    queue.add(request1);
                    ////////////////////////

                    // Toast.makeText(Main2Activity.this, list.get(0).getId(), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), finalMovie.getId(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




                // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        ///////////////////////////

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity_2, container, false);
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

}
