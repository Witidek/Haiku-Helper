package cmps121.haikuhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jason on 3/5/2016.
 */
public class DBHelper extends SQLiteAssetHelper {

    // Database information
    private static final String DATABASE_NAME = "haiku.db";
    private static final int DATABASE_VERSION = 1;

    // Constants for database tables
    private static final int ONE_SYLLABLE_WORDS_MAX = 5121;
    private static final int TWO_SYLLABLE_WORDS_MAX = 41501;
    private static final int THREE_SYLLABLE_WORDS_MAX = 50844;

    // Singleton instance
    private static DBHelper instance;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //setForcedUpgrade();
    }

    // Getter for singleton instance
    public static DBHelper getInstance(Context context){
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    // Save new haiku to database, return the new ROWID created
    public int saveHaiku(Haiku haiku) {
        // Connect to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // Bundle the values with their column names
        ContentValues values = new ContentValues();
        values.put("title", haiku.title);
        values.put("line1", haiku.line1);
        values.put("line2", haiku.line2);
        values.put("line3", haiku.line3);
        values.put("line1syl", haiku.line1syl);
        values.put("line2syl", haiku.line2syl);
        values.put("line3syl", haiku.line3syl);

        // SQLite insert
        long result = db.insert("haiku", null, values);

        // Close stuff
        db.close();

        // Return new ROWID
        return (int)result;
    }

    // Update an existing haiku in database
    public void updateHaiku(Haiku haiku) {
        // Connect to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // Bundle the values with their column names
        ContentValues values = new ContentValues();
        values.put("title", haiku.title);
        values.put("line1", haiku.line1);
        values.put("line2", haiku.line2);
        values.put("line3", haiku.line3);
        values.put("line1syl", haiku.line1syl);
        values.put("line2syl", haiku.line2syl);
        values.put("line3syl", haiku.line3syl);

        // SQLite update
        db.update("haiku", values, "ROWID = " + Integer.toString(haiku.id), null);

        // Close stuff
        db.close();
    }

    // Delete a haiku from database using its id
    public void deleteHaiku(Haiku haiku) {
        // Connect to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // SQLite delete with haiku's id acting as ROWID
        db.delete("haiku", "ROWID = " + Integer.toString(haiku.id), null);

        // Close stuff
        db.close();
    }

    // Return a single haiku from its id
    public Haiku getHaiku(int id) {
        Haiku haiku = new Haiku();

        // Connect to readable database
        SQLiteDatabase db = getReadableDatabase();

        // Build query
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = new String[]{Haiku.KEY_id,Haiku.KEY_title, Haiku.KEY_line1,Haiku.KEY_line2,Haiku.KEY_line3,Haiku.KEY_line1syl,Haiku.KEY_line2syl,Haiku.KEY_line3syl};
        String sqlWhere = "ROWID = ?";
        String[] sqlWhereArgs = new String[]{String.valueOf(id)};
        qb.setTables("haiku");
        Cursor cursor = qb.query(db, sqlSelect, sqlWhere, sqlWhereArgs, null, null, null);

        // Build haiku
        cursor.moveToFirst();
        haiku.id = id;
        haiku.title = cursor.getString(cursor.getColumnIndex(Haiku.KEY_title));
        haiku.line1 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line1));
        haiku.line2 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line2));
        haiku.line3 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line3));
        haiku.line1syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line1syl));
        haiku.line2syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line2syl));
        haiku.line3syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line3syl));

        // Close stuff
        cursor.close();
        db.close();

        // Return single haiku
        return haiku;
    }

    // Return an ArrayList of all haikus stored in the database
    public ArrayList<Haiku> getHaikuList(){
        ArrayList<Haiku> haikuList = new ArrayList<Haiku>();

        // Connect to readable database
        SQLiteDatabase db = getReadableDatabase();

        // Build query to select all rows from haiku table
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = new String[]{Haiku.KEY_id,Haiku.KEY_title, Haiku.KEY_line1,Haiku.KEY_line2,Haiku.KEY_line3,Haiku.KEY_line1syl,Haiku.KEY_line2syl,Haiku.KEY_line3syl};
        qb.setTables("haiku");
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        // Store values into a haiku and insert into the ArrayList
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_id));
            String title = cursor.getString(cursor.getColumnIndex(Haiku.KEY_title));
            String line1 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line1));
            String line2 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line2));
            String line3 = cursor.getString(cursor.getColumnIndex(Haiku.KEY_line3));
            int line1syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line1syl));
            int line2syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line2syl));
            int line3syl = cursor.getInt(cursor.getColumnIndex(Haiku.KEY_line3syl));
            Haiku listHaiku = new Haiku(id,title,line1,line2,line3,line1syl,line2syl,line3syl);
            haikuList.add(listHaiku);
        }
        // Close stuff
        cursor.close();
        db.close();

        // Return ArrayList of haikus
        return haikuList;
    }

    // Return an ArrayList filled with ArrayLists of words
    public ArrayList<ArrayList<String>> getSuggestions() {
        ArrayList<ArrayList<String>> wordsList = new ArrayList<>();
        ArrayList<String> words;
        Cursor cursor;

        // Connect to a readable database
        SQLiteDatabase db = getReadableDatabase();

        Random r = new Random();
        String tableName[] = new String[]{"one_syllable_words", "two_syllable_words", "three_syllable_words"};
        int tableMax[] = {ONE_SYLLABLE_WORDS_MAX, TWO_SYLLABLE_WORDS_MAX, THREE_SYLLABLE_WORDS_MAX};

        // Generate 3 strings of 20 random integers each separated by ", " to grab from database
        for (int i = 0; i < 3; i++) {
            String ids = "";
            for (int j = 0; j < 20; j++) {
                int result = r.nextInt(tableMax[i]);
                if (!ids.equals("")) {
                    ids += ", ";
                }
                ids += Integer.toString(result);
            }

            // Grab 20 words decided by randomly pulled ids
            cursor = db.rawQuery("SELECT word FROM "+tableName[i]+" WHERE ROWID IN ("+ids+");", null);
            words = new ArrayList<>();
            words.add(String.format("%d syllable words", i+1));
            while (cursor.moveToNext()) {
                words.add(cursor.getString(cursor.getColumnIndex("word")));
            }
            wordsList.add(words);
            cursor.close();
        }

        // Close stuff
        db.close();

        // Return 2D array
        return wordsList;
    }

}