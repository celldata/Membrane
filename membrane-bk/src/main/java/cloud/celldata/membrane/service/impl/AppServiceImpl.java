package cloud.celldata.membrane.service.impl;

import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.*;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.vo.*;
import cloud.celldata.membrane.pojo.entity.*;
import cloud.celldata.membrane.pojo.enumeration.IsAuthenticationEnum;
import cloud.celldata.membrane.service.AppService;
import cloud.celldata.membrane.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 平台信息管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: AppServiceImpl
 * @Description: 平台信息管理业务逻辑层
 * @Author: jiwang
 * @CreateDate: 2020/5/19 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/19 14:22
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private DataAttributeMapper dataAttributeMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataConfigMapper dataConfigMapper;

    @Autowired
    private DataScreeMapper dataScreeMapper;

    @Autowired
    private DataScreeAttributeRelationMapper dataScreeAttributeRelationMapper;

    @Override
    public ClientEntity getClient(ClientEntity clientEntity) {

        return clientMapper.getClient(clientEntity);
    }

    @Override
    public List<ClientListBean> selectAllClient(Integer sign, Integer authentication, Integer verification,
                                                Integer tokenCheckType, Integer clientId) {
        List<ClientListBean> ClientListBeans = clientMapper.selectAllClient(authentication, verification, tokenCheckType, clientId);
        if(0 == sign){
            ClientListBeans = ClientListBeans.stream().filter(c -> !c.getClientName().equals("权限管理系统")).collect(Collectors.toList());
        }
        return ClientListBeans;
    }

    @Override
    public void addClient(CertificationBean clientBean, Integer userId) {

        if (clientMapper.countClientByclientName(clientBean.getClientName(), null) > 0) {
            throw new MembraneException(ResponseCode.APP_NAME_EXISTS);
        }
        if (clientMapper.countClientByAppId(clientBean.getAppId(), null) > 0) {
            throw new MembraneException(ResponseCode.APP_ID_EXISTS);
        }
        if(clientBean.getAuthentication().getCode().equals(IsAuthenticationEnum.UNVERIFIED.getCode())){
            clientBean.setVerification(null);
        }
        clientBean.setAppId(UUID.randomUUID().toString().replaceAll("-",""));
        clientBean.setSecret(UUID.randomUUID().toString().replaceAll("-",""));

        clientMapper.addClient(clientBean,userId);
    }

    @Override
    public ClientBean updateApp(ClientBean clientBean, Integer userId) {

        if (clientBean.getSecret() ==null){
            if (clientMapper.countClientByclientName(clientBean.getClientName(), clientBean.getClientId()) > 0) {
                throw new MembraneException(ResponseCode.APP_NAME_EXISTS);
            }
            if (clientMapper.countClientByAppId(clientBean.getAppId(), clientBean.getClientId()) > 0) {
                throw new MembraneException(ResponseCode.APP_ID_EXISTS);
            }
        }else {
            clientBean.setSecret(UUID.randomUUID().toString().replaceAll("-",""));
        }


        clientMapper.updateApp(clientBean,userId);
        return clientBean;
    }


    @Override
    public void addModuleAndFunction(ModuleBean moduleBean, Integer userId) {
        Date createTime = new Date();
        if (moduleBean.getFunctionList() == null || moduleBean.getFunctionList().size() == 0) {
            //新增模块
            Integer countName = apiMapper.countModuleNameByClientIdAndApiName(moduleBean.getClientId(), moduleBean.getModuleName(), null);
            if (countName > 0){
                throw new MembraneException(ResponseCode.MODULE_NAME_EXISTS);
            }
            ApiEntity apiEntity = new ApiEntity();
            apiEntity.setParentId(0);
            apiEntity.setClientId(moduleBean.getClientId());
            apiEntity.setApiName(moduleBean.getModuleName());
            apiEntity.setApiNum(1);
            apiEntity.setCreatorId(userId);
            apiEntity.setCreateTime(createTime);
            apiEntity.setEnableFlag(1);
            apiMapper.insertModuleAndFunction(apiEntity);
        }else{
            //新增功能

            List<FunctionBean> functionList = moduleBean.getFunctionList();
            for (FunctionBean functionBean : functionList) {
                Integer countName = apiMapper.countFunctionNameByClientIdAndApiName(moduleBean.getClientId(),
                        functionBean.getFunctionName(),moduleBean.getModuleId(),null);
                if (countName > 0){
                    throw new MembraneException(ResponseCode.FUNCTION_NAME_EXISTS);
                }
                ApiEntity apiEntity = new ApiEntity();
                apiEntity.setParentId(moduleBean.getModuleId());
                apiEntity.setClientId(moduleBean.getClientId());
                apiEntity.setApiName(functionBean.getFunctionName());
                apiEntity.setEnableFlag(1);
                apiEntity.setApiNum(2);
                apiEntity.setCreatorId(userId);
                apiEntity.setCreateTime(createTime);
                apiEntity.setIsTree(1);
                apiEntity.setType(functionBean.getApiType());
                //插入模块对应功能表
                apiMapper.insertModuleAndFunction(apiEntity);
                List<String> apiUrlList = functionBean.getApiUrlList();
                for (String s : apiUrlList) {
                    UrlEntity urlEntity = new UrlEntity();
                    urlEntity.setFunctionUrl(s);
                    urlEntity.setEnableFlag(1);
                    urlEntity.setCreatorId(userId);
                    urlEntity.setCreateTime(createTime);
                    //插入功能对应URL表 一对多
                    apiMapper.insertUrl(urlEntity);
                    ApiUrlEntity apiUrlEntity = new ApiUrlEntity();
                    apiUrlEntity.setApiId(apiEntity.getId());
                    apiUrlEntity.setFunctionUrlId(urlEntity.getId());
                    apiUrlEntity.setEnableFlag(1);
                    apiUrlEntity.setCreatorId(userId);
                    apiUrlEntity.setCreateTime(createTime);
                    //插入功能和URL对应中间表
                    apiMapper.insertApiUrl(apiUrlEntity);
                }
            }

        }
    }

    @Override
    public List<ModuleBean> selectModuleAndFunctionByClientID(Integer clientId,Integer moduleId) {
        return apiMapper.selectModuleAndFunctionByClientID(clientId,moduleId);
    }

    @Override
    public void removeFunction(RemoveMoudleAndFuctionBean removeMoudleAndFuctionBean, Integer userId) {
        for (Integer id : removeMoudleAndFuctionBean.getIds()) {
            //删除URI表
            apiMapper.removeFunctionURI(id, userId);
            //删除中间表
            apiMapper.removeFunctionApiURI(id, userId);
            //删除功能表
            apiMapper.removeFunctionApi(id, userId);
        }


    }

    @Override
    public void removeModule(RemoveMoudleAndFuctionBean removeMoudleAndFuctionBean, Integer userId) {

        for (Integer id : removeMoudleAndFuctionBean.getIds()) {
            //判断 该模块下 是否有应用
            Integer functionCount = apiMapper.selectFunctionCountByModuleId(id);
            if (functionCount > 0) {
                throw new MembraneException(ResponseCode.MODULE_NOT_EMPTY);
            }
            apiMapper.removeModuleApi(id, userId);
        }
    }

    @Override
    public void updateModule(ModuleBean moduleBean, Integer userId) {
        Integer countName = apiMapper.countModuleNameByClientIdAndApiName(moduleBean.getClientId(), moduleBean.getModuleName(), moduleBean.getModuleId());
        if (countName > 0) {
            throw new MembraneException(ResponseCode.MODULE_NAME_EXISTS);
        }
        apiMapper.updateModule(moduleBean, userId);
    }

    @Override
    public void updateFunction(FunctionBean functionBean, Integer userId) {
        Integer countName = apiMapper.countFunctionNameByClientIdAndApiName(functionBean.getClientId(), functionBean.getFunctionName(), functionBean.getModuleId(),functionBean.getFunctionId());
        if (countName > 0) {
            throw new MembraneException(ResponseCode.FUNCTION_NAME_EXISTS);
        }
        Date createTime = new Date();

        apiMapper.updateFunction(functionBean, userId);

        //删除URI表
        apiMapper.removeFunctionURI(functionBean.getFunctionId(), userId);
        //删除中间表
        apiMapper.removeFunctionApiURI(functionBean.getFunctionId(), userId);
        if (!Collections.isEmpty(functionBean.getApiUrlList())) {
            for (String s : functionBean.getApiUrlList()) {
                UrlEntity urlEntity = new UrlEntity();
                urlEntity.setFunctionUrl(s);
                urlEntity.setEnableFlag(1);
                urlEntity.setCreatorId(userId);
                urlEntity.setCreateTime(createTime);
                //插入功能对应URL表 一对多
                apiMapper.insertUrl(urlEntity);
                ApiUrlEntity apiUrlEntity = new ApiUrlEntity();
                apiUrlEntity.setApiId(functionBean.getFunctionId());
                apiUrlEntity.setFunctionUrlId(urlEntity.getId());
                apiUrlEntity.setEnableFlag(1);
                apiUrlEntity.setCreatorId(userId);
                apiUrlEntity.setCreateTime(createTime);
                //插入功能和URL对应中间表
                apiMapper.insertApiUrl(apiUrlEntity);
            }
        }

    }

    @Override
    public void removeApp(Integer clientId, Integer userId) {
        //查询应用下是否有模块
        Integer countModule = apiMapper.selectModuleByClientid(clientId);
        if (countModule > 0) {
            throw new MembraneException(ResponseCode.APP_NOT_EMPTY);
        }
        List<RoleIdAndNameBean> roleIdAndNameBeans = roleService.selectAllRoleForClientId(clientId);
        if (!Collections.isEmpty(roleIdAndNameBeans)){
            throw new MembraneException(ResponseCode.APPP_ROLE_NOT_EMPTY);
        }

        apiMapper.removeApp(clientId, userId);
    }

    @Override
    public void certificationApp(CertificationBean certificationBean, Integer userId) {
        if(certificationBean.getAuthentication().getCode().equals(IsAuthenticationEnum.UNVERIFIED.getCode())){
            certificationBean.setVerification(null);
            certificationBean.setExternalAuthId(null);
        }
        apiMapper.certificationApp(certificationBean, userId);
    }

    @Override
    public List selectFunctionAuthorityByClientId(Integer clientId, Integer flag) {

        if (flag == 0) {
            //功能权限
            List<ModuleBean> moduleBeans = apiMapper.selectModuleAndFunctionByClientID(clientId,null);
            return moduleBeans;
        } else {
            return null;
        }

    }

    /**
     *
     * @param dataConfigEntity 数据权限 基础配置实体
     * @param userId 用户ID
     */
    @Override
    public void addConfig(DataConfigEntity dataConfigEntity, Integer userId) {

        Integer integer = dataConfigMapper.countDataConfigName(dataConfigEntity.getClientId(),
                dataConfigEntity.getName(), null);
        if (integer > 0){
            throw new MembraneException(ResponseCode.DATA_CONFIG_NAME_EXISTS);
        }
        dataConfigMapper.addDataConfig(dataConfigEntity,userId);
    }

    /**
     *
     * @param dataConfigEntity 数据权限 基础配置实体
     * @param userId 用户ID
     */
    @Override
    public void updateConfig(DataConfigEntity dataConfigEntity, Integer userId) {
        Integer integer = dataConfigMapper.countDataConfigName(dataConfigEntity.getClientId(),
                dataConfigEntity.getName(), dataConfigEntity.getId());
        if (integer > 0){
            throw new MembraneException(ResponseCode.DATA_CONFIG_NAME_EXISTS);
        }
        dataConfigMapper.updateDataConfig(dataConfigEntity,userId);
    }

    /**
     *  删除数据权限 基础配置
     * @param idList id list
     * @param userId 用户ID
     */
    @Override
    public void removeConfig(List<Integer> idList, Integer userId) {
        //判断能否删除
        for (Integer id : idList) {
            List<DataAttributeEntity> dataAttributeEntities = dataAttributeMapper.selectAttributesByDataConfigId(id);
            if (!dataAttributeEntities.isEmpty()){
                throw new MembraneException(ResponseCode.DATA_CONFIG_NOT_EMPTY);
            }
        }
        dataConfigMapper.removeDataConfig(idList,userId);
    }

    /**
     * 查询数据权限 基础配置
     * @param clientId 应用ID
     * @param type 类型
     * @param pageIndex 当前页
     * @param pageSize 页大小
     * @return
     */
    @Override
    public PageInfo selectDateConfigEntity(Integer clientId, Integer type, Integer pageIndex, Integer pageSize) {

        if (pageIndex == null || pageSize ==null){
             pageSize = dataConfigMapper.countDataConfig(clientId,type);
             pageIndex = 1;
        }
        PageHelper.startPage(pageIndex,pageSize);
        List<DataConfigEntity> dataConfigEntityList = dataConfigMapper.selectDateConfigEntity(clientId,type);
        return new PageInfo(dataConfigEntityList);
    }

    /**
     * 添加属性配置实体
     *
     * @param dataAttributeEntity 数据权限 属性配置实体
     * @param userId 用户ID
     */
    @Override
    public void addAttribute(DataAttributeEntity dataAttributeEntity, Integer userId) {
        if (dataAttributeMapper.countDataAttribute(dataAttributeEntity.getId(), dataAttributeEntity.getAttributeName()) > 0) {
            throw new MembraneException(ResponseCode.DATA_ATTRIBUTE_REPEAT);
        }
        dataAttributeMapper.addAttribute(dataAttributeEntity, userId);
    }

    /**
     * 编辑属性配置实体
     *
     * @param dataAttributeEntity 数据权限 属性配置实体
     * @param userId 用户ID
     */
    @Override
    public void updateAttribute(DataAttributeEntity dataAttributeEntity, Integer userId) {
        if (dataAttributeMapper.countDataAttribute(dataAttributeEntity.getId(), dataAttributeEntity.getAttributeName()) > 0) {
            throw new MembraneException(ResponseCode.DATA_ATTRIBUTE_REPEAT);
        }
        dataAttributeMapper.updateAttribute(dataAttributeEntity, userId);
    }

    /**
     * 根据基础配置ID分页查询属性配置列表
     *
     * @param dataConfigId 基础配置ID
     * @param pageIndex 页号
     * @param pageSize 页大小
     * @return 同一基础配置下属性配置列表
     */
    @Override
    public List<DataAttributeEntity> selectAttributesByDataConfigId(Integer dataConfigId, Integer pageIndex, Integer pageSize) {
        if (null != pageIndex && null != pageSize) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        return dataAttributeMapper.selectAttributesByDataConfigId(dataConfigId);
    }

    /**
     * 删除属性配置
     *
     * @param idList 属性配置ID列表
     * @param userId 用户ID
     */
    @Override
    public void removeAttribute(List<Integer> idList, Integer userId) {
        if (CollectionUtils.isEmpty(idList)) {
            throw new MembraneException(ResponseCode.ILLEGAL_PARAMETER);
        }
        if (dataScreeAttributeRelationMapper.countByAttIds(idList) > 0) {
            throw new MembraneException(ResponseCode.ATTRIBUTE_USED_BY_RULE);
        }
        dataAttributeMapper.removeAttribute(idList, userId);
    }

    /**
     * 添加条件配置
     *
     * @param dataScreeEntity 条件配置实体
     * @param userId 用户ID
     */
    @Override
    public void addScree(DataScreeEntity dataScreeEntity, Integer userId) {
        if (dataScreeMapper.countScree(dataScreeEntity.getId(), dataScreeEntity.getScrName()) > 0) {
            throw new MembraneException(ResponseCode.DATA_SCREE_REPEAT);
        }
        //新增条件配置
        dataScreeMapper.addScree(dataScreeEntity, userId);
        //批量新增条件配置与资源和属性的关联关系
        batchAddScreeAttributeRelations(dataScreeEntity.getId(), dataScreeEntity.getAttIdList(), userId);
    }

    /**
     * 编辑条件配置
     *
     * @param dataScreeEntity 条件配置实体
     * @param userId 用户ID
     */
    @Override
    public void updateScree(DataScreeEntity dataScreeEntity, Integer userId) {
        if (dataScreeMapper.countScree(dataScreeEntity.getId(), dataScreeEntity.getScrName()) > 0) {
            throw new MembraneException(ResponseCode.DATA_SCREE_REPEAT);
        }
        //编辑条件配置
        dataScreeMapper.updateScree(dataScreeEntity, userId);
        //删除条件配置与资源和属性的关联关系
        List<Integer> screeIds = new ArrayList<>();
        screeIds.add(dataScreeEntity.getId());
        dataScreeAttributeRelationMapper.batchDeleteRelations(screeIds, userId);
        //批量新增条件配置与资源和属性的关联关系
        batchAddScreeAttributeRelations(dataScreeEntity.getId(), dataScreeEntity.getAttIdList(), userId);
    }

    /**
     * 根据ClientId分页查询条件配置列表
     *
     * @param clientId 应用ID
     * @param pageIndex 页号
     * @param pageSize 页大小
     * @return 同一应用下条件配置列表
     */
    @Override
    public List<DataScreeEntity> selectScreesByClientId(Integer clientId, Integer pageIndex, Integer pageSize) {
        if (null != pageIndex && null != pageSize) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        //分页查询条件配置列表
        List<DataScreeEntity> screeEntities = dataScreeMapper.selectScreesByClientId(clientId);
        if (!CollectionUtils.isEmpty(screeEntities)) {
            //装配资源与属性信息
            List<Integer> screeIds = screeEntities.stream().map(DataScreeEntity::getId).collect(Collectors.toList());
            List<DataScreeAttributeRelationEntity> relations = dataScreeAttributeRelationMapper.selectByScreeIds(screeIds);
            if (!CollectionUtils.isEmpty(relations)) {
                for (DataScreeEntity screeEntity : screeEntities) {
                    for (DataScreeAttributeRelationEntity relation : relations) {
                        if (screeEntity.getId().equals(relation.getScreeId())) {
                            List<DataScreeAttributeRelationEntity> attrInfoList = screeEntity.getAttInfoList();
                            if (CollectionUtils.isEmpty(attrInfoList)) {
                                attrInfoList = new ArrayList<>();
                            }
                            attrInfoList.add(relation);
                            screeEntity.setAttInfoList(attrInfoList);
                        }
                    }
                }
            }
        }
        return screeEntities;
    }

    /**
     * 删除条件配置
     *
     * @param idList 条件配置ID列表
     * @param userId 用户ID
     */
    @Override
    public void removeScree(List<Integer> idList, Integer userId) {
        if (CollectionUtils.isEmpty(idList)) {
            throw new MembraneException(ResponseCode.ILLEGAL_PARAMETER);
        }
        //删除条件配置
        dataScreeMapper.removeScree(idList, userId);
        //删除条件配置与资源和属性的关联关系
        dataScreeAttributeRelationMapper.batchDeleteRelations(idList, userId);
    }

    /**
     * 批量新增条件配置与资源和属性的关联关系
     * @param screeId 条件配置ID
     * @param attIdList 资源与属性ID列表
     * @param userId 用户ID
     */
    private void batchAddScreeAttributeRelations(Integer screeId, Integer[][] attIdList, Integer userId) {
        if (!ArrayUtils.isEmpty(attIdList)) {
            List<DataScreeAttributeRelationEntity> dataScreeAttributeEntities = new ArrayList<>();
            for (Integer[] relation : attIdList) {
                if (!ArrayUtils.isEmpty(relation) && (2 == relation.length)) {
                    dataScreeAttributeEntities.add(DataScreeAttributeRelationEntity.DataScreeAttributeEntityBuilder
                            .aDataScreeAttributeEntity()
                            .withScreeId(screeId)
                            .withConfigId(relation[0])
                            .withAttributeId(relation[1])
                            .build());
                }
            }
            if (!CollectionUtils.isEmpty(dataScreeAttributeEntities)) {
                dataScreeAttributeRelationMapper.batchAddRelations(dataScreeAttributeEntities, userId);
            }
        }
    }

}
