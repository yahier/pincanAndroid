package pincan.yahier.com.pincan.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pincan.yahier.com.pincan.R;
import pincan.yahier.com.pincan.model.MainItem;

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
            tvNum = (TextView) v.findViewById(R.id.tvNum);
            tvBudget = (TextView) v.findViewById(R.id.tvBudget);
            lin = v.findViewById(R.id.lin);
            tvBudget.setVisibility(View.VISIBLE);
        }
    }


    List<MainItem> list = null;

    public MainRecycleAdapter(List<MainItem> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (list.size() <= position) return;
        MainItem item = list.get(position);
        if (item == null) return;

        holder.tvNum.setText(item.getTitle());
        if (TextUtils.isEmpty(item.getDes())) {
            holder.tvBudget.setVisibility(View.GONE);
        } else {
            holder.tvBudget.setVisibility(View.VISIBLE);
            holder.tvBudget.setText(item.getDes());
        }

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClck(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 18;
    }


    public void setOnItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClck(int i);
    }

}
