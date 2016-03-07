package cmps121.haikuhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {
    ListView listView ;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        db = DBHelper.getInstance(this);
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Haiku> getHaiku = db.getHaiku();
        Toast.makeText(this, String.format("Haikus loaded: %d", getHaiku.size()), Toast.LENGTH_LONG).show();
        ArrayAdapter<Haiku> adapter = new ArrayAdapter<Haiku>(this,
                android.R.layout.simple_list_item_1, getHaiku);

        listView.setAdapter(adapter);
    }
}
