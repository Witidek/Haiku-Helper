package cmps121.haikuhelper;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database singleton
        DBHelper db = DBHelper.getInstance(this);

        // Custom font
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Japan.ttf");
        TextView textview = (TextView) findViewById(R.id.textView14);
        textview.setTypeface(custom_font);
        Button button = (Button) findViewById(R.id.button);
        button.setTypeface(custom_font);
        button = (Button) findViewById(R.id.button2);
        button.setTypeface(custom_font);
        button = (Button) findViewById(R.id.button3);
        button.setTypeface(custom_font);
    }

    public void onClickWritingActivity(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }

    public void onClickSavedActivity(View view){
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    public void OnClickInstruction (View view){
        Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
        startActivity(intent);
    }
}
