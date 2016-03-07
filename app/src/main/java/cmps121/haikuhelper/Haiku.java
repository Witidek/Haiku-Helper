package cmps121.haikuhelper;

/**
 * Created by Jason on 3/5/2016.
 */
public class Haiku {

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

    public Haiku(){
        this.line1syl = 0;
        this.line2syl = 0;
        this.line3syl = 0;
    }

    public Haiku(int id, String title, String line1, String line2, String line3) {
        this.id = id;
        this.title = title;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;

    }
    public String toString (){
        return this.title;
    }
}
