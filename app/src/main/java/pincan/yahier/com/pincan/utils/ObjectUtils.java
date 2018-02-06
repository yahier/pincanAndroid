package pincan.yahier.com.pincan.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yahier on 2018/2/6.
 */

public class ObjectUtils {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Map<String, String> convertToMap(Object object) {
        Map<String, String> hashMap = new HashMap<>();
        Class mClass = object.getClass();
        Field[] fields = mClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();
            try {
                String value = (String) field.get(object);
                //如果没有直接赋值，则通过getXy方法来获取
                if (value == null || value.equals("")) {
                    Method method = mClass.getMethod("get" + StringUtils.upperCaseFirstCharacter(key));
                    value = (String) method.invoke(object);
                }
                hashMap.put(key, value);

            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
        return hashMap;

    }
}
