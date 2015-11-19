package com.ghostflying.forcemeteredap.util;

/**
 * Created by ghostflying on 15-11-19.
 */
public abstract class PreferencesUtil {
    public final static String PREFERENCES_NAME = "Settings";

    public final static String METERED_PREFERENCES_POSTFIX = "/Metered";

    public final static int DEFAULT_METERED= 1984;// not modified

    public final static long[] VIBRATE_SILENT_PATTERN = new long[]{0, 0};

    public final static int METERED_FORCE_NOT = 0;
    public final static int METERED_FORCE = 1;
    public final static int METERED_AUTO = 2;
}