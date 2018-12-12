package id.kiosku.tcx.interfaces;

import id.kiosku.tcx.variables.AuthData;

public interface OnTokenReceive {
    void onTokenReceive(AuthData data);
    void onFailed();
    void onError();
}
