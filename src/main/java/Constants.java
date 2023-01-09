import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Constants {
    public static final String PARAM_ID = "id";
    public static final String PARAM_RESULT = "result";
    public static final String UNDERSCORE = "_";
    public static final String DOMAIN = "https://app.seker.live/";
    public static final int SECOND = 1000;
    public static final String METHOD_GET = "GET";
    public static final String QUESTION_MARK = "?";
    public static final String EQUALS = "=";
    public static final String AND = "&";
    public static final Map<Integer, String> FUNCTIONS_MAP;
    static {
        Map<Integer, String> aMap = new TreeMap<>();
        aMap.put(0, "mostCommonLastName");
        aMap.put(1, "reverse");
        aMap.put(2, "sumArrayToDigit");
        FUNCTIONS_MAP = Collections.unmodifiableMap(aMap);
    }
    public static final int[] ARRAY = {41,12,0, 6,1,876,1,4, 22};
}
