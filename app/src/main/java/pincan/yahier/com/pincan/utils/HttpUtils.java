package pincan.yahier.com.pincan.utils;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by yahier on 2018/2/5.
 * 网络请求响应的基类
 */

public class HttpUtils {
    final static String TAG = "HttpUtils";
    static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }


    public static void request(String url, OnSucceedListener succeedListener) {
        request(url, succeedListener, null);
    }

    public static void request(String url, OnSucceedListener succeedListener, OnFailedListener failedListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.logE(TAG, e.getLocalizedMessage());
                if (failedListener != null)
                    failedListener.onFailed();
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String result = response.body().string();
                LogUtils.logE(TAG, result);
                succeedListener.onSuccess(result);
            }
        });
    }

    public static void post(String url, HashMap<String, String> params, OnSucceedListener succeedListener) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.add(key, params.get(key));
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.logE(TAG, e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                LogUtils.logE(TAG, result);
                succeedListener.onSuccess(result);
            }
        });
    }


    public interface OnSucceedListener {
        void onSuccess(String result);
    }

    public interface OnFailedListener {
        void onFailed();
    }
}
