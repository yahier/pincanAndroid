package pincan.yahier.com.pincan.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pincan.yahier.com.pincan.R;
import pincan.yahier.com.pincan.model.Pincan;

/**
 * Created by yahier on 2018/1/25.
 * 主页面的推荐拼餐列表
 */

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNum;
        public TextView tvBudget;
        public View lin;

        public ViewHolder(View v) {
            super(v);
            tvNum = v.findViewById(R.id.tvNum);
            tvBudget = v.findViewById(R.id.tvBudget);
            lin = v.findViewById(R.id.pincanlin);
            tvBudget.setVisibility(View.VISIBLE);
        }
    }


    List<Pincan> list = null;

    public MainRecycleAdapter(List<Pincan> list, OnItemClickListener listener) {
        this.listener = listener;
        this.list = list;

        //虚造数据
        if (list.size() == 0) {
            for (int i = 0; i < 20; i++) {
                Pincan pincan = new Pincan();
                pincan.personNum = "3人";
                pincan.flavor = "微辣";
                pincan.dishType = "客家菜";
                pincan.budget = "60";
                list.add(pincan);
            }
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (list.size() <= position) return;
        Pincan item = list.get(position);
        if (item == null) return;

        holder.tvNum.setText(item.personNum);
        if (TextUtils.isEmpty(item.budget)) {
            holder.tvBudget.setVisibility(View.GONE);
        } else {
            holder.tvBudget.setVisibility(View.VISIBLE);
            holder.tvBudget.setText(item.budget);
        }

        holder.lin.setOnClickListener(v -> listener.onItemClck(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClck(int i);
    }

}
