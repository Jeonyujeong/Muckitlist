package foodlist.muckitlist;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yujeong on 16/12/17.
 */

public class JSON {
    private String search;

    public void getText(String str) {
        Log.d("json", "getText");
        search = null;
        search = str;
    }

    public JSON(String search){
        this.search = search;
    }


    public  String readUrl() {
        Log.d("json", "readUrl");
        String clientId = "EwErUhuZyRuYH1B7K1ri";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "pQVoFt7BWf";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(search, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/local.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
