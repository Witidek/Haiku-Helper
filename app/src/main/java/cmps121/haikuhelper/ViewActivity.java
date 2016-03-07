package cmps121.haikuhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    Haiku haiku = new Haiku();
    String message;
    String [] pick = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        haiku = getIntent().getExtras().getParcelable("haiku");
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Victorian.ttf");
        TextView textview = (TextView) findViewById(R.id.textView15);
        textview.setText(haiku.title);
        textview.setTypeface(custom_font);
        textview = (TextView) findViewById(R.id.textView16);
        textview.setText(haiku.line1);
        textview.setTypeface(custom_font);
        textview = (TextView) findViewById(R.id.textView17);
        textview.setText(haiku.line2);
        textview.setTypeface(custom_font);
        textview = (TextView) findViewById(R.id.textView18);
        textview.setText(haiku.line3);
        textview.setTypeface(custom_font);
        message = haiku.title + "\n" + haiku.line1 +"\n"+ haiku.line2 + "\n" + haiku.line3;
        pick[0] = "Text Message";
        pick[1] = "Email";

    }

    public void OnClickSend ( View view){
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
                        if (which == 1){
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Awesome Haiku");
                            emailIntent.putExtra(Intent.EXTRA_TEXT   , message);
                            startActivity(emailIntent);
                        }
                    }
                });
        builder.show();

    }

    public void OnClickEdit (View view){
        Intent intent = new Intent(ViewActivity.this, WritingActivity.class);
        intent.putExtra("previous", "ViewActivity");
        intent.putExtra("haiku", haiku);
        startActivity(intent);
    }

}
