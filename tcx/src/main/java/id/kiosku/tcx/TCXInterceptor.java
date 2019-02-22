package id.kiosku.tcx;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TCXInterceptor implements Interceptor {
    private TCX tcx;
    public TCXInterceptor(){}
    public TCXInterceptor(TCX tcx){
        this.tcx = tcx;
    }
    public static TCXInterceptor create(){
        return new TCXInterceptor();
    }
    public static TCXInterceptor create(TCX tcx){
        return new TCXInterceptor(tcx);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        if(this.tcx == null) this.tcx = TCX.getInstance();
        final String tcxPassToken = this.tcx.getPassToken();
        final String tcxPass = this.tcx.getPass(tcxPassToken);

        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("tcx_datetime", tcxPassToken)
                .build();

        Request.Builder builder = request.newBuilder()
                .addHeader("X-TCX-TYPE", "FTC")
                .addHeader("X-TCX-APP-ID", this.tcx.getID())
                .addHeader("X-TCX-APP-PASS", tcxPass)
                .addHeader("X-TCX-TOKEN", this.tcx.getMasterToken())
                .url(url);

        Request requestDone = builder.build();

        return chain.proceed(requestDone);
    }
}
