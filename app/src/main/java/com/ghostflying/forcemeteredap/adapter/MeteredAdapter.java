package com.ghostflying.forcemeteredap.adapter;

import android.view.View;
import android.widget.TextView;

import com.ghostflying.forcemeteredap.R;
import com.ghostflying.forcemeteredap.model.AppInfo;
import com.ghostflying.forcemeteredap.util.PreferencesUtil;

/**
 * Created by ghostflying on 15-11-19.
 */
public class MeteredAdapter extends BaseAppListAdapter {

    public MeteredAdapter(View.OnClickListener itemClickListener, int modifiedTextColor, int unmodifiedTextColor){
        super(itemClickListener, modifiedTextColor, unmodifiedTextColor);
    }

    @Override
    protected void setStatusText(AppInfo info, TextView status) {
        switch (info.getMeteredStatus()){
            case PreferencesUtil.METERED_FORCE_NOT:
                status.setText(R.string.status_metered_force_not);
                status.setTextColor(mModifiedTextColor);
                break;
            case PreferencesUtil.METERED_FORCE:
                status.setText(R.string.status_metered_force);
                status.setTextColor(mModifiedTextColor);
                break;
            case PreferencesUtil.METERED_AUTO:
                status.setText(R.string.status_metered_auto);
                status.setTextColor(mModifiedTextColor);
                break;
            default:
                status.setText(R.string.status_not_modified);
                status.setTextColor(mUnmodifiedTextColor);
        }
    }
}
