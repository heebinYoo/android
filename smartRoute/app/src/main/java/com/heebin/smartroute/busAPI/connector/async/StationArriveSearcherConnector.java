package com.heebin.smartroute.busAPI.connector.async;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.data.bean.meta.Coord;
import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.learning.Matrix.BusStationMatrixHolder;

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

public class StationArriveSearcherConnector extends Connector {

    private static final String urlBody = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";


//http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?busRouteId=100100117&serviceKey=KhPxLJdPDVQZwVP4PWVyIXCXXaqu3PQUTE%2BSX7fEAYicTAdXjr%2Bhb0%2Bucz%2FHfBpWmTWiLRHMnFBtqCF2AnkX8Q%3D%3D



    private ArrayList<String> interestedStIds;
    private ArrayList<String> interestedBusIds;
    private HashMap<Bus, Coord> nearBuses;

    public HashMap<Bus, Coord> postRun() {
        return nearBuses;
    }

    public void preRun(ArrayList<Station> stations){

        interestedStIds = new ArrayList<String>();
        interestedBusIds = new ArrayList<String>();


        nearBuses = new HashMap<Bus, Coord>();

        for (Station station : stations) {

           int matIdx = BusStationMatrixHolder.getInstance().getDetailStations().indexOf(station);

            BusStationMatrixHolder.getInstance().getAvailableBus(matIdx).forEach(bus -> {
                interestedBusIds.add(bus.getBusId());

            });

            interestedStIds.add(station.getStationId());
        }

    }

    @Override
    public void run() {
        HashMap<String, String> prop;

        String result;
        for (String busId : interestedBusIds) {
            prop = new HashMap<String, String>();
            prop.put("serviceKey", super.serviceKey);
            prop.put("busRouteId", busId);

            result = this.get(this.makeURL(urlBody, prop));
            parse(result);
        }

    }


    @Override
    protected void parse(String result) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(result)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("itemList");

            for(int i = 0; i< nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

               String nextStId = fstElmnt.getElementsByTagName("nextStId").item(0).getChildNodes().item(0).getNodeValue();
               if(interestedStIds.contains(nextStId)){

                   this.nearBuses.put(
                           new Bus(fstElmnt.getElementsByTagName("busRouteId").item(0).getChildNodes().item(0).getNodeValue(),
                                   fstElmnt.getElementsByTagName("rtNm").item(0).getChildNodes().item(0).getNodeValue()),
                           new Coord(Double.parseDouble( fstElmnt.getElementsByTagName("gpsX").item(0).getChildNodes().item(0).getNodeValue()),
                                   Double.parseDouble(  fstElmnt.getElementsByTagName("gpsY").item(0).getChildNodes().item(0).getNodeValue())));

               }
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
