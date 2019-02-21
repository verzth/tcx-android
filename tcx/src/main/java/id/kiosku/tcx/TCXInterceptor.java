package id.kiosku.tcx;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TCXInterceptor implements Interceptor {
    public static TCXInterceptor create(){
        return new TCXInterceptor();
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String tcxPassToken = TCX.getInstance().getPassToken();
        final String tcxPass = TCX.getInstance().getPass(tcxPassToken);

        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("tcx_datetime", tcxPassToken)
                .build();

        Request.Builder builder = request.newBuilder()
                .addHeader("X-TCX-TYPE", "FTC")
                .addHeader("X-TCX-APP-ID", TCX.getInstance().getID())
                .addHeader("X-TCX-APP-PASS", tcxPass)
                .addHeader("X-TCX-TOKEN", TCX.getInstance().getMasterToken())
                .url(url);

        Request requestDone = builder.build();

        return chain.proceed(requestDone);
    }
}
