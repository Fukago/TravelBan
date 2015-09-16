package com.example.apple.travelban.module.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseRecyclerActivity;
import com.example.apple.travelban.model.bean.PlaceBean;
import com.example.apple.travelban.utils.ActivityCollector;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.io.UnsupportedEncodingException;

/**
 * Created by apple on 15/8/16.
 */
@RequiresPresenter(PlacePresenter.class)
public class PlaceActivity extends BaseRecyclerActivity<PlacePresenter, PlaceBean.CityResult.Plan.Detail.Place> {
    @Override
    protected void onRefresh() {
        getPresenter().onResponse();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        iniData();
        onRefresh();
    }
    private void iniData() {
        Intent intent = getIntent();
        try {
            getPresenter().setLocation(intent.getStringExtra("location"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setToolBar(true);
        getToolbar().setTitle(intent.getStringExtra("location"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_fanhui);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return super.getLayoutRes();
    }


    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent) {
        return new PlaceViewHolder(parent);
    }
}
