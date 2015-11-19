package com.ghostflying.forcemeteredap.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ghostflying.forcemeteredap.R;
import com.ghostflying.forcemeteredap.adapter.BaseAppListAdapter;
import com.ghostflying.forcemeteredap.model.AppInfo;
import com.ghostflying.forcemeteredap.util.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseManagerFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<AppInfo>>,
        SingleChooseDialogFragment.OnDialogButtonClickListener {
    protected int mClickedPosition;
    protected AppInfo mClickedItem;
    protected BaseAppListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.app_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    public BaseManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        mAdapter = getAdapter();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        getLoaderManager().initLoader(0, null, this);
        return v;
    }

    protected abstract BaseAppListAdapter getAdapter();

    protected void getClickedItem(View v){
        mClickedPosition = mRecyclerView.getChildPosition(v);
        mClickedItem = mAdapter.getDataItem(mClickedPosition);
    }

    @Override
    public abstract Loader<List<AppInfo>> onCreateLoader(int id, Bundle args);

    @Override
    public void onLoadFinished(Loader<List<AppInfo>> loader, List<AppInfo> data){
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<AppInfo>> loader){

    }
}
