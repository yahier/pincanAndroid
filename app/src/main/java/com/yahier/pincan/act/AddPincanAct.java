package com.yahier.pincan.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.yahier.pincan.R;
import com.yahier.pincan.model.Pincan;
import com.yahier.pincan.model.Position;
import com.yahier.pincan.model.Weatherinfo;
import com.yahier.pincan.utils.DialogUtils;
import com.yahier.pincan.utils.HttpUtils;
import com.yahier.pincan.utils.LogUtils;
import com.yahier.pincan.utils.UrlConstant;


/**
 * Created by yahier on 2018/1/25.
 * 发起拼餐页面
 */

public class AddPincanAct extends BaseActivity {
    private final String TAG = "AddPincanAct";
    Pincan pincan = new Pincan();
    final int requestCodeMap = 100;

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
            Intent intent = new Intent(AddPincanAct.this, MapChooseLocationActivity.class);
            startActivityForResult(intent, requestCodeMap);
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
        pincan.budget = inputBudget.getText().toString();
        pincan.dishType = inputDishType.getText().toString();
        pincan.flavor = inputFlavor.getText().toString();
        pincan.personNum = inputPersonNum.getText().toString();
        pincan.address = inputChooseMap.getText().toString();

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case requestCodeMap:
                    if (data != null) {
                        Position position = (Position) data.getSerializableExtra("position");
                        inputChooseMap.setText(position.address);
                        pincan.latitude = position.latitude;
                        pincan.longitude = position.longitude;
                    }


            }
        }
    }


}
