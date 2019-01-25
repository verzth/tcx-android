package id.kiosku.tcx;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import id.kiosku.tcx.apis.AuthAPI;
import id.kiosku.tcx.apis.AuthReceiver;
import id.kiosku.tcx.drivers.CryptoDriver;
import id.kiosku.tcx.drivers.DatetimeDriver;
import id.kiosku.tcx.drivers.UtilityDriver;
import id.kiosku.tcx.interfaces.OnTokenReceive;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TCX {
    private static TCX anInstance;

    private String URL;
    private String ID;
    private String KEY;
    private String TOKEN;

    public static TCX getInstance() {
        return anInstance;
    }

    public static void init(String URL, String ID, String KEY){
        anInstance = new TCX(URL,ID,KEY);
    }
    public static void init(String URL, String ID, String KEY, String TOKEN){
        anInstance = new TCX(URL,ID,KEY,TOKEN);
    }
    public TCX(String URL, String ID,String KEY){
        this(URL,ID,KEY,null);
    }
    public TCX(String URL, String ID, String KEY, String TOKEN){
        this.URL = URL;
        this.ID = ID;
        this.KEY = KEY;
        this.TOKEN = TOKEN!=null?CryptoDriver.Base64.encode(TOKEN.getBytes(Charset.forName("UTF-8"))):"";
    }

    public String getPassToken(){
        return DatetimeDriver.getAsText();
    }

    public String getPassToken(HashMap<String,String> params){
        String temp = "";
        Map<String,String> sortedMap = new TreeMap<>(params);
        for(Map.Entry<String,String> entry:sortedMap.entrySet()){
            temp = temp.concat(entry.getKey()).concat("=");
            temp = temp.concat(entry.getValue()).concat("&");
        }
        temp = temp.substring(0,temp.length()-1);
        return temp;
    }

    public String getID(){
        return ID;
    }
    public String getPass(){
        return getPass("");
    }
    public String getPass(String token){
        String dynamicToken = UtilityDriver.randomString(16).toLowerCase();
        String pass = CryptoDriver.SHA1(token+KEY+dynamicToken);
        return pass!=null?CryptoDriver.Base64.encode(pass.concat(":").concat(dynamicToken).getBytes(Charset.forName("UTF-8"))):"";
    }
    public String getMasterToken(){
        return TOKEN;
    }

    public void getToken(final OnTokenReceive onTokenReceive){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthAPI authAPI = retrofit.create(AuthAPI.class);
        Call<AuthReceiver> receiverCall = authAPI.authorize(this.ID,getPass());
        receiverCall.enqueue(new Callback<AuthReceiver>() {
            @Override
            public void onResponse(Call<AuthReceiver> call, Response<AuthReceiver> response) {
                if(response.code()==200){
                    if(response.body()!=null){
                        if(response.body().status==1){
                            onTokenReceive.onTokenReceive(response.body().data);
                        }else{
                            onTokenReceive.onFailed();
                        }
                    }else{
                        onTokenReceive.onFailed();
                    }
                }else{
                    onTokenReceive.onError();
                }
            }

            @Override
            public void onFailure(Call<AuthReceiver> call, Throwable t) {
                onTokenReceive.onError();
            }
        });
    }
}
