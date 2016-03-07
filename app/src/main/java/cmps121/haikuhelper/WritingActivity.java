package cmps121.haikuhelper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    DBHelper db;

    EditText titleText;
    EditText[] linesText = new EditText[3];
    TextView[] syllablesText = new TextView[3];
    String[] lines = new String[3];
    Button countSyllablesButton;

    int apiRequests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        db = DBHelper.getInstance(this);

        titleText = (EditText)findViewById(R.id.titleText);

        linesText[0] = (EditText)findViewById(R.id.line1);
        linesText[1] = (EditText)findViewById(R.id.line2);
        linesText[2] = (EditText)findViewById(R.id.line3);

        syllablesText[0] = (TextView)findViewById(R.id.line1Syllables);
        syllablesText[1] = (TextView)findViewById(R.id.line2Syllables);
        syllablesText[2] = (TextView)findViewById(R.id.line3Syllables);

        countSyllablesButton = (Button)findViewById(R.id.countSyllablesButton);

        Intent intent = getIntent();
        if (intent.hasExtra("haiku")) {
            Haiku haiku = intent.getParcelableExtra("haiku");
            titleText.setText(haiku.title);
            linesText[0].setText(haiku.line1);
            linesText[1].setText(haiku.line2);
            linesText[2].setText(haiku.line3);
            syllablesText[0].setText(Integer.toString(haiku.line1syl));
            syllablesText[1].setText(Integer.toString(haiku.line2syl));
            syllablesText[2].setText(Integer.toString(haiku.line3syl));
        }
    }

    public void countSyllables(View view) {
        countSyllablesButton.setEnabled(false);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rhymebrain.com/")
                .addConverterFactory(GsonConverterFactory.create())	//parse Gson string
                .client(httpClient)	//add logging
                .build();

        RhymeBrainService service = retrofit.create(RhymeBrainService.class);

        for (int i = 0; i < lines.length; i++) {
            lines[i] = linesText[i].getText().toString();
            syllablesText[i].setText(String.format("%d", 0));
            String[] words = lines[i].split(" ");
            final int index = i;


            for (String word: words) {
                Call<WordInfo> queryResponseCall = service.getWordInfo("getWordInfo", word);

                apiRequests++;
                //Call retrofit asynchronously
                queryResponseCall.clone().enqueue(new Callback<WordInfo>() {

                    @Override
                    public void onResponse(Response<WordInfo> response) {
                        // isSuccess() checks for a 200 code
                        if (response.isSuccess()) {
                            apiRequests--;
                            int syllableCount = Integer.parseInt(syllablesText[index].getText().toString());
                            syllableCount += Integer.parseInt(response.body().syllables);
                            syllablesText[index].setText(String.format("%d", syllableCount));
                            if (index == 0 || index == 2) {
                                if (syllableCount == 5) {
                                    syllablesText[index].setTextColor(Color.GREEN);
                                }else if (syllableCount > 5) {
                                    syllablesText[index].setTextColor(Color.RED);
                                }else {
                                    syllablesText[index].setTextColor(Color.BLACK);
                                }
                            }else if (index == 1){
                                if (syllableCount == 7) {
                                    syllablesText[index].setTextColor(Color.GREEN);
                                }else if (syllableCount > 7) {
                                    syllablesText[index].setTextColor(Color.RED);
                                }else {
                                    syllablesText[index].setTextColor(Color.BLACK);
                                }
                            }
                            if (apiRequests == 0) {
                                countSyllablesButton.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Log error here since request failed
                    }
                });
            }
        }
    }

    public void saveHaiku(View view) {
        Haiku haiku = new Haiku();

        haiku.title = titleText.getText().toString();
        haiku.line1 = linesText[0].getText().toString();
        haiku.line2 = linesText[1].getText().toString();
        haiku.line3 = linesText[2].getText().toString();

        countSyllables(countSyllablesButton);

        haiku.line1syl = Integer.parseInt(syllablesText[0].getText().toString());
        haiku.line2syl = Integer.parseInt(syllablesText[1].getText().toString());
        haiku.line3syl = Integer.parseInt(syllablesText[2].getText().toString());

        db.saveHaiku(haiku);
    }

    public interface RhymeBrainService {
        @GET("talk")
        Call<WordInfo> getWordInfo(@Query("function") String function,
                                   @Query("word") String word);
    }
}
