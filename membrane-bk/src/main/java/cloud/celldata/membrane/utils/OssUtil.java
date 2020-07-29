package cloud.celldata.membrane.utils;


import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云文件上传工具类
 * jiwang
 */
public class OssUtil {

    private static String endpoint = "membrane.oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI4G1tJMdDpgsjogr6BQ9K";
    private static String accessKeySecret = "v49Qag6G1FQoIbmHk8PQxQ9krD2ifS";
    private static String bucketName = "membrane";

    private static String endpointSTS = "sts.cn-beijing.aliyuncs.com";
    private static String roleArn = "acs:ram::1680091363828537:role/ramoss";



    /**
     * 文件上传
     * @param file 文件
     * @param fileName 文件名
     * @return 文件路径
     */
    public static String getOSSUrl(File file,String fileName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	    ossClient.putObject(bucketName, fileName, file);
        // 关闭client
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        ossClient.shutdown();
        return url;
    }

    /**
     * 前端文件上传STS
     * @return STS信息
     */
    public static Map<String,String> getSTS() throws ClientException {
        Map<String,String> resultMap = new HashMap<>(0);

            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", endpointSTS);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(bucketName);
            request.setPolicy(null); // 若policy为空，则用户将获得该角色下所有权限
            request.setDurationSeconds(3*5*60L); // 设置凭证有效时间
            final AssumeRoleResponse response = client.getAcsResponse(request);

            resultMap.put("Expiration", response.getCredentials().getExpiration());
            resultMap.put("Access Key Id" , response.getCredentials().getAccessKeyId());
            resultMap.put("Access Key Secret" , response.getCredentials().getAccessKeySecret());
            resultMap.put("Security Token" , response.getCredentials().getSecurityToken());
            resultMap.put("RequestId" , response.getRequestId());
            resultMap.put("bucketName" ,bucketName);

        return resultMap;
    }

}
