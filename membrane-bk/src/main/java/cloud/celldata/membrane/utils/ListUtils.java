package cloud.celldata.membrane.utils;

import java.util.Collections;
import java.util.List;

/**
 * List工具类
 *
 */
public class ListUtils {

    private ListUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 校验两个集合是否一致
     * @param listA 集合
     * @param listB 集合
     * @param <T> 集合中存放数据的数据类型
     * @return 校验结果
     */
    public synchronized static<T extends Comparable<T>> boolean compare(List<T> listA, List<T> listB) {
        if(null!=listA && null==listB){
            return false;
        }
        if(null==listA && null!=listB){
            return false;
        }
        if(listA.size() != listB.size())
            return false;
        Collections.sort(listA);
        Collections.sort(listB);
        for(int i=0;i<listA.size();i++){
            if(!listA.get(i).equals(listB.get(i)))
                return false;
        }
        return true;
    }

}
