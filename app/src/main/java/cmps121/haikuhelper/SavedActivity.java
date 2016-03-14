package cmps121.haikuhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {
    ListView listView;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        db = DBHelper.getInstance(this);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get all stored haikus from database
        ArrayList<Haiku> getHaiku = db.getHaikuList();

        // Fill ListView
        final ArrayAdapter<Haiku> adapter = new SavedAdapter(this, getHaiku);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Haiku haiku;
                haiku = adapter.getItem(position);
                Intent intent = new Intent(SavedActivity.this, ViewActivity.class);
                intent.putExtra("haiku", haiku);
                //Log.i("LOG: Sending id: ", Integer.toString(haiku.id));
                startActivity(intent);
            }
        });
    }
}



