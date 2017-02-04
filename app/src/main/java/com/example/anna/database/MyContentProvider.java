package com.example.anna.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY="com.example.anna.database.provider";//данные в манифесте
    public static final Uri URI=Uri.parse("content://"+AUTHORITY+"/"+DBHelper.TABLE_NAME);
    SQLiteDatabase database;
    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        DBHelper helper= new DBHelper(getContext(), DBHelper.CURRENT_VERSION);//getContext() - метод взять контекст

        database=helper.getWritableDatabase();
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id=database.insert(DBHelper.TABLE_NAME, null,values);
        //Uri uri - имя базы, таблицы и id
        Uri uri1= ContentUris.withAppendedId(uri,id);
        getContext().getContentResolver().notifyChange(uri,null);//сообщаем, что что то изменилось
        return uri1;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=database.query(DBHelper.TABLE_NAME, projection, selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);//добавляем курсору юри
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
