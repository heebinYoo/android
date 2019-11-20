package com.heebin.smartroute.busAPI.connector.sync;

import android.location.Location;
import android.util.Log;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.data.bean.meta.Coord;
import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.learning.Matrix.BusStationMatrixHolder;
import com.heebin.smartroute.util.Distance;

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
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class StationArriveSearcherConnector extends Connector {

    private static final String urlBody = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";


//


    private ArrayList<String> interestedArsIds;
    private ArrayList<Bus> interestedBuses;
    private HashMap<Bus, Coord> nearBuses;
    private Location location;

    public HashMap<Bus, Coord> postRun() {
        return nearBuses;
    }

    public void preRun(ArrayList<Station> stations, Location location) {

        interestedArsIds = new ArrayList<String>();
        interestedBuses = new ArrayList<Bus>();
        this.location=location;


        nearBuses = new HashMap<Bus, Coord>();
        HashSet<Bus> busHashSet = new HashSet<>();

        for (Station station : stations) {

            int matIdx = BusStationMatrixHolder.getInstance().getDetailStations().indexOf(station);

            BusStationMatrixHolder.getInstance().getAvailableBus(matIdx).forEach(bus -> {
                busHashSet.add(bus);
            });

            interestedArsIds.add(station.getArsId());
        }

        interestedBuses.addAll(busHashSet);
       // Log.d("StationArriveSearcherConnector", "preRun: Done");

    }

    @Override
    public void run() {
        HashMap<String, String> prop;

        String result;
        for (Bus bus : interestedBuses) {
            prop = new HashMap<String, String>();
            prop.put("serviceKey", super.serviceKey);
            prop.put("busRouteId", bus.getBusId());

            result = this.get(this.makeURL(urlBody, prop));
            addtionalParmBus=bus;

            parse(result);
        }

    }


   private Bus addtionalParmBus;

    @Override
    protected void parse(String result) {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(result)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("itemList");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;


                //String nextStId = fstElmnt.getElementsByTagName("nextStId").item(0).getChildNodes().item(0).getNodeValue();

                double gpsX =Double.parseDouble(fstElmnt.getElementsByTagName("gpsX").item(0).getChildNodes().item(0).getNodeValue());
                double gpsY = Double.parseDouble(fstElmnt.getElementsByTagName("gpsY").item(0).getChildNodes().item(0).getNodeValue());

                if(Distance.distance(location.getLatitude(),location.getLongitude(),gpsY,gpsX)<1000){
                    this.nearBuses.put(addtionalParmBus, new Coord(gpsX,gpsY));
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
