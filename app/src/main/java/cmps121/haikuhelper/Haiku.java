package cmps121.haikuhelper;

/**
 * Created by Jason on 3/5/2016.
 */
public class Haiku {
    String title;

    String line1;
    String line2;
    String line3;

    int line1syl;
    int line2syl;
    int line3syl;

    public Haiku(){
        this.line1syl = 0;
        this.line2syl = 0;
        this.line3syl = 0;
    }

    public Haiku(String title, String line1, String line2, String line3) {
        this.title = title;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }
}
