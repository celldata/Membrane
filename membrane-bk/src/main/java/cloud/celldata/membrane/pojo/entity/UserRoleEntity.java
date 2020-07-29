package cloud.celldata.membrane.pojo.entity;

/**
 * 用户与角色关系实体
 **/
public class UserRoleEntity {

    // 用户角色编号
    private Integer id;

    // 用户编号
    private Integer userId;

    // 角色编号
    private Integer roleId;

    // 关系创建者用户编号
    private Integer creatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public static final class UserRoleEntityBuilder {
        private Integer id;
        private Integer userId;
        private Integer roleId;
        private Integer creatorId;

        private UserRoleEntityBuilder() {
        }

        public static UserRoleEntityBuilder anUserRoleEntity() {
            return new UserRoleEntityBuilder();
        }

        public UserRoleEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserRoleEntityBuilder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public UserRoleEntityBuilder withRoleId(Integer roleId) {
            this.roleId = roleId;
            return this;
        }

        public UserRoleEntityBuilder withCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public UserRoleEntity build() {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setId(id);
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            userRoleEntity.setCreatorId(creatorId);
            return userRoleEntity;
        }
    }

}
