package pincan.yahier.com.pincan.utils;

import android.text.TextUtils;

/**
 * Created by yahier on 2018/2/6.
 */

public class StringUtils {

    public static String upperCaseFirstCharacter(String str) {
        if (TextUtils.isEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
