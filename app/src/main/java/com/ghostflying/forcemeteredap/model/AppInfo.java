package com.ghostflying.forcemeteredap.model;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.ghostflying.forcemeteredap.util.PreferencesUtil;

/**
 * Created by ghostflying on 2/17/15.
 */
public class AppInfo {
    private final static String DEFAULT_VERSION_NAME = "Version not found";

    private String mAppName;
    private String mPackageName;
    private Drawable mIcon;
    private SharedPreferences mPreferences;


    public AppInfo(ApplicationInfo info, PackageManager pm, SharedPreferences preferences){
        mAppName = info.loadLabel(pm).toString();
        mPackageName = info.packageName;
        mIcon = info.loadIcon(pm);
        mPreferences = preferences;
    }

    public String getAppName(){
        return mAppName;
    }

    public Drawable getIcon(){
        return mIcon;
    }

    public int getMeteredStatus(){
        return mPreferences.getInt(mPackageName + PreferencesUtil.METERED_PREFERENCES_POSTFIX, PreferencesUtil.DEFAULT_METERED);
    }

    public void setMetered(int metered) {
        if (metered == PreferencesUtil.DEFAULT_METERED) {
            mPreferences.edit().remove(mPackageName + PreferencesUtil.METERED_PREFERENCES_POSTFIX).apply();
        } else {
            mPreferences.edit().putInt(mPackageName + PreferencesUtil.METERED_PREFERENCES_POSTFIX, metered).apply();
        }
    }

    public boolean isMeteredRestricted(){
        return mPreferences.contains(mPackageName + PreferencesUtil.METERED_PREFERENCES_POSTFIX);
    }
}
