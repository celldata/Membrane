package cloud.celldata.membrane.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 从普通POJO类中提取关键信息
 *
 * @ProjectName: demo_mqtt
 * @Package: com.example.demo_mqtt.util
 * @ClassName: AttributesExtractUtils
 * @Description: 从普通POJO类中提取关键信息
 * @Author: jiwang
 * @CreateDate: 2020/6/2 17:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/2 17:26
 */
public class AttributesExtractUtils {
    /**
     * 从集合中提取信息
     *
     * 适用于：list<对象>
     *
     * @param targets
     * @param targetFields
     * @return
     */
    public static List<Map<String, Object>> extractForCollection(Collection<?> targets, String... targetFields) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object target : targets) {
            result.add(extract(target, targetFields));
        }
        return result;
    }

    /**
     * 从普通POJO类中提取关键信息, 要求遵循 getMethod 规范
     *
     * @param target       目标JAVA POJO类,要求装有数据
     * @param targetFields 类的属性名字: 希望从 POJO 中提取哪些字段的数据
     * @return 数据会被装载于MAP中
     */
    public static Map<String, Object> extract(Object target, String... targetFields) {
        if ((null == target) || null == targetFields || 0 == targetFields.length) {
            return new HashMap<>();
        }
        Map<String, Object> objectMap = new HashMap<>();
        for (String targetField : targetFields) {
            Class<?> targetClass = target.getClass();
            try {
                Field field = targetClass.getDeclaredField(targetField);
                String methodNamePrefix ="get";
                String methodName = methodNamePrefix + String.valueOf(targetField.charAt(0)).toUpperCase()
                        + targetField.substring(1);
                Method targetMethod = targetClass.getMethod(methodName);
                Object invoke = targetMethod.invoke(target);
                objectMap.put(targetField, invoke);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return objectMap;
    }

    public static Map<String, Object> extractForStringList(Object target,List<String> list){
        if ((null == target) || null == list || 0 == list.size()) {
            return new HashMap<>();
        }

        Map<String, Object> objectMap = new HashMap<>();
        for (String targetField : list) {
            Class<?> targetClass = target.getClass();
            try {
                Field field = targetClass.getDeclaredField(targetField);
                String methodNamePrefix ="get";
                String methodName = methodNamePrefix + String.valueOf(targetField.charAt(0)).toUpperCase()
                        + targetField.substring(1);
                Method targetMethod = targetClass.getMethod(methodName);
                Object invoke = targetMethod.invoke(target);
                objectMap.put(targetField, invoke);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return objectMap;
    }


    private static String getNameOfElegant(String old) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : old.toCharArray()) {
            if (Character.isUpperCase(c) && stringBuilder.length() > 0) {
                stringBuilder.append("_");
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString().toLowerCase();
    }
}
