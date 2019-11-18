package com.heebin.smartroute.data.bean.meta;

import androidx.annotation.Nullable;

public class Coord {
    public double X;
    public double Y;

    public Coord(double x, double y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof  Coord){
            Coord dst = (Coord) obj;
            if(dst.X-X < 0.00000000001 && dst.Y-Y<0.00000000001){
                return true;
            }
        }
        return  false;
    }

    @Override
    public int hashCode() {


        return (int) X+ (int)Y;
    }
}
