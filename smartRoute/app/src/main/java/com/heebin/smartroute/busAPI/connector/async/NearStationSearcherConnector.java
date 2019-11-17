package com.heebin.smartroute.busAPI.connector.async;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.data.inMemory.userData.LodgmentData;
import com.heebin.smartroute.data.inMemory.initRelated.NearStationData;

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

public class NearStationSearcherConnector extends Connector {


    private static final String urlBody = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos";

    private ArrayList<Station> homeStationList=null;
    private ArrayList<Station> officeStationList=null;

    public ArrayList<Station> getHomeStationList() {
        return homeStationList;
    }

    public ArrayList<Station> getOfficeStationList() {
        return officeStationList;
    }



    public void run() {
        HashMap<String, String> prop;

        String resultHome;
        String resultOffice;

        prop = new HashMap<String, String>();
        prop.put("serviceKey", super.serviceKey);
        prop.put("tmX", String.format("%.6f", LodgmentData.getInstance().getHomeLong()));
        prop.put("tmY", String.format("%.6f", LodgmentData.getInstance().getHomeLat()));
        prop.put("radius", "300");
        resultHome = this.get(this.makeURL(urlBody, prop));

        prop = new HashMap<String, String>();
        prop.put("serviceKey", super.serviceKey);
        prop.put("tmX", String.format("%.6f", LodgmentData.getInstance().getOfficeLong()));
        prop.put("tmY", String.format("%.6f", LodgmentData.getInstance().getOfficeLat()));
        prop.put("radius", "300");
        resultOffice = this.get(this.makeURL(urlBody, prop));


        parse(resultHome);
        parse(resultOffice);

        NearStationData.getInstance().setStationLists(homeStationList, officeStationList);
    }

    @Override
    public void parse(String result) {


        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(result)));
            doc.getDocumentElement().normalize();

            if(homeStationList == null){
                homeStationList = new ArrayList<Station>();

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
                            fstElmnt.getElementsByTagName("arsId").item(0).getChildNodes().item(0).getNodeValue()
                    );
                    homeStationList.add(data);

                }
            }
            else if(homeStationList != null && officeStationList==null) {
                officeStationList = new ArrayList<Station>();

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
                        fstElmnt.getElementsByTagName("arsId").item(0).getChildNodes().item(0).getNodeValue()
                    );
                    officeStationList.add(data);
                }


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
