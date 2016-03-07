package cmps121.haikuhelper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WritingActivity extends AppCompatActivity {

    private enum Action {
        COUNT, SAVE
    }

    DBHelper db;
    Haiku haiku = new Haiku();

    EditText titleText;
    EditText[] linesText = new EditText[3];
    TextView[] syllablesText = new TextView[3];
    Button countSyllablesButton;
    ListView listView;
    SuggestionAdapter suggestionAdapter;

    int apiRequests = 0;
    boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        // Get DB instance
        db = DBHelper.getInstance(this);

        // Get views
        titleText = (EditText)findViewById(R.id.titleText);
        linesText[0] = (EditText)findViewById(R.id.line1);
        linesText[1] = (EditText)findViewById(R.id.line2);
        linesText[2] = (EditText)findViewById(R.id.line3);
        syllablesText[0] = (TextView)findViewById(R.id.line1Syllables);
        syllablesText[1] = (TextView)findViewById(R.id.line2Syllables);
        syllablesText[2] = (TextView)findViewById(R.id.line3Syllables);
        countSyllablesButton = (Button)findViewById(R.id.countSyllablesButton);

        // Check intent
        Intent intent = getIntent();
        if (intent.hasExtra("haiku")) {
            Haiku haiku = intent.getParcelableExtra("haiku");
            titleText.setText(haiku.title);
            linesText[0].setText(haiku.line1);
            linesText[1].setText(haiku.line2);
            linesText[2].setText(haiku.line3);
            syllablesText[0].setText(String.format("%d/5", haiku.line1syl));
            syllablesText[1].setText(String.format("%d/7", haiku.line2syl));
            syllablesText[2].setText(String.format("%d/5", haiku.line3syl));
        }

        ArrayList<ArrayList<String>> wordsList;
        wordsList = db.getSuggestions();

        suggestionAdapter = new SuggestionAdapter(this, wordsList);
        listView = (ListView)findViewById(R.id.suggestionList);
        listView.setAdapter(suggestionAdapter);
    }

    public void updateHaiku(final Action onFinished) {
        // Disable button while API requests are still processing
        countSyllablesButton.setEnabled(false);

        // Enable logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // Build retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rhymebrain.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        RhymeBrainService service = retrofit.create(RhymeBrainService.class);

        // Update haiku lines with EditText fields
        haiku.line1 = linesText[0].getText().toString();
        haiku.line2 = linesText[1].getText().toString();
        haiku.line3 = linesText[2].getText().toString();

        // Reset syllable counts to 0
        haiku.line1syl = 0;
        haiku.line2syl = 0;
        haiku.line3syl = 0;

        // For each haiku line
        for (int i = 0; i < linesText.length; i++) {
            // Get haiku line from EditText
            String line = linesText[i].getText().toString();

            // Split line into word array
            String[] words = line.split(" ");
            final int index = i;

            // For each word in a line
            for (String word: words) {
                Call<WordInfo> queryResponseCall = service.getWordInfo("getWordInfo", word);
                apiRequests++;

                // Call retrofit asynchronously
                queryResponseCall.clone().enqueue(new Callback<WordInfo>() {

                    @Override
                    public void onResponse(Response<WordInfo> response) {
                        apiRequests--;
                        // isSuccess() checks for a 200 code
                        if (response.isSuccess()) {
                            switch(index) {
                                case 0:
                                    haiku.line1syl += Integer.parseInt(response.body().syllables);
                                    syllablesText[index].setText(String.format("%d/5", haiku.line1syl));
                                    if (haiku.line1syl == 5) {
                                        syllablesText[index].setTextColor(Color.GREEN);
                                    }else if (haiku.line1syl > 5) {
                                        syllablesText[index].setTextColor(Color.RED);
                                    }else {
                                        syllablesText[index].setTextColor(Color.BLACK);
                                    }
                                    break;
                                case 1:
                                    haiku.line2syl += Integer.parseInt(response.body().syllables);
                                    syllablesText[index].setText(String.format("%d/7", haiku.line2syl));
                                    if (haiku.line1syl == 7) {
                                        syllablesText[index].setTextColor(Color.GREEN);
                                    }else if (haiku.line1syl > 7) {
                                        syllablesText[index].setTextColor(Color.RED);
                                    }else {
                                        syllablesText[index].setTextColor(Color.BLACK);
                                    }
                                    break;
                                case 2:
                                    haiku.line3syl += Integer.parseInt(response.body().syllables);
                                    syllablesText[index].setText(String.format("%d/5", haiku.line3syl));
                                    if (haiku.line1syl == 5) {
                                        syllablesText[index].setTextColor(Color.GREEN);
                                    }else if (haiku.line1syl > 5) {
                                        syllablesText[index].setTextColor(Color.RED);
                                    }else {
                                        syllablesText[index].setTextColor(Color.BLACK);
                                    }
                                    break;
                                default:
                                    break;
                            }

                            if (apiRequests == 0) {
                                countSyllablesButton.setEnabled(true);
                                switch (onFinished) {
                                    case COUNT:
                                        break;
                                    case SAVE:
                                        if (saved) {
                                            //db.updateHaiku(haiku);
                                        }else {
                                            db.saveHaiku(haiku);
                                            Toast.makeText(getApplicationContext(), "Haiku saved!", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        apiRequests--;
                    }
                });
            }
        }
    }

    public void saveHaiku(View view) {
        updateHaiku(Action.SAVE);
    }

    public void onClickCountSyllables(View view) {
        updateHaiku(Action.COUNT);
    }

    public void onClickSuggestions(View view) {
        listView.setVisibility(View.VISIBLE);
        suggestionAdapter = new SuggestionAdapter(this, db.getSuggestions());
        listView.setAdapter(suggestionAdapter);
    }

    public interface RhymeBrainService {
        @GET("talk")
        Call<WordInfo> getWordInfo(@Query("function") String function,
                                   @Query("word") String word);
    }
}
