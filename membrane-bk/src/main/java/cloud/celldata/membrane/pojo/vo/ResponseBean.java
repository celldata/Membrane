package cloud.celldata.membrane.pojo.vo;

/**
 * 响应类
 * @param <T>
 */
public class ResponseBean<T> {

    // 响应code
    private int err_CODE = 0;

    // 响应消息
    private String err_MESSAGE = "";

    // 响应数据
    private T data = null;

    public int getErr_CODE() {
        return err_CODE;
    }

    public void setErr_CODE(int err_CODE) {
        this.err_CODE = err_CODE;
    }

    public String getErr_MESSAGE() {
        return err_MESSAGE;
    }

    public void setErr_MESSAGE(String err_MESSAGE) {
        this.err_MESSAGE = err_MESSAGE;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseBean(int err_CODE, String err_MESSAGE, T data) {
        this.err_CODE = err_CODE;
        this.err_MESSAGE = err_MESSAGE;
        this.data = data;
    }
}
