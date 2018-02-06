package pincan.yahier.com.pincan.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import pincan.yahier.com.pincan.R;

/**
 * Created by yahier on 2018/2/6.
 */

public class PincanDetailActivity extends BaseActivity {

    Button btnJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincan_detail);
        setTitleText("拼餐详情");
        initViews();
    }

    private void initViews() {
        btnJoin = findViewById(R.id.btnJoin);
    }
}
