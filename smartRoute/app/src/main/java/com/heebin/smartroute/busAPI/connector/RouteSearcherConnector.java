package com.heebin.smartroute.busAPI.connector;

import android.util.Log;

import com.heebin.smartroute.bean.meta.SrcDstStation;
import com.heebin.smartroute.bean.raw.Path;
import com.heebin.smartroute.bean.raw.Route;
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

    private ArrayList<Route> h2orouteList = new ArrayList<Route>();
    private ArrayList<Route> o2hrouteList = new ArrayList<Route>();

    public boolean ish2oDone=false;

    @Override
    public void run() {
        HashMap<String, String> prop;

        String h2oresult;
        String o2hresult;
       UserLocation debug = UserLocation.getInstance();

        for(SrcDstStation x : UserLocation.getInstance().geth2oCatesianSrcDst()) {


            prop = new HashMap<String, String>();
            prop.put("serviceKey", super.serviceKey);
            prop.put("startX", String.format("%.6f", x.src.getGpsX()));
            prop.put("startY", String.format("%.6f", x.src.getGpsY()));
            prop.put("endX", String.format("%.6f", x.dst.getGpsX()));
            prop.put("endY", String.format("%.6f", x.dst.getGpsY()));

            h2oresult = this.get(this.makeURL(urlBody, prop));
            parse(h2oresult);
        }
        ish2oDone=true;

        for(SrcDstStation x : UserLocation.getInstance().geth2oCatesianSrcDst()) {
            prop = new HashMap<String, String>();
            prop.put("serviceKey", super.serviceKey);
            prop.put("startX", String.format("%.6f", x.src.getGpsX()));
            prop.put("startY", String.format("%.6f", x.src.getGpsY()));
            prop.put("endX", String.format("%.6f", x.dst.getGpsX()));
            prop.put("endY", String.format("%.6f", x.dst.getGpsY()));

            o2hresult = this.get(this.makeURL(urlBody, prop));
            parse(o2hresult);
        }


        UserLocation.getInstance().setRouteList(h2orouteList, o2hrouteList);
        int i = 0;
        for(Route x: h2orouteList) {
            Log.d("RouteSearcherConnector", "route" + i++);
            for(Path y: x.getPathList()) {
                Log.d("RouteSearcherConnector", "run: " +y.toString());
            }
        }

    }



    @Override
    protected void parse(String result) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(result)));
            doc.getDocumentElement().normalize();

            if(!ish2oDone) {


                NodeList nodeList = doc.getElementsByTagName("itemList");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    Route data;
                    data = new Route(Integer.parseInt(fstElmnt.getElementsByTagName("distance").item(0).getChildNodes().item(0).getNodeValue()),
                            Integer.parseInt(fstElmnt.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue()));

                    NodeList pathList = fstElmnt.getElementsByTagName("pathList");
                    for (int j = 0; j < pathList.getLength(); j++) {
                        Node pathNode = nodeList.item(i);
                        Element fstPathElmnt = (Element) node;

                        data.addNewPath(
                                fstElmnt.getElementsByTagName("fid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("fname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstElmnt.getElementsByTagName("fx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstElmnt.getElementsByTagName("fy").item(0).getChildNodes().item(0).getNodeValue()),
                                fstElmnt.getElementsByTagName("routeId").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("routeNm").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("tid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("tname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstElmnt.getElementsByTagName("tx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstElmnt.getElementsByTagName("ty").item(0).getChildNodes().item(0).getNodeValue())
                        );
                    }
                    h2orouteList.add(data);
                }

            }
            else if(ish2oDone){


                NodeList nodeList = doc.getElementsByTagName("itemList");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    Route data;
                    data = new Route(Integer.parseInt(fstElmnt.getElementsByTagName("distance").item(0).getChildNodes().item(0).getNodeValue()),
                            Integer.parseInt(fstElmnt.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue()));

                    NodeList pathList = fstElmnt.getElementsByTagName("pathList");
                    for (int j = 0; j < pathList.getLength(); j++) {
                        Node pathNode = nodeList.item(i);
                        Element fstPathElmnt = (Element) node;

                        data.addNewPath(
                                fstElmnt.getElementsByTagName("fid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("fname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstElmnt.getElementsByTagName("fx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstElmnt.getElementsByTagName("fy").item(0).getChildNodes().item(0).getNodeValue()),
                                fstElmnt.getElementsByTagName("routeId").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("routeNm").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("tid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstElmnt.getElementsByTagName("tname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstElmnt.getElementsByTagName("tx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstElmnt.getElementsByTagName("ty").item(0).getChildNodes().item(0).getNodeValue())
                        );
                    }
                    o2hrouteList.add(data);
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
