package cloud.celldata.membrane.utils;


import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.entity.MembraneTokenEntity;
import cloud.celldata.membrane.pojo.ResponseCode;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */

public class JWTUtils {

    private JWTUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    /**
     * map 通过 base64Security 加密成token
     * @param tokenInfo token payload内容
     * @param base64Security 密钥
     * @return 加密结果
     */
    public static String createJWT(MembraneTokenEntity tokenInfo, String base64Security) {
        if(null==base64Security || "".equals(base64Security) || tokenInfo.getUserId() == null){
            throw new MembraneException(ResponseCode.PARAMETER_INVALID);
        }

        Map<String, Object> payload = new HashMap<>();
        if(tokenInfo.getApplicationId() != null){
            payload.put("application_id",tokenInfo.getApplicationId());
        }
        if(tokenInfo.getApplication_name() != null){
            payload.put("application_name",tokenInfo.getApplication_name());
        }
        payload.put("user_id",tokenInfo.getUserId());
        payload.put("user_name",tokenInfo.getUser_name());
        payload.put("issue_time",tokenInfo.getIssue_time());
        payload.put("functions",tokenInfo.getFunctions());
        payload.put("scope",tokenInfo.getScopes());
        payload.put("issuer",tokenInfo.getIssuer());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("iss",tokenInfo.getIssuer())
                .setPayload(new Gson().toJson(payload))
                .signWith(signatureAlgorithm,base64Security.getBytes());
        // 生成JWT
        return builder.compact();
    }

    /**
     * 解密
     * @param jsonWebToken token
     * @param base64Security 生成token的密钥
     * @return 解密结果
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        if(null==base64Security || "".equals(base64Security)){
            throw new MembraneException(ResponseCode.PARAMETER_INVALID);
        }
        logger.info("进行解密========================================================");
        logger.info("utils中token:"+jsonWebToken+"=================================================");
        logger.info("utils中base64Security:"+base64Security+"=================================================");
        try {
            return Jwts.parser()
                    .setSigningKey(base64Security.getBytes())
                    .parseClaimsJws(jsonWebToken)
                    .getBody();
        } catch (Exception e) {
            throw new MembraneException(ResponseCode.JWT_DECODE_FAILURE);
        }
    }



}
