package com.heebin;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ParseTest {

    final static String serviceKey = "KhPxLJdPDVQZwVP4PWVyIXCXXaqu3PQUTE%2BSX7fEAYicTAdXjr%2Bhb0%2Bucz%2FHfBpWmTWiLRHMnFBtqCF2AnkX8Q%3D%3D";

    public static void main(String[] args) {
        ParseTest c = new ParseTest();
        HashMap<String, String> prop = new HashMap<String, String>();
        prop.put("serviceKey", ParseTest.serviceKey);
        prop.put("startX", "126.950268");
        prop.put("startY", "37.582501");
        prop.put("endX", "126.957681");
        prop.put("endY", "37.506142");


        String result = c.get(c.makeURL("http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus", prop));


        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(result);//new InputSource(url.openStream())
            doc.getDocumentElement().normalize();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public URL makeURL(String strUrl, HashMap<String, String> property) {
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

    public String get(URL url) {
        try {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정


            con.setRequestMethod("GET");

// URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다. URL 연결은 입출력에 사용될 수 있다.
// URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.


            con.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
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
/*
        protected void onPostExecute(Document doc) {

            String s = "";
            NodeList nodeList = doc.getElementsByTagName("item");

            for(int i = 0; i< nodeList.getLength(); i++){

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

                NodeList idx = fstElmnt.getElementsByTagName("idx");
                s += "idx = "+  idx.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList gugun = fstElmnt.getElementsByTagName("gugun");
                s += "gugun = "+  gugun.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList instt  = fstElmnt.getElementsByTagName("instt");
                s += "instt = "+ instt.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList spot = fstElmnt.getElementsByTagName("spot");
                s += "spot = "+  spot.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList spotGubun = fstElmnt.getElementsByTagName("spotGubun");
                s += "spotGubun = "+  spotGubun.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList airPump = fstElmnt.getElementsByTagName("airPump");
                s += "airPump = "+  airPump.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList updtDate = fstElmnt.getElementsByTagName("updtDate");
                s += "updtDate = "+  updtDate.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            }

            textview.setText(s);

            super.onPostExecute(doc);
        }
 */