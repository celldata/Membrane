package cloud.celldata.membrane.utils;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Sha256工具类
 *
 * @ProjectName: Nerium
 * @Package: com.beyondsoft.nerium.utils
 * @ClassName: Sha256
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2019/12/19 11:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/19 11:24
 */
public class Sha256 {
    private static Logger logger = LoggerFactory.getLogger(Sha256.class);

    private Sha256() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 利用java原生的类实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
 public static String getSHA256(String str){
        MessageDigest messageDigest;
         String encodestr = "";
         try {
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes(Charsets.UTF_8));
        encodestr = byte2Hex(messageDigest.digest());
         } catch (NoSuchAlgorithmException e) {
        logger.error("SHA256加密数据-{}出错",str);
         }
     return encodestr;
        }
        /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
            //1得到一位的进行补0操作
            stringBuffer.append("0");
        }
        stringBuffer.append(temp);
        }
        return stringBuffer.toString();
}


}
