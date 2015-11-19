package com.ghostflying.forcemeteredap.ui.fragment;

import android.app.DialogFragment;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;

import com.ghostflying.forcemeteredap.R;
import com.ghostflying.forcemeteredap.adapter.BaseAppListAdapter;
import com.ghostflying.forcemeteredap.adapter.MeteredAdapter;
import com.ghostflying.forcemeteredap.loader.MeteredLoader;
import com.ghostflying.forcemeteredap.model.AppInfo;
import com.ghostflying.forcemeteredap.util.PreferencesUtil;

import java.util.List;

/**
 * Created by ghostflying on 15-11-19.
 */
public class MeteredFragment extends BaseManagerFragment {
    public static MeteredFragment newInstance(){
        return new MeteredFragment();
    }

    @Override
    protected BaseAppListAdapter getAdapter() {
        return new MeteredAdapter(
                mOnItemClickListener,
                getResources().getColor(R.color.modified_status_text_color),
                getResources().getColor(R.color.unmodified_status_text_color)
        );
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getClickedItem(v);
            int meteredStatus = getSelectedMeteredStatus(mClickedItem.getMeteredStatus());
            DialogFragment mDialog = SingleChooseDialogFragment.newInstance(R.string.metered_dialog_title, R.array.status_metered_array, meteredStatus);
            mDialog.setTargetFragment(MeteredFragment.this, 0);
            mDialog.show(getFragmentManager(), null);
        }
    };

    private int getSelectedMeteredStatus(int meteredStatus){
        switch (meteredStatus){
            case PreferencesUtil.METERED_FORCE_NOT:
                return 1;
            case PreferencesUtil.METERED_FORCE:
                return 2;
            case PreferencesUtil.METERED_AUTO:
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public Loader<List<AppInfo>> onCreateLoader(int id, Bundle args) {
        return new MeteredLoader(getActivity());
    }

    @Override
    public void onPositiveButtonClick(int value, int title) {
        int setMeteredStatus;
        switch (value){
            case 1:
                setMeteredStatus = PreferencesUtil.METERED_FORCE_NOT;
                break;
            case 2:
                setMeteredStatus = PreferencesUtil.METERED_FORCE;
                break;
            case 3:
                setMeteredStatus = PreferencesUtil.METERED_AUTO;
                break;
            default:
                setMeteredStatus = PreferencesUtil.DEFAULT_METERED;
        }
        mClickedItem.setMetered(setMeteredStatus);
        mAdapter.notifyItemChanged(mClickedPosition);
    }
}
