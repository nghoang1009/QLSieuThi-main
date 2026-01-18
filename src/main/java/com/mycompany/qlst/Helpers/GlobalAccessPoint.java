package com.mycompany.qlst.Helpers;

public class GlobalAccessPoint {
    private static final GlobalAccessPoint INSTANCE = new GlobalAccessPoint();
    public String chucVuNguoiDung, username;

    private GlobalAccessPoint(){}

    public static GlobalAccessPoint getInstance() {
        return INSTANCE;
    }
}
