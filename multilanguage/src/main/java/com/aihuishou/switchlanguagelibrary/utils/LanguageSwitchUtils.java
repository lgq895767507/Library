package com.aihuishou.switchlanguagelibrary.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

import static java.util.Locale.getDefault;

/**
 * @Author Li Gui Yun
 * @date 2018年08月27日16:15
 * @email guiyun.li@aihuishou.com
 * @ClassName:
 **/
public class LanguageSwitchUtils {

    private static final String KEY_APP_LANGUAGE = "KEY_APP_LANGUAGE";
    //App 语言持久化


    //更改App语言
    public static void switchAppLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);

        saveLanguageSetting(context, locale);//保存
    }


    //判断当前语言是否和选择的语言是否一致
    public static boolean isSameWithSetting(Context context, Locale locale) {
        Locale spLocale = getAppLocale(context);
        if (spLocale == null) {
            return false;
        }
        return spLocale.getLanguage().equals(locale.getLanguage());
    }

    //保存语言到Sp当中
    public static void saveLanguageSetting(Context context, Locale locale) {
        SPUtils.put(context, KEY_APP_LANGUAGE, locale.getLanguage());
    }

    //获取sp当中保存的而语言
    public static String getAppLanguage(Context context) {
        return (String) SPUtils.get(context, KEY_APP_LANGUAGE, getDefault().getLanguage());
    }

    //获取语言
    public static Locale getAppLocale(Context context) {
        String lang = (String) SPUtils.get(context, KEY_APP_LANGUAGE, getDefault().getLanguage());
        if (Locale.ENGLISH.getLanguage().equals(lang)) {
            return Locale.ENGLISH;
        } else if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(lang)) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (Locale.US.getLanguage().equals(lang)) {
            return Locale.US;
        } else if (Locale.JAPANESE.getLanguage().equals(lang)) {
            return Locale.JAPANESE;
        } else if (Locale.CHINESE.getLanguage().equals(lang)) {
            return Locale.CHINESE;
        } else if (Locale.CHINA.getLanguage().equals(lang)) {
            return Locale.CHINA;
        } else if (Locale.TRADITIONAL_CHINESE.getLanguage().equals(lang)) {
            return Locale.TRADITIONAL_CHINESE;
        } else if (Locale.CANADA_FRENCH.getLanguage().equals(lang)) {
            return Locale.CANADA_FRENCH;
        } else if ("in".equals(lang)) {//印度尼西亚
            return new Locale("in");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return LocaleList.getDefault().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }
}
