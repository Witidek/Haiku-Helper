package cmps121.haikuhelper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SuggestionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Haiku haiku;
    EditText lineText;
    TextView syllableText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.line_numbers_array, android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        haiku = intent.getParcelableExtra("haiku");
        lineText = (EditText)findViewById(R.id.lineSuggestionText);
        syllableText = (TextView)findViewById(R.id.syllableSuggestionText);

        if (haiku.line1syl < 5) {
            spinner.setSelection(0);
            lineText.setText(haiku.line1);
            syllableText.setText(String.format("%d",haiku.line1syl));
        }else if (haiku.line2syl < 7) {
            spinner.setSelection(1);
            lineText.setText(haiku.line2);
            syllableText.setText(String.format("%d",haiku.line2syl));
        }else if (haiku.line3syl < 5) {
            spinner.setSelection(2);
            lineText.setText(haiku.line3);
            syllableText.setText(String.format("%d",haiku.line3syl));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WritingActivity.class);
        intent.putExtra("haiku", haiku);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch(pos) {
            case 0:
                lineText.setText(haiku.line1);
                break;
            case 1:
                lineText.setText(haiku.line2);
                break;
            case 2:
                lineText.setText(haiku.line3);
                break;
            default:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
