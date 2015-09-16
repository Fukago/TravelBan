package com.example.apple.travelban.module.ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.AdBean;
import com.example.apple.travelban.module.place.PlaceActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.DynamicPagerAdapter;

import java.io.InputStream;
import java.util.List;

/**
 * Created by apple on 15/8/24.
 */
public class AdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AdBean.AdData.Books> contents;
    private AdFragment mFragment;

    public AdAdapter(List<AdBean.AdData.Books> list, AdFragment fragment) {
        contents = list;
        mFragment = fragment;
    }

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_roll, parent, false);
                return new RollViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_ad, parent, false);
                return new ViewHolder(view) {
                };
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER: {
                RollViewHolder viewHolder = (RollViewHolder) holder;
                viewHolder.mRollViewPager.setAdapter(new RollAdapter(mFragment));

                break;
            }
            case TYPE_CELL: {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mFragment.getActivity(), ADWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", contents.get(position - 1).getBookUrl());
                        intent.putExtras(bundle);
                        mFragment.startActivity(intent);
                    }
                });
                viewHolder.text.setText("他／她的足迹:\n      " + contents.get(position - 1).getText());
                viewHolder.title.setText(contents.get(position-1).getTitle());
                break;
            }
        }

    }


    @Override
    public int getItemCount() {
        return contents.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView text;
        public LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tittle);
            text = (TextView) itemView.findViewById(R.id.text);
            mLinearLayout= (LinearLayout) itemView.findViewById(R.id.ad_LinearLayout);
            title.setEllipsize(TextUtils.TruncateAt.END);
            title.setLines(1);
            text.setEllipsize(TextUtils.TruncateAt.END);
            text.setLines(3);
        }

    }

    public static class RollViewHolder extends RecyclerView.ViewHolder {
        private RollPagerView mRollViewPager;
        public RollViewHolder(View view) {
            super(view);
            mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        }
    }

    class RollAdapter extends DynamicPagerAdapter {
        private AdFragment mFragment;

        public RollAdapter(AdFragment fragment) {
            mFragment = fragment;
        }

        private int[] imgs = {
                R.drawable.gugong,
                R.drawable.shanghai,
                R.drawable.hangzhou,
                R.drawable.chongqin,
        };

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public View getView(ViewGroup viewGroup, final int i) {
            ImageView view = new ImageView(viewGroup.getContext());
            InputStream is = mFragment.getActivity().getResources().openRawResource(imgs[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap btp = BitmapFactory.decodeStream(is, null, options);
            view.setImageBitmap(btp);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (i){
                        case 0:
                            intent.putExtra("location", "北京");
                            intent.setClass(mFragment.getActivity(), PlaceActivity.class);
                            Log.d("page0","true");
                            break;
                        case 1:
                            intent.putExtra("location", "上海");
                            intent.setClass(mFragment.getActivity(), PlaceActivity.class);
                            Log.d("page1","true");
                            break;
                        case 2:
                            intent.putExtra("location", "杭州");
                            intent.setClass(mFragment.getActivity(), PlaceActivity.class);
                            Log.d("page2","true");
                            break;
                        default:
                            intent.putExtra("location", "重庆");
                            intent.setClass(mFragment.getActivity(), PlaceActivity.class);
                            Log.d("page3","true");
                            break;
                    }
                    mFragment.startActivity(intent);
                }
            });
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

    }

}