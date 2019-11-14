package com.heebin.smartroute.busAPI.connector;

import com.heebin.smartroute.bean.Route;
import com.heebin.smartroute.bean.Station;
import com.heebin.smartroute.bean.userData.UserLocation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RouteSearcherConnector extends Connector {

    private static final String urlBody = "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus";

    private ArrayList<Route> routeList;
    @Override
    public void run() {
        HashMap<String, String> prop;

        String result;


        prop = new HashMap<String, String>();
        prop.put("serviceKey", super.serviceKey);

        prop.put("startX", String.format("%.6f", UserLocation.getInstance().getHomeLong()));
        prop.put("startY", String.format("%.6f", UserLocation.getInstance().getHomeLat()));

        prop.put("endX", String.format("%.6f", UserLocation.getInstance().getHomeLong()));
        prop.put("endY", String.format("%.6f", UserLocation.getInstance().getHomeLat()));

        result = this.get(this.makeURL(urlBody, prop));



        parse(result);

    }


    @Override
    protected void parse(String result) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(result)));
            doc.getDocumentElement().normalize();


            routeList = new ArrayList<Route>();

            NodeList nodeList = doc.getElementsByTagName("itemList");

            for(int i = 0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

                Route data;
                data = new Route(
                        fstElmnt.getElementsByTagName("stationId").item(0).getChildNodes().item(0).getNodeValue()

                );
                routeList.add(data);

            }



        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
