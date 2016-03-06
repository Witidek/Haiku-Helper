
package cmps121.haikuhelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordInfo {

    @SerializedName("word")
    @Expose
    public String word;
    @SerializedName("pron")
    @Expose
    public String pron;
    @SerializedName("ipa")
    @Expose
    public String ipa;
    @SerializedName("freq")
    @Expose
    public Integer freq;
    @SerializedName("flags")
    @Expose
    public String flags;
    @SerializedName("syllables")
    @Expose
    public String syllables;

}
