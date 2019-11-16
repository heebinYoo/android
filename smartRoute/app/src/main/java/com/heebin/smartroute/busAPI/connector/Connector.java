package com.heebin.smartroute.busAPI.connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class Connector {

    final protected String serviceKey = "KhPxLJdPDVQZwVP4PWVyIXCXXaqu3PQUTE%2BSX7fEAYicTAdXjr%2Bhb0%2Bucz%2FHfBpWmTWiLRHMnFBtqCF2AnkX8Q%3D%3D";
    protected abstract void parse(String result);
    public abstract void run();
    public String getServiceKey() {
        return serviceKey;
    }

    protected URL makeURL(String strUrl, HashMap<String, String> property) {
        StringBuffer urlForm = new StringBuffer();
        String finalUrl;

        urlForm.append(strUrl);
        urlForm.append('?');
        property.forEach((key, value) -> {
            urlForm.append(key);
            urlForm.append('=');
            urlForm.append(value);
            urlForm.append('&');
        });
        finalUrl = urlForm.toString();
        finalUrl = finalUrl.substring(0, finalUrl.length() - 1); //뒤에 & 제거

        try {
            return new URL(finalUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String get(URL url) {
        try {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정


            con.setRequestMethod("GET");

            con.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(

                        new InputStreamReader(con.getInputStream(), "utf-8"));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                return sb.toString();

            } else {
                return "not good connection";
            }

        } catch (Exception e) {
            return "exception occured";
        }

    }


}
