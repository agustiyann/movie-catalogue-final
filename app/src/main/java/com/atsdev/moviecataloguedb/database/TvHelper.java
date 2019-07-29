package com.atsdev.moviecataloguedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TABLE_NAME_TV;

public class TvHelper {

    private static final String DATABASE_TABLE = TABLE_NAME_TV;

    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TvHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper=new DatabaseHelper(context);
        database=databaseHelper.getWritableDatabase();
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
