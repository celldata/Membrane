package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.*;
import cloud.celldata.membrane.pojo.entity.DataAttributeEntity;
import cloud.celldata.membrane.pojo.entity.DataConfigEntity;
import cloud.celldata.membrane.pojo.entity.DataScreeEntity;
import cloud.celldata.membrane.pojo.enumeration.IsAuthenticationEnum;
import cloud.celldata.membrane.pojo.enumeration.TokenCheckTypeEnum;
import cloud.celldata.membrane.service.AppService;
import cloud.celldata.membrane.utils.ListPageUtil;
import cloud.celldata.membrane.utils.OssUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * 平台管理控制器
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: AppController
 * @Description: 应用类
 * @Author: jiwang
 * @CreateDate: 2020/5/21 11:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 11:09
 */
@RestController
@CrossOrigin
@RequestMapping("/api/app")
public class AppController extends BaseController{

    @Autowired
    private AppService appService;


    /**
     * 查询所有平台
     * @param sign sign=0时需过滤权限平台
     * @return 平台列表
     */
    @ApiOperation(value ="/list", notes = "所有平台集合")
    @GetMapping(value = "/list")
    public ResponseBean list(@RequestParam(required = false, value = "sign") Integer sign,
                             @RequestParam(required = false, value = "authentication") Integer authentication,
                             @RequestParam(required = false, value = "verification") Integer verification,
                             @RequestParam(required = false, value = "tokenCheckType") Integer tokenCheckType,
                             @RequestParam(required = false, value = "clientId") Integer clientId,
                             @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                             @RequestParam(required = false, value = "pageSize") Integer pageSize
    ) {
        try {

            List<ClientListBean> clientListBeans = appService.selectAllClient(sign, authentication,
                    verification, tokenCheckType, clientId);
            PageInfo pageInfo;
            if ( pageIndex== null ||  pageSize== null) {
                pageInfo = ListPageUtil.getPageInfo(clientListBeans, 1, clientListBeans.size());
            } else {
                pageInfo = ListPageUtil.getPageInfo(clientListBeans, pageIndex,pageSize);
            }
            return getResponse(ResponseCode.SUCCESS_PROCESSED, pageInfo);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/add",notes = "新增加应用")
    @PostMapping(value = "/add")
    public ResponseBean add(@RequestBody CertificationBean clientBean){

        Integer userId = getCurrentUserId();
        try {
            appService.addClient(clientBean,userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/fetch",notes = "认证列表下拉框，在线离线列表下拉框")
    @GetMapping(value = "/fetch")
    public ResponseBean fetch(){
        try {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("lineStatus", TokenCheckTypeEnum.tokenCheckTypes);
            map.put("authentication",IsAuthenticationEnum.isAuthenticationMapList);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, map);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/update",notes = "编辑应用")
    @PostMapping(value = "/update")
    public ResponseBean updateApp(@RequestBody ClientBean clientBean){

        Integer userId = getCurrentUserId();
        try {
            ClientBean clientBeanForUpdata = appService.updateApp(clientBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED,clientBeanForUpdata);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/select", notes = "查询应用对应模块和功能")
    @GetMapping(value = "/select")
    public ResponseBean selectModuleAndFunctionByClientID(@RequestParam(required = false, value = "clientId") Integer clientId,
                                                          @RequestParam (required = false, value = "moduleId")Integer moduleId) {

        try {
            return getResponse(ResponseCode.SUCCESS_PROCESSED,
                    appService.selectModuleAndFunctionByClientID(clientId,moduleId));
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/module/add", notes = "新增模块，功能")
    @PostMapping(value = "/module/add")
    public ResponseBean addModuleAndFunction(@RequestBody ModuleBean moduleBean) {

        try {
            Integer userId = getCurrentUserId();
            appService.addModuleAndFunction(moduleBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/module/remove", notes = "删除模块")
    @PostMapping(value = "/module/remove")
    public ResponseBean removeModule(@RequestBody RemoveMoudleAndFuctionBean removeMoudleAndFuctionBean) {

        try {
            Integer userId = getCurrentUserId();
            appService.removeModule(removeMoudleAndFuctionBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }

    }


    @ApiOperation(value = "/module/function/remove", notes = "删除功能")
    @PostMapping(value = "/module/function/remove")
    public ResponseBean removeFunction(@RequestBody RemoveMoudleAndFuctionBean removeMoudleAndFuctionBean) {
        try {
            Integer userId = getCurrentUserId();
            appService.removeFunction(removeMoudleAndFuctionBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }

    }

    @ApiOperation(value = "/module/update", notes = "编辑模块")
    @PostMapping(value = "/module/update")
    public ResponseBean updateModule(@RequestBody ModuleBean moduleBean) {

        try {

            Integer userId = getCurrentUserId();
            appService.updateModule(moduleBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }

    }

    @ApiOperation(value = "/module/function/update", notes = "编辑功能")
    @PostMapping(value = "/module/function/update")
    public ResponseBean updateFunction(@RequestBody FunctionBean functionBean) {
        try {
            Integer userId = getCurrentUserId();
            appService.updateFunction(functionBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/remove", notes = "删除应用")
    @GetMapping(value = "/remove")
    public ResponseBean removeApp(@RequestParam Integer clientId) {
        try {

            Integer userId = getCurrentUserId();
            appService.removeApp(clientId, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 根据平台id查询所有权限
     *
     * @param clientId 平台ID
     * @param flag     标记 0代表功能权限 1代表数据权限
     * @return
     */
    @ApiOperation(value = "/claim", notes = "根据平台id查询权限")
    @GetMapping(value = "/claim")
    public ResponseBean selectFunctionByClientId(@RequestParam("clientId") Integer clientId,
                                                 @RequestParam("flag") Integer flag) {
        try {
            List list = appService.selectFunctionAuthorityByClientId(clientId, flag);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, list);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/certification", notes = "认证管理")
    @PostMapping(value = "/certification")
    public ResponseBean certificationApp(@RequestBody CertificationBean certificationBean) {

        try {
            Integer userId = getCurrentUserId();
            appService.certificationApp(certificationBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/oss", notes = "获取oss相关参数")
    @GetMapping(value = "/oss")
    public ResponseBean oss(){
        try {
            return getResponse(ResponseCode.SUCCESS_PROCESSED,OssUtil.getSTS());
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/config/add", notes = "增加数据权限基础配置")
    @PostMapping (value = "/config/add")
    public ResponseBean addConfig(@RequestBody DataConfigEntity dataConfigEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.addConfig(dataConfigEntity,userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/config/update", notes = "修改数据权限基础配置")
    @PostMapping (value = "/config/update")
    public ResponseBean updateConfig(@RequestBody DataConfigEntity dataConfigEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.updateConfig(dataConfigEntity,userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/config/list", notes = "数据权限基础配置列表")
    @GetMapping  (value = "/config/list")
    public ResponseBean list(@RequestParam(required = false, value = "clientId") Integer clientId,
                             @RequestParam(required = false, value = "type") Integer type,
                             @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                             @RequestParam(required = false, value = "pageSize") Integer pageSize
                             ){
        try {
            PageInfo pageInfo = appService.selectDateConfigEntity(clientId,type,pageIndex,pageSize);
            return getResponse(ResponseCode.SUCCESS_PROCESSED,pageInfo);
        } catch (MembraneException e) {
            e.printStackTrace();
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/config/remove", notes = "删除数据权限基础配置列表")
    @GetMapping (value = "/config/remove")
    public ResponseBean removeConfig(@RequestParam(name = "idList") List<Integer> idList){
        try {
            Integer userId = getCurrentUserId();
            appService.removeConfig(idList,userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/attribute/add", notes = "增加数据权限属性配置")
    @PostMapping (value = "/attribute/add")
    public ResponseBean addAttribute(@RequestBody DataAttributeEntity dataAttributeEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.addAttribute(dataAttributeEntity, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/attribute/update", notes = "修改数据权限属性配置")
    @PostMapping (value = "/attribute/update")
    public ResponseBean updateAttribute(@RequestBody DataAttributeEntity dataAttributeEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.updateAttribute(dataAttributeEntity, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/attribute/list", notes = "数据权限属性配置列表")
    @GetMapping (value = "/attribute/list")
    public ResponseBean selectAttributesByDataConfigId(@RequestParam(value = "configId") Integer configId,
                                                       @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                                                       @RequestParam(required = false, value = "pageSize") Integer pageSize){
        try {
            List<DataAttributeEntity> dataAttributeEntities = appService.selectAttributesByDataConfigId(configId,
                    pageIndex, pageSize);
            Boolean hasPageInfo = null != pageIndex && null != pageSize;
            return getResponse(ResponseCode.SUCCESS_PROCESSED, hasPageInfo ? new PageInfo<>(dataAttributeEntities) : dataAttributeEntities);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/attribute/remove", notes = "删除数据权限属性配置列表")
    @GetMapping (value = "/attribute/remove")
    public ResponseBean removeAttribute(@RequestParam(name = "idList") List<Integer> idList){
        try {
            Integer userId = getCurrentUserId();
            appService.removeAttribute(idList, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/scree/add", notes = "增加条件配置")
    @PostMapping (value = "/scree/add")
    public ResponseBean addScree(@RequestBody DataScreeEntity dataScreeEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.addScree(dataScreeEntity, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/scree/update", notes = "修改条件配置")
    @PostMapping (value = "/scree/update")
    public ResponseBean updateScree(@RequestBody DataScreeEntity dataScreeEntity){
        try {
            Integer userId = getCurrentUserId();
            appService.updateScree(dataScreeEntity, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/scree/list", notes = "条件配置列表")
    @GetMapping (value = "/scree/list")
    public ResponseBean selectScreesByClientId(@RequestParam(value = "clientId") Integer clientId,
                                               @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                                               @RequestParam(required = false, value = "pageSize") Integer pageSize){
        try {
            List<DataScreeEntity> dataRuleEntities = appService.selectScreesByClientId(clientId, pageIndex, pageSize);
            Boolean hasPageInfo = null != pageIndex && null != pageSize;
            return getResponse(ResponseCode.SUCCESS_PROCESSED, hasPageInfo ? new PageInfo<>(dataRuleEntities) : dataRuleEntities);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/scree/remove", notes = "删除条件配置")
    @GetMapping (value = "/scree/remove")
    public ResponseBean removeScree(@RequestParam(name = "idList") List<Integer> idList){
        try {
            Integer userId = getCurrentUserId();
            appService.removeScree(idList, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

}
