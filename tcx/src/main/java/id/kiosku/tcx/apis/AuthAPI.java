package id.kiosku.tcx.apis;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPI {
    @FormUrlEncoded
    @POST("tcx/authorize")
    Call<AuthReceiver> authorize(@Field("app_id") String appId, @Field("app_pass") String appPass);

    @FormUrlEncoded
    @POST("tcx/reauthorize")
    Call<AuthReceiver> reauthorize(@Field("app_id") String appId, @Field("token") String token);
}
