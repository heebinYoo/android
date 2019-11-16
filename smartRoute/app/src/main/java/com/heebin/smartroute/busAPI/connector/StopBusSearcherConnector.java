package com.heebin.smartroute.busAPI.connector;

import com.heebin.smartroute.bean.raw.Bus;
import com.heebin.smartroute.bean.userData.StopBusData;


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

public class StopBusSearcherConnector extends Connector{

    private static final String urlBody = "http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation";

    private String arsId;
    private ArrayList<Bus> busList;

    public void preRun(String arsId) {
        this.arsId = arsId;
    }

    @Override
    public void run() {
        HashMap<String, String> prop;
        if(StopBusData.getInstance().isExist(this.arsId)) {
            busList = StopBusData.getInstance().get(this.arsId);

        }else {
            this.busList = new ArrayList<Bus>();
            prop = new HashMap<String, String>();
            prop.put("ServiceKey", super.serviceKey);
            prop.put("arsId", this.arsId);

            parse(this.get(this.makeURL(urlBody, prop)));
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

                Bus data = new Bus(
                        fstElmnt.getElementsByTagName("busRouteId").item(0).getChildNodes().item(0).getNodeValue(),
                        fstElmnt.getElementsByTagName("busRouteNm").item(0).getChildNodes().item(0).getNodeValue()
                );

                this.busList.add(data);
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
        StopBusData.getInstance().add(this.arsId, this.busList);

    }


    public ArrayList<Bus> postRun(){

        return busList;
    }
}
