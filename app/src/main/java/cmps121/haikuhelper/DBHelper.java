package cmps121.haikuhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Jason on 3/5/2016.
 */
public class DBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "haiku.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.insert("haiku", null, values);

        // Close stuff
        db.close();
    }

}