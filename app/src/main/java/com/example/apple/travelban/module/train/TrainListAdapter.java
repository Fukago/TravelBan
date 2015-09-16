package com.example.apple.travelban.module.train;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.TrainBean;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TrainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private TrainListFragment mFragment;
    List<TrainBean.TrainList> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TrainListAdapter(List<TrainBean.TrainList> contents, TrainListFragment fragment) {
        this.contents = contents;
        mFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_big, parent, false);
                return new BigViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_small, parent, false);
                return new SmallViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER: {
                final BigViewHolder viewHolder = (BigViewHolder) holder;
                viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragment.getPresenter().setData(viewHolder.startTime.getText().toString(), viewHolder.from.getText().toString(), viewHolder.to.getText().toString());
                        Log.d("data", viewHolder.startTime.getText().toString());
                        mFragment.getPresenter().response();

                    }
                });
                break;
            }
            case TYPE_CELL: {
                if (!contents.isEmpty()) {
                    SmallViewHolder viewHolder = (SmallViewHolder) holder;
                    viewHolder.trainType.setText(" （" + contents.get(position - 1).getTrainType() + "）");
                    viewHolder.trainNo.setText(contents.get(position - 1).getTrainNo());
                    viewHolder.from.setText(contents.get(position - 1).getFrom());
                    viewHolder.to.setText("－>" + contents.get(position - 1).getTo());
                    viewHolder.startTime.setText(contents.get(position - 1).getStartTime());
                    viewHolder.endTime.setText("－" + contents.get(position - 1).getEndTime());
                    viewHolder.duration.setText(contents.get(position - 1).getDuration());
                    viewHolder.container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mFragment.getActivity(), "亲～目前只支持查询哦。", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            }
        }

    }

    public static class SmallViewHolder extends RecyclerView.ViewHolder {
        public TextView trainType;
        public TextView trainNo;
        public TextView from;
        public TextView to;
        public TextView startTime;
        public TextView endTime;
        public TextView duration;
        public MaterialRippleLayout container;

        public SmallViewHolder(View itemView) {
            super(itemView);
            trainType = (TextView) itemView.findViewById(R.id.trainType);
            trainNo = (TextView) itemView.findViewById(R.id.trainNo);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            startTime = (TextView) itemView.findViewById(R.id.startTime);
            endTime = (TextView) itemView.findViewById(R.id.endTime);
            duration = (TextView) itemView.findViewById(R.id.duration);
            container = (MaterialRippleLayout) itemView.findViewById(R.id.container);

        }
    }

    public static class BigViewHolder extends RecyclerView.ViewHolder {
        public Button mButton;
        public MaterialEditText from;
        public MaterialEditText to;
        public MaterialEditText startTime;
        public ImageView fromImage;
        public ImageView toImage;
        public ImageView timeImage;

        public BigViewHolder(View itemView) {
            super(itemView);
            mButton = (Button) itemView.findViewById(R.id.item_button);
            from = (MaterialEditText) itemView.findViewById(R.id.from);
            to = (MaterialEditText) itemView.findViewById(R.id.to);
            startTime = (MaterialEditText) itemView.findViewById(R.id.startime);
            fromImage = (ImageView) itemView.findViewById(R.id.from_image);
            toImage = (ImageView) itemView.findViewById(R.id.to_image);
            timeImage = (ImageView) itemView.findViewById(R.id.startime_image);
        }

    }
}
