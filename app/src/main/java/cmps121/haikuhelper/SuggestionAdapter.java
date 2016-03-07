package cmps121.haikuhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 3/6/2016.
 */
public class SuggestionAdapter extends ArrayAdapter<ArrayList<String>> {

    private final Context context;
    private final ArrayList<ArrayList<String>> wordsList;

    static class ViewHolder {
        public TextView suggestionHeader;
        public TextView suggestionWords;
    }

    public SuggestionAdapter(Context context, ArrayList<ArrayList<String>> wordsList) {
        super(context, R.layout.row_suggestion, wordsList);
        this.context = context;
        this.wordsList = wordsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Recycle convertView if it is filled, or create new layout and ViewHolder if null
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_suggestion, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.suggestionHeader = (TextView) rowView.findViewById(R.id.suggestionHeader);
            holder.suggestionWords = (TextView) rowView.findViewById(R.id.suggestionWords);
            rowView.setTag(holder);
        }

        // Fill views with data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.suggestionHeader.setText(wordsList.get(position).get(0));
        String words = "";
        for (int i = 1; i < wordsList.get(position).size(); i++ ) {
            if (i != 1) {
                words += ", ";
            }
            words += wordsList.get(position).get(i);
        }
        holder.suggestionWords.setText(words);

        return rowView;
    }
}
