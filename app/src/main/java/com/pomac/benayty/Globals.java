package com.pomac.benayty;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Global variables, fields, and methods for the app.
 */
public class Globals {

    public static CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * For storing user token
     */
    public static String token = "1583861467mUAX0TtHPz1583861467";

    /**
     * FCM Base URL
     */
    public static final String FCM_BASE_URL = "https://fcm.googleapis.com/";

    /**
     * For storing user phone
     */
    public static String phone = "01114591647";

    /**
     * Base URL
     */
    public static final String BASE_URL = "https://pomac.info/";
    public static final String FCM_AUTHORIZATION = "key=AAAAWsdOdxM:APA91bHsMA1G0n7w9LzzLwTSoDeEBy" +
            "J8yIP2cx9BmTOEm0qj6xcx-t5dWA8F8wo6zHCwSyV7z7Apt_lCRxkkkB3bNmgVAVuEBjKqeckombtgpzpwyBR" +
            "hjU9HB53Gzf3wMQp2nT43Rg6f";

    /**
     * Log tag
     */
    public static final String TAG = "nussair";

    /**
     * Field for main category id.
     */
    public static final String MAIN_CATEGORY_ID = "main_category_id";

    /**
     * Field for main category name.
     */
    public static final String MAIN_CATEGORY_NAME = "main_category_name";

    /**
     * Field for secondary category id.
     */
    public static final String SECONDARY_CATEGORY_ID = "secondary_category_id";

    /**
     * Field for area id.
     */
    public static final String AREA_ID = "area_id";

    /**
     * field for city id
     */
    public static final String CITY_ID = "city_id";

    /**
     * Gets the creation time text
     */
    public static String getCreationTimeText(Date date) {
        String result = "";

        Date now = new Date();

        long duration = now.getTime() - date.getTime();

        duration /= 1000;

        result = duration > 1? duration + " ثواني": duration + " ثانية";

        if(duration > 60) {
            duration /= 60;
            result = duration > 1?duration + " دقائق":duration + " دقيقة";

            if(duration > 60) {
                duration /= 60;
                result = duration > 1? duration + " ساعات":duration + " ساعة";

                if(duration > 24) {
                    duration /= 24;
                    result = duration > 1?duration + " أيام":duration + " يوم";

                    if (duration > 7) {
                        duration /= 7;
                        result = duration > 1?duration + " أسابيع":duration + " أسبوع";

                        if(duration > 4) {
                            duration /= 4;
                            result = duration > 1?duration + " شهور":duration + " شهر";

                            if(duration > 12) {
                                duration /= 12;
                                result = duration > 1?duration + " سنين":duration + " سنة";
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Field for reset code
     */
    public static final String RESET_CODE = "reset_code";

    /**
     * Field for advertisement id
     */
    public static final String AD_ID = "ad_id";

    /**
     * Field for the name of the user to be chatted
     */
    public static final String USER_NAME = "user_name";

    public static final String FROM = "from";

    public static final String TO = "to";

    // For shared preferences
    public static final String SHARED_PREFERENCES = "benayty";
    public static final String USER_TOKEN = "token";
    public static final String USER_PHONE = "phone";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String USER_IMAGE_PATH = "image_path";
    /**
     * For storing user token
     */
    public static String fcmToken = "";
}
