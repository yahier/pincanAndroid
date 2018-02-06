package pincan.yahier.com.pincan.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.google.gson.Gson;

import pincan.yahier.com.pincan.R;
import pincan.yahier.com.pincan.model.Pincan;
import pincan.yahier.com.pincan.model.Weatherinfo;
import pincan.yahier.com.pincan.utils.DialogUtils;
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

    AppCompatEditText inputPersonNum, inputDishType, inputFlavor, inputBudget, inputChooseMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pincan);
        setTitleText("发起拼餐");
        initViews();
    }

    private void initViews() {
        inputPersonNum = findViewById(R.id.inputPersonNum);
        inputDishType = findViewById(R.id.inputDishType);
        inputFlavor = findViewById(R.id.inputFlavor);
        inputBudget = findViewById(R.id.inputBudget);
        inputChooseMap = findViewById(R.id.inputChooseMap);

        //选择地图
        inputChooseMap.setOnClickListener(v -> {
            Toast.makeText(AddPincanAct.this, "跳转到地图页面", Toast.LENGTH_SHORT).show();
        });

        //选择菜系
        inputDishType.setOnClickListener(v -> {
            String[] dishType = getResources().getStringArray(R.array.dish_type);
            DialogUtils.showList(AddPincanAct.this, dishType, selectedDish -> {
                inputDishType.setText(selectedDish);
            });
        });

        inputFlavor.setOnClickListener(v -> {
            String[] dishType = getResources().getStringArray(R.array.flavor_type);
            DialogUtils.showList(AddPincanAct.this, dishType, selectedDish -> {
                inputFlavor.setText(selectedDish);
            });
        });
        findViewById(R.id.btnPost).setOnClickListener(v -> {
            doCommit();
        });

    }

    private void doCommit() {
        Pincan pincan = new Pincan();
        pincan.budget = inputBudget.getText().toString();
        pincan.dishType = inputDishType.getText().toString();
        pincan.flavor = inputFlavor.getText().toString();
        pincan.personNum = inputPersonNum.getText().toString();

        String url1Post = UrlConstant.host + UrlConstant.pincanPost;
        String url2 = "http://www.weather.com.cn/data/sk/101010100.html";
        HttpUtils.post(url1Post, pincan, result -> {
            try {
                Weatherinfo weather = new Gson().fromJson(result, Weatherinfo.class);
                Weatherinfo.WeatherinfoBean bean = weather.getWeatherinfo();
                LogUtils.logE(TAG, bean.getCity() + ":" + bean.getCityid());
            } catch (Exception e) {
                LogUtils.logE(TAG, e.getLocalizedMessage());
            }
        });
    }


}
