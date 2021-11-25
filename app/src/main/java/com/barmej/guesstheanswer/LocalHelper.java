package com.barmej.guesstheanswer;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocalHelper {
//الدالة setLocale تقوم باستقبال Context و نص يمثل اللغة المراد تغيير التطبيق إليها.
// بعد ذلك تقوم باستدعاء الدالة updateResourcesLegacy
    public static Context setLocale(Context context, String language) {
        return updateResourcesLegacy(context, language);
    }

    //والدالة الأخرى updateResourcesLegacy تقوم بتغيير اللغة من داخل الـ Context المرسل
    // عن طريق الوصول إلى إعدادات الموارد من resources.getConfiguration
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }
}
