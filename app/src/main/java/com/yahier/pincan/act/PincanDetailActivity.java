package com.yahier.pincan.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yahier.pincan.R;


/**
 * Created by yahier on 2018/2/6.
 */

public class PincanDetailActivity extends BaseActivity {

    Button btnJoin;
    LinearLayout linJoinedPeople;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincan_detail);
        setTitleText("拼餐详情");
        initViews();
    }

    private void initViews() {
        btnJoin = findViewById(R.id.btnJoin);
        linJoinedPeople = findViewById(R.id.linJoinedPeople);
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.wukong);
            linJoinedPeople.addView(imageView);
        }

    }
}
