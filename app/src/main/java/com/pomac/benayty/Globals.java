package com.pomac.benayty;

import java.util.Date;

/**
 * Global variables, fields, and methods for the app.
 */
public class Globals {

    /**
     * Base URL
     */
    public static final String BASE_URL = "https://pomac.info/";

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
}
