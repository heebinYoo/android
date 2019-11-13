package com.heebin.smartroute.busAPI;

import com.heebin.smartroute.bean.Station;
import com.heebin.smartroute.bean.userData.UserLocation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class StationSearcher extends GetConnector implements Runnable {


    private static final String urlBody = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos";

    @Override
    public void run() {
        HashMap<String, String> prop;

        String resultHome;
        String resultOffice;

        prop = new HashMap<String, String>();
        prop.put("serviceKey", super.serviceKey);
        prop.put("tmX", String.format("%.6f", UserLocation.getInstance().getHomeLat()));
        prop.put("tmY", String.format("%.6f", UserLocation.getInstance().getHomeLong()));
        prop.put("radius", "1500");
        resultHome = this.get(this.makeURL(urlBody, prop));

        prop = new HashMap<String, String>();
        prop.put("serviceKey", super.serviceKey);
        prop.put("tmX", String.format("%.6f", UserLocation.getInstance().getOfficeLat()));
        prop.put("tmY", String.format("%.6f", UserLocation.getInstance().getOfficeLong()));
        prop.put("radius", "1500");
        resultOffice = this.get(this.makeURL(urlBody, prop));


        parse(resultHome);
        parse(resultOffice);

    }

    @Override
    protected void parse(String result) {
        ArrayList<Station> homeStationList=null;
        ArrayList<Station> officeStationList=null;

        try {
            if(homeStationList == null){
                homeStationList = new ArrayList<Station>();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(result);
                doc.getDocumentElement().normalize();

                String s = "";
                NodeList nodeList = doc.getElementsByTagName("itemList");

                for(int i = 0; i< nodeList.getLength(); i++){
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    Station data;
                    data = new Station(
                            fstElmnt.getElementsByTagName("stationId").item(0).getChildNodes().item(0).getNodeValue(),
                            fstElmnt.getElementsByTagName("stationNm").item(0).getChildNodes().item(0).getNodeValue(),
                            Double.parseDouble( fstElmnt.getElementsByTagName("gpsX").item(0).getChildNodes().item(0).getNodeValue()),
                            Double.parseDouble(  fstElmnt.getElementsByTagName("gpsY").item(0).getChildNodes().item(0).getNodeValue()),
                            fstElmnt.getElementsByTagName("arsId").item(0).getChildNodes().item(0).getNodeValue(),
                            Integer.parseInt( fstElmnt.getElementsByTagName("dist").item(0).getChildNodes().item(0).getNodeValue())
                    );
                    homeStationList.add(data);

                }
            }
            else if(homeStationList != null && officeStationList==null) {
                officeStationList = new ArrayList<Station>();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(result);
                doc.getDocumentElement().normalize();

                String s = "";
                NodeList nodeList = doc.getElementsByTagName("itemList");

                for(int i = 0; i< nodeList.getLength(); i++){
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    Station data;
                    data = new Station(
                        fstElmnt.getElementsByTagName("stationId").item(0).getChildNodes().item(0).getNodeValue(),
                        fstElmnt.getElementsByTagName("stationNm").item(0).getChildNodes().item(0).getNodeValue(),
                            Double.parseDouble( fstElmnt.getElementsByTagName("gpsX").item(0).getChildNodes().item(0).getNodeValue()),
                            Double.parseDouble(  fstElmnt.getElementsByTagName("gpsY").item(0).getChildNodes().item(0).getNodeValue()),
                        fstElmnt.getElementsByTagName("arsId").item(0).getChildNodes().item(0).getNodeValue(),
                       Integer.parseInt( fstElmnt.getElementsByTagName("dist").item(0).getChildNodes().item(0).getNodeValue())
                    );
                    officeStationList.add(data);
                }

                UserLocation.getInstance().setStationLists(homeStationList, officeStationList);
            }
            else{
                throw new Exception();
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
