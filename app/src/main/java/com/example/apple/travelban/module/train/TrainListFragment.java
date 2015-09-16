package com.example.apple.travelban.module.train;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.TrainBean;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/8/20.
 */
@RequiresPresenter(TrainListPresenter.class)
public class TrainListFragment extends NucleusFragment<TrainListPresenter> {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TrainBean.TrainList> mContentItems = new ArrayList<>();

    public static TrainListFragment newInstance() {
        return new TrainListFragment();
    }

    public void setData(List<TrainBean.TrainList> trainLists) {
        if (trainLists == null) {
            Toast.makeText(getActivity(), "亲～并没有您所需的信息哦!", Toast.LENGTH_SHORT).show();
        } else {
            mContentItems.clear();
            mContentItems.addAll(trainLists);
            Log.d("item", mContentItems.get(0).getTrainType());
            Log.d("item1", mContentItems.get(0).toString());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmengt_train, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new TrainListAdapter(mContentItems, this));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}

