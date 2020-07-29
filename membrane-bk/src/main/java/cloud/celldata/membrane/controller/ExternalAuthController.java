package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.pojo.entity.ExternalAuthEntity;
import cloud.celldata.membrane.pojo.vo.external.ExternalAuthInfo;
import cloud.celldata.membrane.service.ExternalAuthService;
import cloud.celldata.membrane.utils.ListPageUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 外部认证管理控制器
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: externalAuthController
 * @Description: 外部认证相关操作类
 * @Author: jiwang
 * @CreateDate: 2020/6/22 13:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/22 13:58
 */

@RestController
@CrossOrigin
@RequestMapping("/api/externalAuth")
public class ExternalAuthController extends BaseController{

    @Autowired
    ExternalAuthService externalAuthService;

    /**
     * 外部认证接入
     * @param externalAuthInfo 请求参数
     * @return 请求响应
     */
    @ApiOperation(value = "/add", notes = "外部认证接入")
    @PostMapping(value = "/add")
    public ResponseBean addExternalAuth(@RequestBody ExternalAuthInfo externalAuthInfo){
        try {
            ExternalAuthEntity externalAuth = externalAuthService.addExternalAuth(externalAuthInfo,getCurrentUserId());
            return getResponse(ResponseCode.SUCCESS_PROCESSED,externalAuth);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 外部认证源列表查询 多条件查询
     * @param name 认证名称
     * @param type 类型
     * @param id  外部认证Id
     * @param pageIndex 当前页
     * @param pageSize 页大小
     * @return 列表
     */
    @ApiOperation(value = "/list", notes = "外部认证源列表查询 多条件查询")
    @GetMapping(value = "/list")
    public ResponseBean list(@RequestParam(required = false, value = "name") String  name,
                             @RequestParam(required = false, value = "type") Integer type,
                             @RequestParam(required = false, value = "id") Integer id,
                             @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                             @RequestParam(required = false, value = "pageSize") Integer pageSize){
        try {

            List<ExternalAuthInfo> externalAuthInfoList = externalAuthService.selectAllExternalAuth(name, type,
                    id);
            PageInfo pageInfo;
            if ( pageIndex== null ||  pageSize== null) {
                pageInfo = ListPageUtil.getPageInfo(externalAuthInfoList, 1, externalAuthInfoList.size());
            } else {
                pageInfo = ListPageUtil.getPageInfo(externalAuthInfoList, pageIndex,pageSize);
            }
            return getResponse(ResponseCode.SUCCESS_PROCESSED,pageInfo);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 外部认证修改
     * @param externalAuthInfo 请求参数
     * @return 请求响应
     */
    @ApiOperation(value = "/update", notes = "外部认证修改")
    @PostMapping(value = "/update")
    public ResponseBean updateExternalAuth(@RequestBody ExternalAuthInfo externalAuthInfo){
        try {
            ExternalAuthEntity externalAuthEntity = externalAuthService.updateExternalAuth(externalAuthInfo, getCurrentUserId());
            return getResponse(ResponseCode.SUCCESS_PROCESSED,externalAuthEntity);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/remove", notes = "删除外部认证 可以批量删除 批量删除传递数组")
    @GetMapping(value = "/remove")
    public ResponseBean removeExternalAuth(@RequestParam(name = "idList") List<Integer> idList){

        try {
            externalAuthService.removeExternalAuth(idList,getCurrentUserId());
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }

    }

}
