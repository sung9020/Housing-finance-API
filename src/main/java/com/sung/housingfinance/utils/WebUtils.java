package com.springboot.map.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WebUtils {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String httpRequest(String method, String url, String authorization, String param, String contentType) throws Exception {
        StringBuffer response = new StringBuffer();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();


        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", USER_AGENT);
        if (!contentType.isEmpty()) {
            con.setRequestProperty("Content-Type", contentType);
        }
        if (!authorization.isEmpty()) {
            con.setRequestProperty("Authorization", authorization);
        }
        con.setUseCaches(false);

        if ((method == "POST" || method == "PUT") && !param.isEmpty()) {

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(param.getBytes());
            os.flush();
            os.close();
        }

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream(), "UTF-8"));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }

        return response.toString();
    }

    public static String httpRequest(String method, String url, String authorization) throws Exception {
        return httpRequest(method, url, authorization, "", "application/x-www-form-urlencoded");
    }
}
