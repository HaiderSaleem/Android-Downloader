package com.debugger.downloader.database;

/**
 * Created by M.HAiDER Saleem on 25/04/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import com.debugger.downloader.model.DownloadedFilesParam;
import com.debugger.downloader.model.FileParams;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "db_files";
    // table name

    private static final String TABLE_FILES = "MyFiles";
    private static final String TABLE_FAVOURITE = "FAVOURITE";


    //Favourite
    private static final String KEY_ID = "id";
    private static final String KEY_FAVOURITE = "FAVOURITE";
    private static final String KEY_KEY = "KEY";

    //cart
    private static final String KEY_FID = "fId";
    private static final String KEY_FILE_NAME = "FILE_NAME";
    private static final String KEY_STATUS = "FILE_STATUS";
    private static final String KEY_URL = "FILE_URL";
    private static final String KEY_PATH = "FILE_PATH";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        String CREATE_FILES_ID = "CREATE TABLE " + TABLE_FILES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_FID + " TEXT," +  KEY_STATUS + " TEXT,"+  KEY_URL + " TEXT," +KEY_FILE_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_FILES_ID);

        String CREATE_FAVOURITE = "CREATE TABLE " + TABLE_FAVOURITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_FAVOURITE + " INTEGER,"+  KEY_PATH + " TEXT," +KEY_FILE_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_FAVOURITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        // Create tables again
        onCreate(sqLiteDatabase);

    }

    // Adding new contact
    public Long addFile(String fId, String name,String status,String url) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FID, fId); // email
        values.put(KEY_FILE_NAME,name);
        values.put(KEY_STATUS,status);
        values.put(KEY_URL,url);

        // Inserting Row
        long id = db.insert(TABLE_FILES, null, values);
        db.close(); // Closing database connection
        return id;
    }
  public Long addFavourite(DownloadedFilesParam downloadedFilesParam) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PATH, downloadedFilesParam.getPath()); // email
        values.put(KEY_FILE_NAME,downloadedFilesParam.getName());
        values.put(KEY_FAVOURITE,downloadedFilesParam.isFavourite());

        // Inserting Row
        long id = db.insert(TABLE_FAVOURITE, null, values);
        db.close(); // Closing database connection
        return id;
    }


    public Long updateStatus(int status,String fID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, String.valueOf(status));
        long id = db.update(TABLE_FILES, values,"fId=?", new String[]{fID});
        db.close();
        return id;


    }


    public ArrayList<FileParams> getFilesData() {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_FILES+"" ;
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            ArrayList<FileParams> fileList= new ArrayList<>();

            do {

                FileParams data= new FileParams();
                data.setFID(ex.getString(ex.getColumnIndex(KEY_FID)));
                data.setFileName(ex.getString(ex.getColumnIndex(KEY_FILE_NAME)));
                data.setStatus(ex.getString(ex.getColumnIndex(KEY_STATUS)));
                data.setUrl(ex.getString(ex.getColumnIndex(KEY_URL)));

                fileList.add(data);
            } while (ex.moveToNext());
            db.close();
            return fileList;
        } else
            return null;


    }

    public ArrayList<DownloadedFilesParam> getFavourites() {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_FAVOURITE+"" ;
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            ArrayList<DownloadedFilesParam> fileList= new ArrayList<>();

            do {

                DownloadedFilesParam data= new DownloadedFilesParam();
                data.setName(ex.getString(ex.getColumnIndex(KEY_FILE_NAME)));
                data.setPath(ex.getString(ex.getColumnIndex(KEY_PATH)));
                data.setFavourite(ex.getInt(ex.getColumnIndex(KEY_FAVOURITE)));

                fileList.add(data);
            } while (ex.moveToNext());
            db.close();
            return fileList;
        } else
            return null;


    }

    public boolean getFavouriteStatus(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_FAVOURITE+" WHERE "+KEY_FILE_NAME+"='"+name+"'";
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            db.close();
            return true;
        } else
            return false;


    }


    public void removeFavourite(String name) {

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_FAVOURITE+" WHERE "+KEY_FILE_NAME+"='"+name+"'");
        db.close();

    }

}
