package com.ghostflying.forcemeteredap;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.net.ConnectivityManager;

import com.ghostflying.forcemeteredap.util.PreferencesUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by ghostflying on 15-11-19.
 */
public class XposedMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        final XSharedPreferences pre = new XSharedPreferences(BuildConfig.APPLICATION_ID, PreferencesUtil.PREFERENCES_NAME);

        final String meteredPreName = loadPackageParam.packageName + PreferencesUtil.METERED_PREFERENCES_POSTFIX;

        if (pre.contains(meteredPreName)){
            //XposedBridge.log("Hook " + loadPackageParam.packageName);

            try{
                findAndHookMethod("android.net.NetworkInfo", loadPackageParam.classLoader, "getType", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        int type;

                        final int setMetered = pre.getInt(meteredPreName, PreferencesUtil.DEFAULT_METERED);

                        final ConnectivityManager connectivityManager = (ConnectivityManager)AndroidAppHelper.currentApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

                        switch (setMetered){
                            case PreferencesUtil.METERED_FORCE_NOT:
                                type = ConnectivityManager.TYPE_WIFI;
                                //XposedBridge.log("Force not");
                                break;
                            case PreferencesUtil.METERED_FORCE:
                                type = ConnectivityManager.TYPE_MOBILE;
                                //XposedBridge.log("Force");
                                break;
                            case PreferencesUtil.METERED_AUTO:
                                if (connectivityManager.isActiveNetworkMetered())
                                    type = ConnectivityManager.TYPE_MOBILE;
                                else
                                    type = ConnectivityManager.TYPE_WIFI;
                                //XposedBridge.log("Auto");
                                break;
                            default:
                                //XposedBridge.log("Default");
                                type = ConnectivityManager.TYPE_MOBILE;
                        }

                        return type;
                    }
                });

                findAndHookMethod("android.net.NetworkInfo", loadPackageParam.classLoader, "getTypeName", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("Hook stupid method " + loadPackageParam.packageName);
                    }
                });

                findAndHookMethod("android.net.ConnectivityManager", loadPackageParam.classLoader, "getNetworkInfo", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        int type = (int)param.args[0];
                        if (type == ConnectivityManager.TYPE_WIFI){
                            final int setMetered = pre.getInt(meteredPreName, PreferencesUtil.DEFAULT_METERED);
                            switch (setMetered){
                                case PreferencesUtil.METERED_FORCE_NOT:
                                    break;
                                case PreferencesUtil.METERED_FORCE:
                                    param.setResult(null);
                                    break;
                                case PreferencesUtil.METERED_AUTO:
                                    final ConnectivityManager connectivityManager = (ConnectivityManager)AndroidAppHelper.currentApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                                    if (connectivityManager.isActiveNetworkMetered())
                                        param.setResult(null);
                                    break;
                            }
                        }
                    }
                });
            }
            catch (Throwable t){
                XposedBridge.log(t);
            }
        }
    }
}
