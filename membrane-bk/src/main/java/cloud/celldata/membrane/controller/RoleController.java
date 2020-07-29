package cloud.celldata.membrane.controller;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.ResponseBean;
import cloud.celldata.membrane.pojo.vo.RoleIdAndNameBean;
import cloud.celldata.membrane.pojo.vo.RoleListBean;
import cloud.celldata.membrane.pojo.vo.role.RoleAddBean;
import cloud.celldata.membrane.pojo.vo.role.RoleAuthorityBasicBean;
import cloud.celldata.membrane.pojo.vo.role.RoleUpdateBean;
import cloud.celldata.membrane.service.RoleAuthorityService;
import cloud.celldata.membrane.service.RoleService;
import cloud.celldata.membrane.utils.ListPageUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 角色管理控制器
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.controller
 * @ClassName: RoleController
 * @Description: 角色控制
 * @Author: jiwang
 * @CreateDate: 2020/5/15 14:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 14:54
 */
@RestController
@CrossOrigin
@RequestMapping("/api/role")
public  class RoleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;



    @Autowired
    private RoleAuthorityService authorityService;


    /**查询角色分页信息
     * @param clientId 应用ID
     * @param pageIndex 当前页
     * @param pageSize 当前页最多条数
     * @return
     */
    @ApiOperation(value = "/list", notes = "查询角色分页")
    @GetMapping(value = "/list")
    public ResponseBean selectAllRole(@RequestParam(required = false, value = "clientId") Integer clientId,
                                      @RequestParam(required = false, value = "pageIndex") Integer pageIndex,
                                      @RequestParam(required = false, value = "pageSize") Integer pageSize){
        try {
            List<RoleListBean> roleListBeans = roleService.selectAllRole(clientId);
            PageInfo pageInfo = ListPageUtil.getPageInfo(roleListBeans, pageIndex,pageSize);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, pageInfo);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 根据平台ID 查询角色列表
     *
     * @param clientId 应用ID
     * @return 请求响应
     */
    @ApiOperation(value = "/select", notes = "根据平台ID 查询角色列表")
    @GetMapping(value = "/select")
    public ResponseBean selectAllRoleForClientId(@RequestParam("clientId") Integer clientId) {
        try {
            List<RoleIdAndNameBean> roleListBeans = roleService.selectAllRoleForClientId(clientId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, roleListBeans);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }


    /**
     * 新增角色
     * @param roleAddBean 请求参数
     * @return 请求响应
     */
    @ApiOperation(value = "/add", notes = "新增角色")
    @PostMapping(value = "/add")
    public ResponseBean addRole(@RequestBody RoleAddBean roleAddBean){
        try {
            roleService.addRole(roleAddBean, getCurrentUserId());
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }



    /**
     * 编辑角色信息
     * @param updateBean 角色信息（详见实体属性）
     * @return 操作状态
     */
    @ApiOperation(value = "/update", notes = "编辑角色")
    @PostMapping(value = "/update")
    public ResponseBean updateRole(@RequestBody RoleUpdateBean updateBean){
        try {
            Integer userId = getCurrentUserId();
            roleService.updateRole(updateBean, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 角色权限详情
     * @param roleId 角色id
     * @return 角色详情
     */
    @ApiOperation(value = "/fetch", notes = "查询角色详情")
    @GetMapping(value = "/fetch")
    public ResponseBean selectBasicRole(@RequestParam("roleId") Integer roleId){
        try {
            RoleAuthorityBasicBean roleAuthorityBasicBean = authorityService.selectRoleBasic(roleId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, roleAuthorityBasicBean);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return 操作状态
     */
    @ApiOperation(value = "/remove", notes = "删除角色")
    @GetMapping(value = "/remove")
    public ResponseBean removeRole(@RequestParam("roleId") Integer roleId){
        try{
            Integer userId = getCurrentUserId();
            roleService.removeRole(roleId, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, roleId);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 复制角色权限
     * @param roleId 角色id
     * @param roleName 角色名称
     * @return 操作状态
     */
    @ApiOperation(value = "/copy", notes = "复制角色")
    @GetMapping(value = "/copy")
    public ResponseBean copyRole(@RequestParam("roleId") Integer roleId, @RequestParam("roleName") String roleName){
        try{

            Integer userId = getCurrentUserId();
            roleService.copyRole(roleId, roleName, userId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED);
        } catch (MembraneException e) {
            return getResponse(e.getCode());
        } catch (Exception e) {
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }

    /**
     * 编辑角色前的查询展示
     * @param roleId 角色id
     * @return 角色详情
     *//*
    @ApiOperation(value = "/select", notes = "编辑前的查询角色")
    @GetMapping(value = "/select")
    public ResponseBean selectRoleAfterUpdate(@RequestParam("roleId") Integer roleId){
        try {
            RoleUpdateAuthorityBean roleUpdateAuthorityBean = authorityService.selectBasicAfterUpdateByRoleId(roleId);
            return getResponse(ResponseCode.SUCCESS_PROCESSED, roleUpdateAuthorityBean);
        } catch (MembraneException e) {
            return getResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("查询{}失败",roleId,e.getMessage());
            return getResponse(ResponseCode.GENERIC_FAILURE);
        }
    }*/


}
