package com.example.apple.travelban.module.ad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.AdBean;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/8/21.
 */
@RequiresPresenter(Adpresenter.class)
public class AdFragment extends NucleusFragment<Adpresenter> {
    private RecyclerView mRecyclerView;
    private ActionButton mActionButton;
    private RecyclerView.Adapter mAdapter;
    private List<AdBean.AdData.Books> mContentItems = new ArrayList<>();

    public static AdFragment newInstance() {
        return new AdFragment();
    }

    public void setData(List<AdBean.AdData.Books> trainLists) {
        mContentItems.clear();
        mContentItems.addAll(trainLists);
        Log.d("item", mContentItems.get(0).getBookUrl());
        Log.d("item1", mContentItems.get(0).toString());
        mAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().onResponse();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mActionButton = (ActionButton) view.findViewById(R.id.actionButton_fragment_ad);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new AdAdapter(mContentItems, this));
        mRecyclerView.setAdapter(mAdapter);

        setScrollListener();

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    private void setScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >= 1){
                    mActionButton.hide();
                }else if (dy <= -1){
                    mActionButton.show();
                }
            }
        });
    }


}
