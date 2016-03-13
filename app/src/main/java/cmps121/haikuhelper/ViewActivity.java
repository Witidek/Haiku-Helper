package cmps121.haikuhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class ViewActivity extends AppCompatActivity {
    DBHelper db;
    Haiku haiku;
    String message;
    String [] pick = new String[2];
    TextToSpeech t0;                      //poem's title
    TextToSpeech t1;                     //poem's 3 lines
    Button b1;
    float speechrate = (float)0.5;      //speed of voice


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        db = DBHelper.getInstance(this);

        haiku = getIntent().getParcelableExtra("haiku");
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Victorian.ttf");
        String title = haiku.title;

        final TextView textview = (TextView) findViewById(R.id.textView15);
        if (title.length() > 14){
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP,50 );
        }
        textview.setText(title + "\n");
        textview.setTypeface(custom_font);

        final TextView ed0 = (TextView) findViewById(R.id.textView16);
        ed0.setText(haiku.line1 + "\n");
        ed0.append(haiku.line2 + "\n");
        ed0.append(haiku.line3 + "\n");
        ed0.setTypeface(custom_font);
        message = haiku.title + "\n" + haiku.line1 +"\n"+ haiku.line2 + "\n" + haiku.line3;
        pick[0] = "Text Message";
        pick[1] = "Email";

        //button used to read
        b1=(Button)findViewById(R.id.button);
        // method to read the string
        t0=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t0.setLanguage(Locale.JAPANESE);
                    t0.setSpeechRate((float)0.5);
                }
            }
        });

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.JAPANESE);
                    t1.setSpeechRate(speechrate);
                }
            }
        });

        //when button clicked, start reading the string
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                String toSpeak0 = textview.getText().toString();
                String toSpeak1 = ed0.getText().toString();
                t0.speak(toSpeak0, TextToSpeech.QUEUE_FLUSH, null);
                t1.speak(toSpeak1, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void onClickSend(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Where to Send?")
                .setItems(pick, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.putExtra("sms_body", message);
                            sendIntent.setType("vnd.android-dir/mms-sms");
                            startActivity(sendIntent);
                        }
                        if (which == 1) {
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Awesome Haiku");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                            startActivity(emailIntent);
                        }
                    }
                });
        builder.show();

    }

    public void onClickDelete(View view) {
        // Creates AlertDialog to ask user for desired quantity
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewActivity.this);
        alertDialog.setTitle("Delete Haiku");
        alertDialog.setMessage("Are You Sure You Want to Delete this Haiku?");
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Leaving Blank
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                db.deleteHaiku(haiku);
                finish();
            }
        });

        alertDialog.setIcon(R.drawable.alert);
        alertDialog.show();
    }

    public void onClickEdit(View view) {
        Intent intent = new Intent(ViewActivity.this, WritingActivity.class);
        intent.putExtra("previous", "ViewActivity");
        intent.putExtra("haiku", haiku);
        Log.i("LOG: Sending edit id: ", Integer.toString(haiku.id));
        startActivity(intent);
    }

}
