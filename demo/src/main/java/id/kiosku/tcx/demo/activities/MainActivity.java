package id.kiosku.tcx.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

import id.kiosku.tcx.TCX;
import id.kiosku.tcx.demo.R;
import id.kiosku.tcx.interfaces.OnTokenReceive;
import id.kiosku.tcx.variables.AuthData;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Application ID
        Log.i("ID", TCX.getInstance().getID());

        // Get Master Token for FTC authentication
        Log.i("Master Token", TCX.getInstance().getMasterToken());


        // Get Application Pass without token
        Log.i("Pass", TCX.getInstance().getPass());

        /*
          * Generate Application Pass with Time base token
          * */
        String tokenTime = TCX.getInstance().getPassToken();
        Log.i("Pass Token Time", tokenTime);
        Log.i("Pass with Time Token", TCX.getInstance().getPass(tokenTime));

        /*
         * Generate Application Pass with Parameters base token
         * */
        HashMap<String,String> params = new HashMap<>();
        params.put("id","1");
        params.put("code","abcde");
        String tokenParams = TCX.getInstance().getPassToken(params);
        Log.i("Pass Token Params", tokenParams);
        Log.i("Pass with Params Token", TCX.getInstance().getPass(tokenParams));

        TCX.getInstance().getToken(new OnTokenReceive() {
            @Override
            public void onTokenReceive(AuthData data) {
                Log.i("Token", data.token);
                Log.i("Refresh Token", data.refresh);
                Log.i("Expired", data.expiredAt);
            }

            @Override
            public void onFailed() {
                Log.i("Token", "Failed");
            }

            @Override
            public void onError() {
                Log.e("Token", "Error");
            }
        });
    }
}
