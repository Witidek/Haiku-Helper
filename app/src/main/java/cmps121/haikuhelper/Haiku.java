package cmps121.haikuhelper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 3/5/2016.
 */

public class Haiku implements Parcelable {

    public static final String KEY_id = "id";
    public static final String KEY_title = "title";
    public static final String KEY_line1 = "line1";
    public static final String KEY_line2 = "line2";
    public static final String KEY_line3 = "line3";
    public static final String KEY_line1syl = "line1syl";
    public static final String KEY_line2syl = "line2syl";
    public static final String KEY_line3syl = "line3syl";

    String title;
    String line1;
    String line2;
    String line3;

    int line1syl;
    int line2syl;
    int line3syl;
    int id;

    public Haiku() {
        this.line1syl = 0;
        this.line2syl = 0;
        this.line3syl = 0;
    }

    public Haiku(int id, String title, String line1, String line2, String line3, int line1syl, int line2syl, int line3syl) {
        this.id = id;
        this.title = title;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line1syl = line1syl;
        this.line2syl = line2syl;
        this.line3syl = line3syl;

    }
    public String toString() {
        return this.title;
    }

    // Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(line1);
        out.writeString(line2);
        out.writeString(line3);
        out.writeInt(line1syl);
        out.writeInt(line2syl);
        out.writeInt(line3syl);
    }

    public static final Parcelable.Creator<Haiku> CREATOR = new Parcelable.Creator<Haiku>() {
        public Haiku createFromParcel(Parcel in) {
            return new Haiku(in);
        }

        public Haiku[] newArray(int size) {
            return new Haiku[size];
        }
    };

    private Haiku(Parcel in) {
        id = in.readInt();
        title = in.readString();
        line1 = in.readString();
        line2 = in.readString();
        line3 = in.readString();
        line1syl = in.readInt();
        line2syl = in.readInt();
        line3syl = in.readInt();
    }
}
