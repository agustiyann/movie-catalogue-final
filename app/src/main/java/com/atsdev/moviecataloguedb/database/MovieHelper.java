package com.atsdev.moviecataloguedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.TABLE_NAME;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_NAME;

    private Context context;
    private DatabaseMovieHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper=new DatabaseMovieHelper(context);
        database=databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){databaseHelper.close();}

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC ");
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,
                null,
                _ID +" = ? ",
                new String[]{id},
                null,
                null,
                null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values) ;
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID + " = ? ",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
