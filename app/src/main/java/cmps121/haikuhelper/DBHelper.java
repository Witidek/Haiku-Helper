package cmps121.haikuhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Jason on 3/5/2016.
 */
public class DBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "haiku.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public static DBHelper getInstance(Context context){
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public void saveHaiku(Haiku haiku) {
        // Connect to writable DB
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", haiku.title);
        values.put("line1", haiku.line1);
        values.put("line2", haiku.line2);
        values.put("line3", haiku.line3);
        values.put("line1syl", haiku.line1syl);
        values.put("line2syl", haiku.line2syl);
        values.put("line3syl", haiku.line3syl);
        long result = db.insert("haiku", "id", values);
        Log.i("LOG: ", String.format("%d", result));

        // Close stuff
        db.close();
    }

    public ArrayList<Haiku> getHaiku(){
        Haiku haiku = new Haiku();
        ArrayList<Haiku> haikuList = new ArrayList<Haiku>();
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = new String[]{haiku.KEY_id,haiku.KEY_title, haiku.KEY_line1,haiku.KEY_line2,haiku.KEY_line3};
        qb.setTables("haiku");
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(haiku.KEY_id));
            String title = cursor.getString(cursor.getColumnIndex(haiku.KEY_title));
            String line1 = cursor.getString(cursor.getColumnIndex(haiku.KEY_line1));
            String line2 = cursor.getString(cursor.getColumnIndex(haiku.KEY_line2));
            String line3 = cursor.getString(cursor.getColumnIndex(haiku.KEY_line3));
            Haiku listHaiku = new Haiku(id,title,line1,line2,line3);
            haikuList.add(listHaiku);
        }
        // Close stuff
        cursor.close();
        db.close();
        return haikuList;
    }

}