package cloud.celldata.membrane.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页工具类
 *
 **/
public class ListPageUtil {

    private ListPageUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取分页信息
     * @param list 待分页列表
     * @param pageNum 页号
     * @param pageSize 页大小
     * @param <T> 列表中数据类型
     * @return 分页信息
     */
    public static <T> PageInfo getPageInfo(List<T> list, Integer pageNum, Integer pageSize) {
        // 截取分页数据
        Integer start = (pageNum-1) * pageSize;
        Integer end = start + pageSize;
        List<T> subUserInfoList = list.subList(start, end > list.size() ? list.size() : end);
        // 重置分页对象
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(subUserInfoList.size());
        pageInfo.setStartRow(start+1);
        pageInfo.setEndRow(end);
        int total = list.size();
        pageInfo.setTotal(total);
        int pages = (int)Math.ceil((double)list.size()/(double)pageSize);
        pageInfo.setPages(pages);
        pageInfo.setList(subUserInfoList);
        pageInfo.setFirstPage(1);
        boolean isFirstPage = pageNum==1;
        pageInfo.setIsFirstPage(isFirstPage);
        pageInfo.setPrePage(isFirstPage ? 0 : pageNum-1);
        boolean isLastPage = pageNum==pages;
        pageInfo.setIsLastPage(isLastPage);
        pageInfo.setNextPage(isLastPage ? 0 : pageNum+1);
        pageInfo.setLastPage(pages);
        pageInfo.setHasNextPage(!isLastPage);
        pageInfo.setHasPreviousPage(!isFirstPage);
        pageInfo.setNavigatePages(8);
        int[] pageNums = {1,2,3,4,5,6,7,8};
        int[] tagetNums = new int[pages];
        if (pages < 8) {
            System.arraycopy(pageNums, 0, tagetNums, 0, pages);
        }
        pageInfo.setNavigatepageNums(pages>=8 ? pageNums : tagetNums);
        return pageInfo;
    }

}
