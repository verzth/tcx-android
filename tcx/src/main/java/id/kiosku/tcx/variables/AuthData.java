package id.kiosku.tcx.variables;

import com.google.gson.annotations.SerializedName;

public class AuthData {
    public String token;
    public String refresh;
    @SerializedName("expired_at")
    public String expiredAt;
}
