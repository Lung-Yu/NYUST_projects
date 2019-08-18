package com.developer.lungyu.ncyu_agricultural.webapi;

import java.io.DataOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lungyu on 11/3/17.
 */

public class HttpURLConnection {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static int send_post_noresponse(String url,String urlParameters,String token) throws Exception {

        //String url = "https://algdb.csie.ncyu.edu.tw:8080/api/login?user=admin&pwd=123";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("X-Auth-Token", token);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        return responseCode;
    }
}
