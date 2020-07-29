package cloud.celldata.membrane.filter;

import com.google.common.base.Charsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Http请求包装类
 */

public class RequestReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpHelper.getBodyString(request).getBytes(Charsets.UTF_8);
    }

    /**
     * 获取读取流
     * @return 读取流
     * @throws IOException IO异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 获取写入流
     * @return 写入流
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

}
