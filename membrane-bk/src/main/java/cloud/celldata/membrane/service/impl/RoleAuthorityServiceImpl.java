package cloud.celldata.membrane.service.impl;


import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.DataScreeMapper;
import cloud.celldata.membrane.mapper.RoleAuthorityMapper;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.mapper.ApiMapper;
import cloud.celldata.membrane.pojo.entity.DataScreeEntity;
import cloud.celldata.membrane.pojo.vo.FunctionBean;
import cloud.celldata.membrane.pojo.vo.ModuleBean;
import cloud.celldata.membrane.pojo.vo.role.*;
import cloud.celldata.membrane.service.RoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限业务逻辑层
 */
@Service
@Transactional
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    @Autowired
    private RoleAuthorityMapper authorityMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private DataScreeMapper dataScreeMapper;

    /**
     * 进行角色信息编辑前的查询
     * @param roleId 角色id
     * @return 角色信息
     */
    @Override
    public RoleAuthorityBasicBean selectRoleBasic(Integer roleId) {
        // 查询角色基本信息
        RoleAuthorityBasicBean roleAuthorityBasicBean = new RoleAuthorityBasicBean();
        // 根据角色id查询角色基本信息
        RoleApiBean roleApiBean = authorityMapper.selectRoleBasicByRoleId(roleId);
        if(null == roleApiBean){
            throw new MembraneException(ResponseCode.ROLE_IS_NULL);
        }
        roleAuthorityBasicBean.setRoleId(roleApiBean.getRoleId());
        roleAuthorityBasicBean.setRoleName(roleApiBean.getRoleName());
        roleAuthorityBasicBean.setClientId(roleApiBean.getClientId());
        roleAuthorityBasicBean.setClientName(roleApiBean.getClientName());
        List<ModuleBean> moduleBeans;
        FunctionAuthorityBean functionAuthorityBean = new FunctionAuthorityBean();
        if (roleApiBean.getIsAllApi() == 1) {
            // 查询应用所有功能权限
            moduleBeans = apiMapper.selectModuleAndFunctionByClientID(roleApiBean.getClientId(),null);
            ArrayList<Integer> integers = new ArrayList<>();
            if (moduleBeans != null && moduleBeans.size() != 0) {
                for (ModuleBean moduleBean : moduleBeans) {
                    for (FunctionBean functionBean : moduleBean.getFunctionList()) {
                        integers.add(functionBean.getFunctionId());
                    }
                }
            }
            functionAuthorityBean.setAllFunctionAuthority(true);
            functionAuthorityBean.setFunctionAuthority(moduleBeans);
            functionAuthorityBean.setHaveAuthority(integers);

        } else {
            //查询部分功能权限
            //查询部分功能权限集合
            List<Integer> roleApiIDListByRoleId = authorityMapper.selectRoleApiIDListByRoleId(roleApiBean.getRoleId());
            moduleBeans = authorityMapper.selectModuleAndFunctionByApiIdList(roleApiBean.getClientId(), roleApiIDListByRoleId);
            functionAuthorityBean.setAllFunctionAuthority(false);
            functionAuthorityBean.setFunctionAuthority(moduleBeans);
            functionAuthorityBean.setHaveAuthority(roleApiIDListByRoleId);
        }
        roleAuthorityBasicBean.setFunctionAuthorityBean(functionAuthorityBean);

        AuthorityBean authorityBean = new AuthorityBean();

        //数据权限
        List<DataScreeEntity> screeEntities = dataScreeMapper.selectScreesByRoleId(roleId);
        List<Integer> ids = new ArrayList<>();
        if (screeEntities.size()!=0) {
            for (DataScreeEntity screeEntity : screeEntities) {
                ids.add(screeEntity.getId());
            }
        }
        List<DataScreeEntity> dataScreeEntities = dataScreeMapper.selectScreesByClientId(roleApiBean.getClientId());
        authorityBean.setAuthority(dataScreeEntities);
        authorityBean.setHaveDataAuthority(ids);
        if (roleApiBean.getIsAllData() ==1){
            //全部数据权限
            authorityBean.setAllDataAuthority(true);
        }else {
            //部分数据权限
            authorityBean.setAllDataAuthority(false);
        }
        roleAuthorityBasicBean.setAuthorityBean(authorityBean);
        return roleAuthorityBasicBean;
    }

    @Override
    public RoleUpdateAuthorityBean selectBasicAfterUpdateByRoleId(Integer roleId) {
        /*// 根据角色id查询角色基本信息
        RoleApiBean roleApiBean = authorityMapper.selectRoleBasicByRoleId(roleId);
        if(null == roleApiBean){
            throw new MembraneException(ResponseCode.ROLE_IS_NULL);
        }
        RoleUpdateAuthorityBean roleUpdateAuthorityBean = new RoleUpdateAuthorityBean();
        roleUpdateAuthorityBean.setRoleId(roleApiBean.getRoleId());
        roleUpdateAuthorityBean.setRoleName(roleApiBean.getRoleName());
        roleUpdateAuthorityBean.setClientId(roleApiBean.getClientId());
        roleUpdateAuthorityBean.setClientName(roleApiBean.getClientName());
        // 根据角色id查询所有数据权限信息
        List<DataInfoBean> dataInfoBeans = dataAuthorityMapper.selctDataAuthorityByRoleId(roleId);
        // 根据角色id查询所有功能权限信息
        List<ApiBean> apiBeans = functionAuthorityMapper.selectFunctionAuthorityByRoleId(roleId);
        Integer clientId = roleUpdateAuthorityBean.getClientId();
        Boolean isAllData = false;
        Boolean isAllApi = false;
        // 整理数据 判断是否选择全部权限
        if(!CollectionUtils.isEmpty(dataInfoBeans) && dataInfoBeans.size() == 1
                && dataInfoBeans.get(0) != null && dataInfoBeans.get(0).getIsAll() == 1){
            isAllData = true;
        }
        List<Integer> apiList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(apiBeans) && apiBeans.get(0) != null){
            // 过滤功能权限是全部的
            List<ApiBean> collect = apiBeans.stream().filter(p -> p.getParentId() != 0).collect(Collectors.toList());
            if(collect.size() > 0){
                apiList = collect.stream().map(ApiBean::getApiId).collect(Collectors.toList());
            }
        }
        List<Integer> dataList = new ArrayList<>();
        if(dataInfoBeans.size() > 0 && dataInfoBeans.get(0) != null){
            List<DataInfoBean> collect = dataInfoBeans.stream().filter(p -> p.getIsAll() != 1).collect(Collectors.toList());
            if(collect.size() > 0){
                dataList = dataInfoBeans.stream().map(DataInfoBean::getId).collect(Collectors.toList());
            }
        }
        if(apiBeans.size() == 1 && apiBeans.get(0) != null && apiBeans.get(0).getParentId() == 0){
            isAllApi = true;
        }
        FunctionAuthorityBean functionAuthorityBean = new FunctionAuthorityBean();
        functionAuthorityBean.setAllFunctionAuthority(isAllApi);
        // 根据平台id查询功能权限
        List<LabelBean> labelBeans = functionAuthorityMapper.selectFunctionAuthorityByClientId(clientId);
        functionAuthorityBean.setFunctionAuthority(labelBeans);
        functionAuthorityBean.setHaveAuthority(apiList);
        roleUpdateAuthorityBean.setFunctionAuthorityBean(functionAuthorityBean);
        // 根据平台id查询所有数据权限
        AuthorityBean authorityBean = new AuthorityBean();
        List<DataAuthorityBean> dataAuthorityBeans = dataAuthorityMapper.selectDataAuthorityByClientId(clientId);
        authorityBean.setAllDataAuthority(isAllData);
        authorityBean.setNoHaveDataAuthority(dataList);
        authorityBean.setHaveDataAuthority(dataAuthorityBeans);
        roleUpdateAuthorityBean.setAuthorityBean(authorityBean);
        return roleUpdateAuthorityBean;*/
        return null;
    }
}
