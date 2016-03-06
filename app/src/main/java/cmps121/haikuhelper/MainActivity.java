package cmps121.haikuhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper db = DBHelper.getInstance(this);
    }

    public void onClickWritingActivity(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }

    public void OnClickInstruction (View view){
        Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
        startActivity(intent);
    }
}
