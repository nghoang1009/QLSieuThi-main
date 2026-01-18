package com.mycompany.qlst.Helpers;

public class GlobalAccessPoint {
    private static final GlobalAccessPoint INSTANCE = new GlobalAccessPoint();
    public String chucVuNguoiDung;

    private GlobalAccessPoint(){}

    public GlobalAccessPoint getInstance() {
        return INSTANCE;
    }
}
