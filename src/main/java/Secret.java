import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;


public class Secret {

    private final String id;

    public Secret (String id) {
        this.id = id;
    }

    public void check () {
        String[] results = null;
        try {
            String a1 = null, a2 = null;
            int a3 = 0;
            Map<String, String> params = new HashMap<>();
            params.put(Constants.PARAM_ID, id);
            String[] names = getNamesArrayFromServer (params);
            if (names != null) {
                a1 = Main.mostCommonLastName(names);
            }
            String str =  getStringFromServer(params);
            if (str != null) {
                String reversed = Main.reverse(str);
                if (reversed != null) {
                    a2 = reversed.substring(0, 4);
                }
            }
            a3 = Main.sumArrayToDigit(Constants.ARRAY);
            String[] tokens = getResults(params, a1, a2, a3);
            if (tokens != null && tokens.length == Constants.FUNCTIONS_MAP.size() + 1) {
                results = new String[tokens.length];
                int count = 0;
                for (int i = 0; i < tokens.length - 1; i++) {
                    boolean result = Boolean.parseBoolean(tokens[i]);
                    String function = Constants.FUNCTIONS_MAP.get(i);
                    results[i] = function + " function: " + (result ? "\033[32mCORRECT\033[0m" : "\033[31mWRONG\033[0m");
                    if (result) {
                        count++;
                    }
                }
                for (int i = 0; i < results.length - 1; i++) {
                    System.out.println(results[i]);
                    Thread.sleep(2 * Constants.SECOND);
                }
                if (count == Constants.FUNCTIONS_MAP.size()) {
                    System.out.printf( "\033[34mCongrats %s, your all done!\033[0m", tokens[Constants.FUNCTIONS_MAP.size()]);
                } else {
                    System.out.println("Fix the errors and try again...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getNamesArrayFromServer (Map<String, String> params) throws Exception {
        String[] names = null;
        String response = sendGetRequest(Constants.DOMAIN, Paths.get_names.getPath(), params);
        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            names = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                names[i] = jsonArray.getString(i);
            }
        }
        return names;
    }

    private String getStringFromServer (Map<String, String> params)  throws Exception {
        return sendGetRequest(Constants.DOMAIN, Paths.get_string.getPath(), params);
    }

    private String[] getResults (Map<String, String> params, String a1, String a2, int a3)  throws Exception {
        String[] tokens = null;
        params.put(Constants.PARAM_RESULT, String.format("%s%s%s%s%d", a1, Constants.UNDERSCORE, a2, Constants.UNDERSCORE, a3));
        String result = sendGetRequest(Constants.DOMAIN, Paths.check.getPath(), params);
        if (result != null) {
            tokens = result.split(Constants.UNDERSCORE);
        }
        return tokens;
    }


    public String sendGetRequest(String domain, String path, Map<String, String> params) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(domain + path);
        urlBuilder.append(Constants.QUESTION_MARK);
        for (Map.Entry<String, String> param : params.entrySet()) {
            urlBuilder.append(param.getKey()).append(Constants.EQUALS).append(param.getValue()).append(Constants.AND);
        }
        String urlString = urlBuilder.toString();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(Constants.METHOD_GET);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }


}
