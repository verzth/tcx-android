package id.kiosku.tcx.variables;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import id.kiosku.tcx.drivers.CryptoDriver;

public class CryptoData {
    @SerializedName("value")
    public String cipher;
    public String iv;
    public String mac;

    public static CryptoData fromString(String raw){
        try {
            byte[] plain = CryptoDriver.Base64.decode(raw);
            return CryptoData.fromJson(new String(plain, "UTF-8"));
        }catch (Exception e){
            return null;
        }
    }

    public static CryptoData fromJson(String json){
        return new Gson().fromJson(json,CryptoData.class);
    }

    @Override
    public String toString() {
        try {
            return CryptoDriver.Base64.encode(toStringRaw().getBytes("UTF-8"));
        }catch (Exception e){return null;}
    }

    public String toStringRaw(){
        return "{\"iv\":\""+iv+"\",\"value\":\""+cipher+"\",\"mac\":\""+mac+"\"}";
    }
}
