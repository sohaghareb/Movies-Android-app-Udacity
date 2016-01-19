package com.example.dell.day2;

/**
 * Created by dell on 09/01/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Movies.db";
    public static final String Favourites_TABLE_NAME = "favorites";
    public static final String favorites_COLUMN_ID = "id";
    public static final String favorites_COLUMN_Poster_Path = "poster_path";
    public static final String favorites_COLUMN_Overview = "overview";
    public static final String favorites_COLUMN_Release_Date = "release_date";
    public static final String favorites_Adult = "adult";
    public static final String favorites_COLUMN_Original_Tiltle = "original_title";
    public static final String favorites_COLUMN_Original_Language = "original_language";
    public static final String favorites_COLUMN_Tiltle = "title";
    public static final String favorites_COLUMN_Background_Path= "backdrop_path";
    public static final String favorites_COLUMN_Popularity = "popularity";
    public static final String favorites_Vote_Count = "vote_count";
    public static final String favorites_COLUMN_Original_Vote_Average = "vote_average";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favorites " +
                        "(id integer primary key, poster_path text,overview text,release_date text," +
                        " title text,original_title text,vote_average double,movie_id integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public boolean insertFavorite  (String release_date, String  poster_path, String overview,
                                    double vote,String title,String original_title,int movie_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues fValues = new ContentValues();
        fValues.put("release_date", release_date);
        fValues.put(" poster_path", poster_path);
        fValues.put("overview", overview);
        fValues.put("title", title);
        fValues.put("vote_average", vote);
        fValues.put("original_title", original_title);
        fValues.put("movie_id", movie_id);
        db.insert("favorites", null, fValues);
        db.close();
        return true;
    }

    public boolean getData(int  id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from favorites where movie_id=" + id, null);

        if(cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }

    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Favourites_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String release_date, String poster_path, String overview, String title,String vote,
                                  String original_title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  fValues = new ContentValues();
        fValues.put("release_date", release_date);
        fValues.put(" poster_path", poster_path);
        fValues.put("overview", overview);
        fValues.put("title", title);
        fValues.put("vote_average", vote);
        fValues.put("original_title", original_title);
        db.update("favorites",fValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "movie_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Movie> getAllCotacts()
    {
        ArrayList<Movie> array_list = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favorites", null );
        res.moveToFirst();
        int i=0;
        while(res.isAfterLast() == false){
            Movie movie=new Movie();
           // array_list.add(res.getString(res.getColumnIndex(favorites_COLUMN_Tiltle)));
            movie.setId(res.getInt(res.getColumnIndex("movie_id")));
            movie.setTitle(res.getString(res.getColumnIndex(favorites_COLUMN_Tiltle)));
            movie.setOriginalTitle(res.getString(res.getColumnIndex(favorites_COLUMN_Original_Tiltle)));

            movie.setReleaseDate(res.getString(res.getColumnIndex(favorites_COLUMN_Release_Date)));
            movie.setPosterPath(res.getString(res.getColumnIndex(favorites_COLUMN_Poster_Path)));
            movie.setOverview(res.getString(res.getColumnIndex(favorites_COLUMN_Overview)));
            movie.setOriginalTitle(res.getString(res.getColumnIndex(favorites_COLUMN_Original_Vote_Average)));

            array_list.add(movie);
            res.moveToNext();

        }
        return array_list;
    }
}