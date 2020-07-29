package cloud.celldata.membrane.utils;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.bo.PasswordBO;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里短信工具类
 *
 **/
@Component
public class AliMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(AliMessageUtil.class);

    // 短信发送成功状态码
    private static final String OK = "OK";

    // 地域ID(API支持的RegionID，如短信API的值为：cn-hangzhou)
    private static String regionId;

    // 短信签名名称，请在控制台签名管理页面签名名称一列查看
    private static String signName;

    // 用户标识(访问密钥 ID，AccessKey 用于调用 API)
    private static String accessKeyId;

    // 用户验证密钥
    private static String accessKeySecret;

    // 阿里服务器域名
    private static String domain;

    public static void sendMessage(String templateCode, String phoneNumber, String txtMessage) throws ClientException {
        // 封装签名信息
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        // 创建服务客户端
        IAcsClient client = new DefaultAcsClient(profile);
        // 创建短信接口请求参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        // API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。
        request.setVersion("2017-05-25");
        // API 的名称
        request.setAction("SendSms");
        // 手机号
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        // 短信模板编号
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板ID，请在控制台模板管理页面模板CODE一列查看
        request.putQueryParameter("TemplateParam", JSON.toJSONString(PasswordBO.PasswordBOBuilder.aPasswordBO()
                .withPassword(txtMessage).build()));
        try {
            // 发送短信
            CommonResponse response = client.getCommonResponse(request);
            // 转换响应结果格式
            Map resultMap = JSON.parseObject(response.getData(), HashMap.class);
            if (resultMap != null) {
                // 获取请求结果状态编码
                String code = String.valueOf(resultMap.get("Code"));
                // 短信发送失败，抛出提示信息
                if (!OK.equals(code)) {
                    throw new MembraneException(ResponseCode.SMS_SEND_ERROR, code);
                }
            }
        } catch (ServerException e) { // 服务端异常
            logger.error("服务端短信异常");
            throw e;
        } catch (ClientException e) { // 客户端异常
            logger.error("客户端短信异常");
            throw e;
        }
    }

    @Value("${ali_msg.region_id}")
    public void setRegionId(String regionId) {
        AliMessageUtil.regionId = regionId;
    }

    @Value("${ali_msg.sign_name}")
    public void setSignName(String signName) {
        AliMessageUtil.signName = signName;
    }

    @Value("${ali_msg.access_key.id}")
    public void setAccessKeyId(String accessKeyId) {
        AliMessageUtil.accessKeyId = accessKeyId;
    }

    @Value("${ali_msg.access_key.secret}")
    public void setAccessKeySecret(String accessKeySecret) {
        AliMessageUtil.accessKeySecret = accessKeySecret;
    }

    @Value("${ali_msg.domain}")
    public void setDomain(String domain) {
        AliMessageUtil.domain = domain;
    }
}
