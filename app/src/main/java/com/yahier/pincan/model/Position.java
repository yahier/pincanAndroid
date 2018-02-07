package com.yahier.pincan.model;

import java.io.Serializable;

/**
 * Created by yahier on 2018/2/7.
 */

public class Position extends RequestBean implements Serializable {
    public String address;
    public double latitude = 0.0;
    public double longitude = 0.0;
}
