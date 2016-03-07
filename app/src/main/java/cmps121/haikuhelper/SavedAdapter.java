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
    /**
     * Context this instance was created from
     */
    Context context;
    /**
     * ArrayList of items to display
     */
    ArrayList<Haiku> haiku;

    static class ViewHolder {
        public TextView titleView;
        public TextView completeView;

    }

    /**
     * Constructor for CartAdapter
     */
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
        if(position % 2 == 0){
            String color = "#e0ebeb";
            rowView.setBackgroundColor(Color.parseColor(color));
        }else{
            String color = "#ffffff";
            rowView.setBackgroundColor(Color.parseColor(color));
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.titleView.setText(String.format(haiku.get(position).title));
        int totalSyllables = haiku.get(position).line1syl + haiku.get(position).line2syl + haiku.get(position).line3syl;
        if(totalSyllables != 17){
            holder.completeView.setText("No");
        }else{
            holder.completeView.setText("Yes");
        }

        return rowView;
    }


}