package com.heebin.smartroute.busAPI.connector.sync;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.data.inMemory.caching.WayPointData;

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

public class WayPointSearcherConnector extends Connector {
    private static final String urlBody = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";


    private String busId;
    private ArrayList<Station> stations;
    public void preRun(String busId) {
        this.busId = busId;


    }

    @Override
    public void run() {
        HashMap<String, String> prop;
        if(WayPointData.getInstance().isExist(this.busId)) {
            stations = WayPointData.getInstance().get(this.busId);

        }else {
            this.stations = new ArrayList<Station>();
            prop = new HashMap<String, String>();
            prop.put("serviceKey", super.serviceKey);
            prop.put("busRouteId", this.busId);

            parse(this.get(this.makeURL(urlBody, prop)));
            WayPointData.getInstance().add(this.busId, this.stations);
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

                for (int i = 0; i < nodeList.getLength(); i++) {


                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    Station data = new Station(
                            fstElmnt.getElementsByTagName("station").item(0).getChildNodes().item(0).getNodeValue(),
                            fstElmnt.getElementsByTagName("stationNm").item(0).getChildNodes().item(0).getNodeValue(),
                            Double.parseDouble(fstElmnt.getElementsByTagName("gpsX").item(0).getChildNodes().item(0).getNodeValue()),
                            Double.parseDouble(fstElmnt.getElementsByTagName("gpsY").item(0).getChildNodes().item(0).getNodeValue()),
                            fstElmnt.getElementsByTagName("arsId").item(0).getChildNodes().item(0).getNodeValue()
                    );

                    stations.add(data);
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


    public ArrayList<Station> postRun(){

        return stations;
    }
}
