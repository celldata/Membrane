package cloud.celldata.membrane.service.impl;


import cloud.celldata.membrane.excep.MembraneException;
import cloud.celldata.membrane.mapper.*;
import cloud.celldata.membrane.pojo.ResponseCode;
import cloud.celldata.membrane.pojo.entity.*;
import cloud.celldata.membrane.pojo.vo.FunctionBean;
import cloud.celldata.membrane.pojo.vo.ModuleBean;
import cloud.celldata.membrane.pojo.vo.RoleIdAndNameBean;
import cloud.celldata.membrane.pojo.vo.RoleListBean;
import cloud.celldata.membrane.pojo.vo.role.RoleAddBean;
import cloud.celldata.membrane.pojo.vo.role.RoleApiBean;
import cloud.celldata.membrane.pojo.vo.role.RoleUpdateBean;
import cloud.celldata.membrane.service.RoleService;
import cloud.celldata.membrane.service.TokenService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 角色管理业务逻辑层
 *
 * @ProjectName: membrane
 * @Package: cloud.celldata.membrane.service.impl
 * @ClassName: RoleServiceImpl
 * @Description: java类作用描述
 * @Author: jiwang
 * @CreateDate: 2020/5/15 15:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/15 15:06
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private DataApiMapper dataApiMapper;

    @Autowired
    private DataScreeMapper dataScreeMapper;
    @Autowired
    private RoleAuthorityMapper authorityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleDataMapper roleDataMapper;

    /**
     * 获取所有角色信息
     * @param clientId 应用ID
     * @return 角色列表
     */
    @Override
    public List<RoleListBean> selectAllRole(Integer clientId) {
        // 获取所有角色信息
        List<RoleEntity> roleByClientId = getRoleByClientId(clientId);
        List<RoleListBean> roles = Lists.newArrayList();
        roleByClientId.forEach(r -> {
            RoleListBean roleListBean = new RoleListBean();
            // 获取功能权限
            if (r.getIsAllApi() == 1) {
                roleListBean.setFunctionAuthority("全部权限");
            } else {
                List<Integer> roleApiIDListByRoleId = authorityMapper.selectRoleApiIDListByRoleId(r.getId());
                List<ModuleBean> moduleBeans = authorityMapper.selectModuleAndFunctionByApiIdList(r.getClientId(), roleApiIDListByRoleId);
                StringBuilder functionAuthority = new StringBuilder();
                if (moduleBeans.size()>0) {
                    for (ModuleBean moduleBean : moduleBeans) {
                        for (FunctionBean functionBean : moduleBean.getFunctionList()) {
                            functionAuthority.append(functionBean.getFunctionName() + ",");
                        }
                    }
                }

                if (functionAuthority.length()>1) {
                    functionAuthority.deleteCharAt(functionAuthority.length() - 1);
                }
                roleListBean.setFunctionAuthority(String.valueOf(functionAuthority));
            }
            //获取数据权限
            if (r.getIsAllData()==1){
                roleListBean.setDataAuthority("全部权限");
            }else {
                List<DataScreeEntity> dataScreeEntities = dataScreeMapper.selectScreesByRoleId(r.getId());
                StringBuilder dataAuthority = new StringBuilder();
                if (dataScreeEntities.size()>0){
                    for (DataScreeEntity dataScreeEntity : dataScreeEntities) {
                        dataAuthority.append(dataScreeEntity.getScrName() + ",");
                    }
                }
                if (dataAuthority.length()>1){
                    dataAuthority.deleteCharAt(dataAuthority.length() - 1);
                }
                roleListBean.setDataAuthority(String.valueOf(dataAuthority));
            }

            // 获取平台信息
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(r.getId());
            ClientEntity client = roleMapper.selectClientByRoleId(r.getId());
            if(!Objects.isNull(client)){
                roleListBean.setClinetName(client.getAppName());
                roleListBean.setClientId(client.getId());
            }
            roleListBean.setRoleId(r.getId());
            roleListBean.setRoleName(r.getRoleName());
            roles.add(roleListBean);
        });
        return roles;
    }

    /**
     * 新增角色
     * @param roleAddBean 角色信息
     * @param creatorId 创建者id
     */
    @Override
    public void addRole(RoleAddBean roleAddBean, Integer creatorId) {
        Integer roleId = roleMapper.selectRoleName(-1, roleAddBean.getRoleName());
        if(roleId !=null && roleId > 0) {
            throw new MembraneException(ResponseCode.ROLE_NAME_EXISTS);
        }
        Date createTime = new Date();

        Role role = new Role();
        role.setClientId(roleAddBean.getClientId());
        role.setRoleName(roleAddBean.getRoleName());
        role.setEnableFlag(1);
        role.setCreatorId(creatorId);
        role.setCreateTime(createTime);
        role.setIsAllData(roleAddBean.getAllDataAuthority()?1:0);
        if (roleAddBean.getAllFunctionAuthority().equals(true)) {
            //全部角色功能权限
            role.setIsAllApi(1);
            roleMapper.addRole1(role);
        } else {
            // 部分角色权限
            role.setIsAllApi(0);
            roleMapper.addRole1(role);
            insertRoleApi(roleAddBean.getFunctionAuthorityIds(), role.getId(), creatorId, createTime);
        }
        if (!roleAddBean.getAllDataAuthority()){
            insertRoleData(roleAddBean.getDataAuthorityIds(),role.getId(),creatorId,createTime);
        }
    }


    /**
     * 根据平台id获取角色信息
     * @param id 查询条件
     * @return 角色列表
     */
    @Override
    public List<RoleEntity> getRoleByClientId(Integer id) {
        // 获取所有角色信息
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);
        return roleMapper.selectRoleByClientId(clientEntity);
    }

    /**
     * 修改角色
     * @param roleUpdateBean 角色信息
     * @param updaterId 更新者id
     */
    @Override
    public void updateRole(RoleUpdateBean roleUpdateBean, Integer updaterId) {
        if (null == roleUpdateBean) {
            return;
        }
        // 根据角色id查询角色基本信息
        RoleApiBean roleApiBean = authorityMapper.selectRoleBasicByRoleId(roleUpdateBean.getRoleId());
        if (null == roleApiBean) {
            throw new MembraneException(ResponseCode.ROLE_IS_NULL);
        }
        Date updateTime = new Date();
        Integer countByRoleName = roleMapper.selectRoleName(roleUpdateBean.getRoleId(), roleUpdateBean.getRoleName());
        if (null != countByRoleName && countByRoleName > 0) {
            throw new MembraneException(ResponseCode.ROLE_NAME_EXISTS);
        }
        //功能权限修改
        if (roleUpdateBean.getAllFunctionAuthority() && roleApiBean.getIsAllApi() == 1) {
            Role role = Role.RoleBuilder.aRole()
                    .withId(roleUpdateBean.getRoleId())
                    .withClientId(roleUpdateBean.getClientId())
                    .withRoleName(roleUpdateBean.getRoleName())
                    .withUpdaterId(updaterId)
                    .withUpdateTime(updateTime)
                    .build();

            roleMapper.updateRole1(updateRoleData(roleUpdateBean,role,roleApiBean.getIsAllData()));
        }

        if (!roleUpdateBean.getAllFunctionAuthority() && roleApiBean.getIsAllApi() == 1) {
            Role role = Role.RoleBuilder.aRole()
                    .withId(roleUpdateBean.getRoleId())
                    .withClientId(roleUpdateBean.getClientId())
                    .withRoleName(roleUpdateBean.getRoleName())
                    .withUpdaterId(updaterId)
                    .withUpdateTime(updateTime)
                    .withIsAllApi(0)
                    .build();
            roleMapper.updateRole1(updateRoleData(roleUpdateBean,role,roleApiBean.getIsAllData()));
            insertRoleApi(roleUpdateBean.getFunctionAuthorityIds(), roleUpdateBean.getRoleId(), updaterId, updateTime);

        }
        if (roleUpdateBean.getAllFunctionAuthority() && roleApiBean.getIsAllApi() != 1) {
            Role role = Role.RoleBuilder.aRole()
                    .withId(roleUpdateBean.getRoleId())
                    .withClientId(roleUpdateBean.getClientId())
                    .withRoleName(roleUpdateBean.getRoleName())
                    .withUpdaterId(updaterId)
                    .withUpdateTime(updateTime)
                    .withIsAllApi(1)
                    .build();
            roleMapper.updateRole1(updateRoleData(roleUpdateBean,role,roleApiBean.getIsAllData()));
            roleMapper.removeRoleApi(roleUpdateBean.getRoleId(),updaterId);
        }
        if (!roleUpdateBean.getAllFunctionAuthority() && roleApiBean.getIsAllApi() != 1) {
            Role role = Role.RoleBuilder.aRole()
                    .withId(roleUpdateBean.getRoleId())
                    .withClientId(roleUpdateBean.getClientId())
                    .withRoleName(roleUpdateBean.getRoleName())
                    .withUpdaterId(updaterId)
                    .withUpdateTime(updateTime)
                    .build();
            roleMapper.updateRole1(updateRoleData(roleUpdateBean,role,roleApiBean.getIsAllData()));
            roleMapper.removeRoleApi(roleUpdateBean.getRoleId(),updaterId);
            insertRoleApi(roleUpdateBean.getFunctionAuthorityIds(), roleUpdateBean.getRoleId(), updaterId, updateTime);

            removeUserTokenByRoleId(roleUpdateBean.getRoleId());
        }

        //数据权限修改
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @param userId 用户id
     */
    @Override
    public void removeRole(int roleId, Integer userId) {

        // 用户表时间更新
        //roleMapper.updateUserUpdateTime(userId, roleId);
        // 删除角色表信息
        roleMapper.removeRole(roleId, userId);
        // 关联表功能权限
        roleMapper.removeRoleApi(roleId, userId);
        // 关联表数据权限
        roleMapper.removeRoleData(roleId, userId);
        // 关联用户表删除
        roleMapper.removeRoleUser(roleId, userId);

        removeUserTokenByRoleId(roleId);
    }

    @Override
    public void copyRole(int roleId, String name, Integer userId) {
        // 根据角色ID获取角色信息
        RoleEntity roleByRoleId = roleMapper.getRoleByRoleId(roleId);
        if(Objects.isNull(roleByRoleId)){
            throw new MembraneException(ResponseCode.ROLE_IS_NULL);
        }
        // 开始进行复制添加
        roleByRoleId.setRoleName(name);
        roleByRoleId.setId(null);
        roleByRoleId.setCreateTime(new Date());
        roleByRoleId.setCreatorId(userId);
        roleByRoleId.setUpdateId(userId);
        roleByRoleId.setUpdateTime(new Date());
        roleByRoleId.setIsAllApi(roleByRoleId.getIsAllApi());
        // 添加前先查询此名称是否存在
        List<RoleEntity> roleByRoleName = roleMapper.selectRoleByRoleName(name);
        if(!CollectionUtils.isEmpty(roleByRoleName)){
            throw new MembraneException(ResponseCode.ROLE_NAME_EXISTS);
        }
        roleMapper.addRole(roleByRoleId);
        if (roleByRoleId.getIsAllApi() != 1) {
            // 获取功能权限
            List<Integer> integerList = authorityMapper.selectRoleApiIDListByRoleId(roleId);
            //插入功能权限
            insertRoleApi(integerList, roleByRoleId.getId(), userId, new Date());
        }


    }

    @Override
    public List<RoleIdAndNameBean> selectAllRoleForClientId(Integer clientId) {
        // 获取所有角色信息
        List<RoleEntity> roleByClientId = getRoleByClientId(clientId);
        List<RoleIdAndNameBean> roles = Lists.newArrayList();
        roleByClientId.forEach(r -> {
            RoleIdAndNameBean roleListBean = new RoleIdAndNameBean();
            roleListBean.setRoleId(r.getId());
            roleListBean.setRoleName(r.getRoleName());
            roles.add(roleListBean);
        });
        return roles;

    }


    /**
     * 插入 角色对应功能权限
     *
     * @param ids    功能权限List
     * @param roleId 角色ID
     * @param userId 用户ID
     * @param date   时间
     */
    private void insertRoleApi(List<Integer> ids, Integer roleId, Integer userId, Date date) {
        List<RoleApi> roleApiList = new ArrayList<>();
        for (Integer id : ids) {
            roleApiList.add(RoleApi.RoleApiBuilder.aRoleApi()
                    .withRoleId(roleId)
                    .withApiId(id)
                    .withEnableFlag(1)
                    .withCreatorId(userId)
                    .withCreateTime(date)
                    .build());
        }
        if (roleApiList.size() > 0) {
            roleMapper.insertRoleApi(roleApiList);
        }

    }

    /**
     * 插入 角色对应数据权限
     *
     * @param ids    数据权限List
     * @param roleId 角色ID
     * @param userId 用户ID
     * @param date   时间
     */
    private void insertRoleData(List<Integer> ids, Integer roleId, Integer userId, Date date) {
        List<RoleData> roleDataList = new ArrayList<>();
        for (Integer id : ids) {
            roleDataList.add(RoleData.RoleDataBuilder.aRoleData()
                    .withRoleId(roleId)
                    .withDataId(id)
                    .withEnableFlag(1)
                    .withCreatorId(userId)
                    .withCreateTime(date)
                    .build());
        }
        if (roleDataList.size() > 0) {
            roleDataMapper.insertRoleData(roleDataList);
        }

    }

    /**
     * 根据角色Id 删除角色下所有正在登陆状态的UserToken
     * @param roleId 角色ID
     */
    private void removeUserTokenByRoleId(Integer roleId){
        List<Integer> userIdList = userMapper.selectUserByRoleId(roleId);
        if(userIdList.size() > 0){
            // 根据userId删除redis中的token
            for (Integer uid : userIdList) {
                tokenService.removeUserToken(uid);
            }
        }
    }

    private Role updateRoleData(RoleUpdateBean roleUpdateBean,Role role,Integer roleData){

        if (roleUpdateBean.getAllDataAuthority()){
            switch (roleData){
                // 数据权限 由 部分改为全部
                case 0:
                    roleMapper.removeRoleData(role.getId(),role.getUpdaterId());
                    role.setIsAllData(1);
                    return role;
                // 数据权限 由 数据权限全部到全部
                case 1:
                    role.setIsAllData(1);
                    return role;
            }

        }else {
            switch (roleData){
                // 数据权限 由 部分改为部分
                case 0:
                    roleMapper.removeRoleData(role.getId(),role.getUpdaterId());
                    role.setIsAllData(0);
                    insertRoleData(roleUpdateBean.getDataAuthorityIds(),role.getId(),role.getUpdaterId(),role.getUpdateTime());
                    return role;

                // 数据权限 由 全部改为部分
                case 1:
                    insertRoleData(roleUpdateBean.getDataAuthorityIds(),role.getId(),role.getUpdaterId(),role.getUpdateTime());
                    role.setIsAllData(0);
                    return role;
            }

        }
        return role;
    }

}
