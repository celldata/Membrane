package cloud.celldata.membrane.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实体类转换
 *
 * @ProjectName: demo_mqtt
 * @Package: com.example.demo_mqtt.util
 * @ClassName: BeanMapper
 * @Description: 实体类转换
 * @Author: jiwang
 * @CreateDate: 2020/6/2 17:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/2 17:04
 */
public class BeanMapper {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 普通对象转换 比如: ADO -> AVO
     * @param: [source 源对象, destinationClass 目标对象class]
     * @return: T
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, destinationClass);
    }

    /**
     * List转换 比如: List<ADO> -> List<AVO>
     * @param: [sourceList 源对象List, destinationClass 目标对象class]
     * @return: java.util.List<T>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList();
        if (sourceList == null) {
            return destinationList;
        }
        for (Object sourceObject : sourceList) {
            if (sourceObject != null) {
                T destinationObject = dozer.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }
}
