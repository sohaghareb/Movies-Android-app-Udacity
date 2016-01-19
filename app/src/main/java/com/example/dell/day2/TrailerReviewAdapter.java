package com.example.dell.day2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dell on 09/01/2016.
 */
public class TrailerReviewAdapter extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    ArrayList<Trailer> trailers;
    ArrayList<Review> reviews;
    Activity activity;
    LayoutInflater inflater;
    Movie movie;
    DBHelper db_helper;
    public TrailerReviewAdapter(ArrayList<Trailer> t,ArrayList<Review> r,Activity c,Movie m){
        trailers=t;
        reviews=r;
        activity=c;
        inflater=activity.getLayoutInflater();
        movie=m;
        db_helper=new DBHelper(c);
    }
    @Override
    public int getCount() {
        return 1+trailers.size()+reviews.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position==0){
            //the header
            //if(convertView==null)
             convertView=inflater.inflate(R.layout.row_header,null);
            //////
            TextView title=(TextView)convertView.findViewById(R.id.title);
            title.setText(movie.getTitle());
            title.setText(movie.getTitle());

            TextView description=(TextView)convertView.findViewById(R.id.description);
            Toast.makeText(activity,movie.getOverview(), Toast.LENGTH_SHORT).show();
            if(movie.getOverview()!=null||!movie.getOverview().equals(""))
                description.setText(movie.getOverview());
            else
                description.setText("no description available ");

            TextView release=(TextView)convertView.findViewById(R.id.release_date);
            release.setText("Release Date is  "+(CharSequence) movie.getReleaseDate());


            TextView average=(TextView)convertView.findViewById(R.id.average);
            average.setText("average "+ movie.getVoteAverage()+"/10");

            ImageView imageView = (ImageView) (convertView.findViewById(R.id.imageView));
            String url="http://image.tmdb.org/t/p/w185/"+movie.getPosterPath();
            Picasso.with(activity)
                    .load(url)
                    .into(imageView);
            //////
            final Button b2 =(Button)convertView.findViewById(R.id.favorite);
            if(db_helper.getData(movie.getId())){
                b2.setText("Added to favorites");
            }
            else{
                b2.setText("Add to favorites");
            }
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(db_helper.getData(movie.getId())){
                       db_helper.deleteContact(movie.getId());
                        b2.setText("Add to favorites");

                    }
                    else {
                        boolean t = db_helper.insertFavorite(movie.getReleaseDate(), movie.getPosterPath(), movie.getOverview(),
                                movie.getVoteAverage(), movie.getTitle(), movie.getOriginalTitle(), movie.getId());
                        if (t) {
                            b2.setText("Added To favorites");
                        }
                    }
                }
            });


        }
        else if(position>=1&&position<=trailers.size()){//inflate trailer
            //if(position==1)
                convertView=inflater.inflate(R.layout.row_trailer,null);
            final int p=position;

            convertView=inflater.inflate(R.layout.row_trailer,null);
            convertView.setClickable(true);
            convertView.setFocusable(true);
            TextView title=(TextView)(convertView.findViewById(R.id.title));
           // final VideoView mVideoView=(VideoView)(convertView.findViewById(R.id.video));
            final Trailer x = trailers.get(position-1);
            title.setText(x.getName());
            ///////////////////////////
            //////////////////////////image play
            ImageView mImageView;
            mImageView = (ImageView) convertView.findViewById(R.id.image);
            mImageView.setImageResource(R.drawable.play128);
            /////////////////////
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "hhhhhhhhhhhhhh", Toast.LENGTH_LONG).show();
//                Intent videoIntent = new Intent(Intent.ACTION_VIEW);
//                String url2="vnd.youtube://"+x.getKey();
//                videoIntent.setData(Uri.parse(url2));
                    // videoIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
                    //                Intent videoIntent = new Intent(Intent.ACTION_VIEW);
//                String url2="vnd.youtube://"+x.getKey();
                    Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + x.getKey()));
                    activity.startActivity(videoIntent);

                }

            });

        }
        else{
           // if(position==trailers.size()+1)
                convertView=inflater.inflate(R.layout.row_review,null); //inflate reviews
           // convertView=inflater.inflate(R.layout.row_review,null);
            TextView author=(TextView)(convertView.findViewById(R.id.author));
            TextView content=(TextView)(convertView.findViewById(R.id.content));
            Review x=reviews.get(position-trailers.size()-1);
            author.setText(x.getAuthor()+" :");
            content.setText(x.getContent());


        }
        return convertView;
    }
}
