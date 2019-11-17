package com.heebin.smartroute.util.constants;

public class DataBaseConstatns {
    public static final String DB_NAME = "route";
    public static final int DB_VERSION = 1;

    public static final int TYPE_H2O =1;
    public static final int TYPE_O2H =2;

    public static final String station ="station";
    public static final String stationId ="stationId";
    public static final String stationName ="stationName";
    public static final String gpsX ="gpsX";
    public static final String gpsY ="gpsY";
    public static final String arsId ="arsId";

    public static final String create_station = "create table station " +
            "( " +
            stationId +
            " TEXT, " +
            stationName +
            " TEXT," +
            gpsX +
            " double," +
            gpsY +
            " double," +
            arsId +
            " TEXT primary key);";


    public static final String bus ="bus";
    public static final String busId ="busId";
    public static final String busName ="busName";

    public static final String create_bus = "create table "+bus+
            "(busId TEXT primary key," +
            "busName TEXT);";


    public static final String waypoint ="waypoint";

    public static final String create_waypoint = "create table " +
            waypoint +
            "" +
            "(" +
            busId +
            " TEXT primary key," +
            arsId +
            " TEXT," +
            "FOREIGN KEY(" +
            busId +
            ") REFERENCES " +
            bus +
            "(" +
            busId +
            ")," +
            "FOREIGN KEY(" +
            arsId +
            ") REFERENCES " +
            bus +
            "(" +
            arsId +
            "));";

    public static final String stopbus ="stopbus";
    public static final   String create_stopbus = "create table "+stopbus +
            "("+busId+" TEXT," +
            arsId+" TEXT primary key," +
            "FOREIGN KEY(" +
            busId+") REFERENCES "+bus+"("+busId+")," +
            "FOREIGN KEY("+arsId+") REFERENCES "+bus+"("+arsId+"));";


    public static final String path ="path";

    public static final String takeId ="takeId";
    public static final String takeName ="takeName";
    public static final String takeX ="takeX";
    public static final String takeY ="takeY";
    public static final String offId ="offId";
    public static final String offName ="offName";
    public static final String offX ="offX";
    public static final String offY ="offY";



    public static final  String create_path = "create table "+path +
            "("+takeId +"TEXT," +
            takeName +"TEXT," +
            takeX +"DOUBLE," +
            takeY +"DOUBLE," +
            offId +"TEXT," +
            offName +"TEXT," +
            offX +"DOUBLE," +
            offY +"DOUBLE," +
            "primary key("+takeId+"," +offId+")" +
            ");";

    public static final String route ="route";

    public static final String rid ="rid";
    public static final String estimatedDistance ="estimatedDistance";
    public static final String estimatedTime ="estimatedTime";
    public static final String type ="type";


    public static final   String create_route = "create table "+route +
            "("+rid+" integer primary key autoincrement," +
            estimatedDistance+" integer," +
            estimatedTime+" integer," +
            type+" integer);";


    public static final String detailedstation ="detailedstation";

    public static final String idx ="idx";


    public static final String create_detailedstation = "create table "+detailedstation +
            "(" +
            rid +" integer," +
            arsId +" TEXT," +
            idx +" integer," +
            "primary key("+rid+", "+arsId+")," +
            "FOREIGN KEY("+rid+") REFERENCES route("+rid+")," +
            "FOREIGN KEY("+arsId+") REFERENCES "+station+"("+arsId+")" +
            ");";


    public static final String mainpath ="mainpath";

    public static final String create_mainpath = "create table "+mainpath +
            "(" +
            rid+" integer," +
            takeId+" TEXT," +
            offId+" TEXT," +
            idx +" integer," +
            "primary key("+rid+", "+takeId+", "+offId+")," +
            "FOREIGN KEY("+rid+") REFERENCES "+route+"("+rid+")," +
            "FOREIGN KEY("+takeId+","+ offId+") REFERENCES "+path+"("+takeId+","+ offId+")" +
            ");";
}
