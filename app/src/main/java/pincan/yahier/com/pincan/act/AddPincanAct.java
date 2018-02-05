package pincan.yahier.com.pincan.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import pincan.yahier.com.pincan.R;
import pincan.yahier.com.pincan.model.Weatherinfo;
import pincan.yahier.com.pincan.utils.HttpUtils;
import pincan.yahier.com.pincan.utils.LogUtils;
import pincan.yahier.com.pincan.utils.UrlConstant;

/**
 * Created by yahier on 2018/1/25.
 * 发起拼餐页面
 */

public class AddPincanAct extends BaseActivity {
    private final String TAG = "AddPincanAct";
    TextInputLayout s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pincan);
        setTitleText("发起拼餐");
        findViewById(R.id.tvChooseMap).setOnClickListener(v -> {
            Toast.makeText(AddPincanAct.this, "跳转到地图页面", Toast.LENGTH_SHORT).show();
        });

        String url1Post = UrlConstant.host + UrlConstant.pincanPost;
        String url2 = "http://www.weather.com.cn/data/sk/101010100.html";
        findViewById(R.id.btnPost).setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("flavor", "重庆三级辣");
            params.put("personNum", "2");
            HttpUtils.post(url1Post, params, result -> {
                try {
                    Weatherinfo weather = new Gson().fromJson(result, Weatherinfo.class);
                    Weatherinfo.WeatherinfoBean bean = weather.getWeatherinfo();
                    LogUtils.logE(TAG, bean.getCity() + ":" + bean.getCityid());
                } catch (Exception e) {
                    LogUtils.logE(TAG, e.getLocalizedMessage());
                }
            });
        });
    }
}
