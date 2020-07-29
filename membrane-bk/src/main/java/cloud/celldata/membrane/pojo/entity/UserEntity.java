package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 用户实体
 */
public class UserEntity {

    // 用户id
    private Integer id;

    // 用户名
    private String userName;

    // 密码
    private String password;

    // 姓名
    private String fullName;

    // 手机号
    private String telephone;

    // 邮箱
    private String email;

    // 登录次数
    private Integer loginNum;

    // 是否禁用：1启用，2禁用
    private Integer activeFlag;

    // 是否管理员：0否，1是
    private Integer privilege;

    // 创建者id
    private Integer creatorId;

    //更新时间
    private Date updateTime;

    //更新人Id
    private Integer updaterId;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Integer updaterId) {
        this.updaterId = updaterId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final class UserEntityBuilder {
        private Integer id;
        private String userName;
        private String password;
        private String fullName;
        private String telephone;
        private String email;
        private Integer loginNum;
        private Integer activeFlag;
        private Integer privilege;
        private Integer creatorId;

        private UserEntityBuilder() {
        }

        public static UserEntityBuilder anUserEntity() {
            return new UserEntityBuilder();
        }

        public UserEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserEntityBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserEntityBuilder withTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public UserEntityBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserEntityBuilder withLoginNum(Integer loginNum) {
            this.loginNum = loginNum;
            return this;
        }

        public UserEntityBuilder withActiveFlag(Integer activeFlag) {
            this.activeFlag = activeFlag;
            return this;
        }

        public UserEntityBuilder withPrivilege(Integer privilege) {
            this.privilege = privilege;
            return this;
        }

        public UserEntityBuilder withCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(id);
            userEntity.setUserName(userName);
            userEntity.setPassword(password);
            userEntity.setFullName(fullName);
            userEntity.setTelephone(telephone);
            userEntity.setEmail(email);
            userEntity.setLoginNum(loginNum);
            userEntity.setActiveFlag(activeFlag);
            userEntity.setPrivilege(privilege);
            userEntity.setCreatorId(creatorId);
            return userEntity;
        }
    }
}
