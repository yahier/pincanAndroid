package pincan.yahier.com.pincan.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pincan.yahier.com.pincan.R;
import pincan.yahier.com.pincan.adapter.MainRecycleAdapter;
import pincan.yahier.com.pincan.model.Pincan;

/**
 * 首页 可供选择的拼餐列表页面
 */
public class MainActivity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        findViewById(R.id.btnPost).setOnClickListener(v -> startActivity(AddPincanAct.class));
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        List<Pincan> list = new ArrayList<>();
        MainRecycleAdapter adapter = new MainRecycleAdapter(list, i -> {

            Intent intent = new Intent(MainActivity.this, PincanDetailActivity.class);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);


        System.out.println(this);
    }
}
