package com.example.apple.travelban.module.travel;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.module.place.PlaceActivity;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import java.io.InputStream;


/**
 * Created by apple on 15/8/19.
 */
public class CityClassFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String[] cities = {
            "北京",
            "天津",
            "上海",
            "重庆",
            "昆明",
            "杭州",
            "成都",
            "南京",
            "长沙",
            "西安",
            "海口",
            "广州",
            "石家庄",
            "郑州",
            "合肥",
            "台北",
            "南昌",
            "深圳",
    };
    private int[] citiesId = {
            R.drawable.ic_beijing,
            R.drawable.ic_tianjing,
            R.drawable.ic_shanghai,
            R.drawable.ic_chongqing,
            R.drawable.ic_kunming,
            R.drawable.ic_hangzhou,
            R.drawable.ic_chengdu,
            R.drawable.ic_nanjing,
            R.drawable.ic_changsha,
            R.drawable.ic_xian,
            R.drawable.ic_haikou,
            R.drawable.ic_guangzhou,
            R.drawable.ic_shijiazhuang,
            R.drawable.ic_zhengzhou,
            R.drawable.ic_hefei,
            R.drawable.ic_taibei,
            R.drawable.ic_nanchang,
            R.drawable.ic_shenzheng,
    };
    private String[] citiesDescription = {
            "表面上它是现代大都会，但内心却有抹不去的古朴和怀旧。闲庭信步在逐渐少去的胡同里，走进那热气腾腾的涮肉店，那才是真正的北京。",
            "海河之畔的一颗渤海明珠，这座中国北方第一大港口，既带有西方殖民时代的烙印、又饱含传统的中华民俗。",
            "古老斑驳的石库门，高脚杯、咖啡吧和老唱片里都流淌着香艳的上海情，去看看小洋楼的小资情调，再品品地道上海菜中的上海味道。",
            "它依山建筑是“山城”，它云轻雾重是“雾都”，它夏长酷热是“火炉”。",
            "这是一座看上去没有强烈主题的城市，慵懒地绽放在西南这块香气四溢的土地上，在这里仿佛只有阳光和春色才是永恒的。",
            "杭州享“人间天堂”美誉，西湖、朦胧烟雨、龙井茶香……将温婉的江南展现在你面前。",
            "一座来了就不想走的城市，一个让时间慢下来的“休闲之都”。",
            "传奇与典故，写下南京的沧桑；江河与湖泊，铸就南京的魂魄。",
            "青葱的岳麓山，火红的口味虾，深灰的西汉夫人，一座制造快乐的山水洲城。",
            "有历史感的古城墙和兵马俑，绝对让你大饱口福的各色美食。滚滚红尘帝王都，悠悠岁月百姓城。",
            "迷人的碧海蓝天，旖旎的海底世界，茂密的热带丛林，传奇的少数民族风情。",
            "铺天盖地的粤语、大街小巷的鲜花、美味的粥品靓汤，让现代化的广州，仍保留着市井气息，漫步街头，各色靓仔靓女定会吸引你的眼球。",
            "古代称“常山、真定”，历史上曾与北京、保定并称“北方三雄镇”。",
            "曾经华夏文明的发源地，承载着璀璨的千年文明，现在的它虽已洗尽铅华。",
            "三国故地，包公故里，这里美丽的风光和人文底蕴交织成熠熠生辉的徽山皖水。",
            "在101看夕阳超赞，在西门町享受夜市的热闹，时尚与怀旧，现代与传统。",
            "特色美食很多，美味滋补的瓦罐汤、地道的米粉肉，让人胃口大开。",
            "深圳人的生活节奏很快，非常美丽的城市。深圳的道路比较宽阔，交通方便。",
    };

    public static CityClassFragment newInstance() {
        return new CityClassFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cityclass, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new CityAdapter(cities, citiesId, citiesDescription,this));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


    private class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private String[] cities;
        private int[] citiesId;
        private String[] citiesDescription;
        private CityClassFragment mFragment;

        public CityAdapter(String[] cities, int[] citiesId, String[] citiesDescription,CityClassFragment cityClassFragment) {
            this.cities = cities;
            this.citiesId = citiesId;
            mFragment = cityClassFragment;
            this.citiesDescription=citiesDescription;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_city_class, parent, false);
            return new CityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final CityViewHolder viewHolder = (CityViewHolder) holder;
            viewHolder.mTextView.setText(cities[position]);
            viewHolder.descroption.setText(citiesDescription[position]);
            ImageView view = new ImageView(mFragment.getActivity());
            InputStream is = mFragment.getActivity().getResources().openRawResource(citiesId[position]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap btp = BitmapFactory.decodeStream(is, null, options);
            viewHolder.mImageView.setImageBitmap(btp);
            viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("location", viewHolder.mTextView.getText());
                    intent.setClass(mFragment.getActivity(),PlaceActivity.class);
                    mFragment.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cities.length;
        }

        private class CityViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTextView;
            private TextView descroption;
            public LinearLayout mLinearLayout;
            public CityViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.item_image);
                mTextView = (TextView) view.findViewById(R.id.item_text);
                descroption= (TextView) view.findViewById(R.id.item_description);
                mLinearLayout= (LinearLayout) itemView.findViewById(R.id.ad_LinearLayout);

            }
        }
    }
}
