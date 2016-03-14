package cmps121.haikuhelper;

import android.content.Context;

import java.util.ArrayList;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Created by Kevin on 3/7/2016.
 */
public class SavedAdapter extends ArrayAdapter<Haiku> {
    Context context;
    ArrayList<Haiku> haiku;

    static class ViewHolder {
        public TextView titleView;
        public TextView completeView;
    }

    // Constructor
    public SavedAdapter(Context context, ArrayList<Haiku> haiku) {
        super(context, R.layout.row_saved, haiku);
        this.context = context;
        this.haiku = haiku;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recycle convertView if it is filled, or create new layout and ViewHolder if null
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_saved, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.titleView = (TextView) rowView.findViewById(R.id.title_name);
            holder.completeView = (TextView) rowView.findViewById(R.id.complete);
            rowView.setTag(holder);
        }

        // Fill views with data
        String title = haiku.get(position).title;
        if (title.length() > 18) {
            title = title.substring(0,18) + "...";
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.titleView.setText(title);
        int totalSyllables = haiku.get(position).line1syl + haiku.get(position).line2syl + haiku.get(position).line3syl;
        if(totalSyllables != 17){
            holder.completeView.setText("No");
        }else{
            holder.completeView.setText("Yes");
        }

        return rowView;
    }
}