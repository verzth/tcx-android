package id.kiosku.tcx.demo;

import android.app.Application;

import id.kiosku.tcx.TCX;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize TCX with given Credentials
        TCX.init("http://demo.com/","DEMO","DEMO123","DEMOKEY123");
    }
}
