package com.example.apple.travelban.module.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.PlaceBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by apple on 15/8/16.
 */

public class PlaceViewHolder extends BaseViewHolder<PlaceBean.CityResult.Plan.Detail.Place> {
    public TextView mTextView;
    private MaterialRippleLayout container;
    public PlaceViewHolder(final ViewGroup parent) {
        super(parent, R.layout.item_cityviewhodler);
        mTextView=$(R.id.text);
        container=$(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceDetailPresenter presenter=new PlaceDetailPresenter();
                String httpArg=presenter.converterToSpell(mTextView.getText().toString());
                Intent intent=new Intent(parent.getContext(),PlaceDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("httpArg",httpArg);
                bundle.putString("tittle",mTextView.getText().toString());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(PlaceBean.CityResult.Plan.Detail.Place data) {
        super.setData(data);
        mTextView.setText(data.getName());
    }
}
