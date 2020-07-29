package cloud.celldata.membrane.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Http请求工具类
 */
public class HttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private HttpHelper() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * 获取请求体中的参数
     * @param request 请求对象
     * @return 请求体中的参数
     * @throws IOException IO异常
     */
    public static String getBodyString(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("字节流出错",e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("字节流出错",e.getMessage());
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("字节流出错",e.getMessage());
                }
            }
        }
        return sb.toString();
    }

}
