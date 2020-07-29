package cloud.celldata.membrane.pojo.vo;

import java.util.List;

/**
 * 平台角色信息实体
 *
 * @author wyw
 * @date 2019-04-25
 **/
public class SelectedClientRoleVO {

    // 平台id
    private Integer clientId;

    // 平台角色id列表
    private List<Integer> roleIds;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public static final class SelectedClientRoleVOBuilder {
        private Integer clientId;
        private List<Integer> roleIds;

        private SelectedClientRoleVOBuilder() {
        }

        public static SelectedClientRoleVOBuilder aSelectedClientRoleVO() {
            return new SelectedClientRoleVOBuilder();
        }

        public SelectedClientRoleVOBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public SelectedClientRoleVOBuilder withRoleIds(List<Integer> roleIds) {
            this.roleIds = roleIds;
            return this;
        }

        public SelectedClientRoleVO build() {
            SelectedClientRoleVO selectedClientRoleVO = new SelectedClientRoleVO();
            selectedClientRoleVO.setClientId(clientId);
            selectedClientRoleVO.setRoleIds(roleIds);
            return selectedClientRoleVO;
        }
    }
}
