package com.ghostflying.forcemeteredap.loader;

import android.content.Context;

import com.ghostflying.forcemeteredap.model.AppInfo;

/**
 * Created by ghostflying on 15-11-19.
 */
public class MeteredLoader extends BaseAppInfoLoader {
    public MeteredLoader(Context context){
        super(context);
    }

    @Override
    protected boolean isRestricted(AppInfo info) {
        return info.isMeteredRestricted();
    }
}
