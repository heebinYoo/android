package com.heebin.smartroute.busAPI.connector.async;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.data.bean.meta.SrcDstStation;
import com.heebin.smartroute.data.bean.raw.Route;
import com.heebin.smartroute.data.inMemory.initRelated.NearStationData;
import com.heebin.smartroute.data.inMemory.initRelated.RouteData;

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
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RouteSearcherConnector extends Connector {

    private static final String urlBody = "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus";

    private ArrayList<Route> h2orouteList = new ArrayList<Route>();
    private ArrayList<Route> o2hrouteList = new ArrayList<Route>();

    public boolean ish2oDone=false;

    private boolean isExist(SrcDstStation x, Iterator<Route> itr){

        Route temp;
        while (itr.hasNext()){
            temp = itr.next();
            if(x.src.getStationId().equals(temp.getPathList().get(0).getTakeId()) &&
                    x.dst.getStationId().equals(temp.getPathList().get(temp.getPathList().size()-1).getOffId())){
                return true;
            }
        }
        return false;
    }
    @Override
    public void run() {
        HashMap<String, String> prop;

        for(SrcDstStation x : NearStationData.getInstance().geth2oCatesianSrcDst()) {

           if( ! isExist(x, RouteData.getInstance().getH2oRouteList().iterator())) {
               prop = new HashMap<String, String>();
               prop.put("serviceKey", super.serviceKey);
               prop.put("startX", String.format("%.6f", x.src.getGpsX()));
               prop.put("startY", String.format("%.6f", x.src.getGpsY()));
               prop.put("endX", String.format("%.6f", x.dst.getGpsX()));
               prop.put("endY", String.format("%.6f", x.dst.getGpsY()));

               String h2oresult = this.get(this.makeURL(urlBody, prop));
               parse(h2oresult);
           }
        }
        ish2oDone=true;

        for(SrcDstStation x : NearStationData.getInstance().geto2hCatesianSrcDst()) {
            if( ! isExist(x, RouteData.getInstance().getO2hRouteList().iterator())) {

                prop = new HashMap<String, String>();
                prop.put("serviceKey", super.serviceKey);
                prop.put("startX", String.format("%.6f", x.src.getGpsX()));
                prop.put("startY", String.format("%.6f", x.src.getGpsY()));
                prop.put("endX", String.format("%.6f", x.dst.getGpsX()));
                prop.put("endY", String.format("%.6f", x.dst.getGpsY()));

                String o2hresult = this.get(this.makeURL(urlBody, prop));
                parse(o2hresult);
            }
        }

        RouteData.getInstance().appendRouteList(h2orouteList, o2hrouteList);


//        int i = 0;
//        for(Route x: o2hrouteList) {
//            Log.d("RouteSearcherConnector", "route" + i++);
//            for(Path y: x.getPathList()) {
//                Log.d("RouteSearcherConnector", "run: " +y.toString());
//            }
//        }



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
                    int distance =Integer.parseInt(fstElmnt.getElementsByTagName("distance").item(0).getChildNodes().item(0).getNodeValue());
                    int time = Integer.parseInt(fstElmnt.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());
                    data = new Route(time, distance);

                    NodeList pathList = fstElmnt.getElementsByTagName("pathList");

                    for (int j = 0; j < pathList.getLength(); j++) {
                        Node pathNode = pathList.item(j);
                        Element fstPathElmnt = (Element) pathNode;

                        data.addNewPath(
                                fstPathElmnt.getElementsByTagName("fid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("fname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("fx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("fy").item(0).getChildNodes().item(0).getNodeValue()),
                                fstPathElmnt.getElementsByTagName("routeId").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("routeNm").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("tid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("tname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("tx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("ty").item(0).getChildNodes().item(0).getNodeValue())
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
                    int distance =Integer.parseInt(fstElmnt.getElementsByTagName("distance").item(0).getChildNodes().item(0).getNodeValue());
                    int time = Integer.parseInt(fstElmnt.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());
                    data = new Route(time, distance);

                    NodeList pathList = fstElmnt.getElementsByTagName("pathList");

                    for (int j = 0; j < pathList.getLength(); j++) {
                        Node pathNode = pathList.item(j);
                        Element fstPathElmnt = (Element) pathNode;

                        data.addNewPath(
                                fstPathElmnt.getElementsByTagName("fid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("fname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("fx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("fy").item(0).getChildNodes().item(0).getNodeValue()),
                                fstPathElmnt.getElementsByTagName("routeId").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("routeNm").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("tid").item(0).getChildNodes().item(0).getNodeValue(),
                                fstPathElmnt.getElementsByTagName("tname").item(0).getChildNodes().item(0).getNodeValue(),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("tx").item(0).getChildNodes().item(0).getNodeValue()),
                                Double.parseDouble(fstPathElmnt.getElementsByTagName("ty").item(0).getChildNodes().item(0).getNodeValue())
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
