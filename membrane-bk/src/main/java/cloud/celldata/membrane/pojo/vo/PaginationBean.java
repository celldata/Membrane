package cloud.celldata.membrane.pojo.vo;

/**
 * 分页信息实体
 */
public class PaginationBean {

    // 页号
    private Integer pageIndex;

    // 页大小
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
