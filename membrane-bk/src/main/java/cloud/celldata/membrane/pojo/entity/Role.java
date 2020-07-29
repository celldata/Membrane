package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 角色实体
 */
public class Role {

	// 角色id
    private Integer id;

	// 平台id
    private Integer clientId;

	// 角色名称
    private String roleName;

	// 是否逻辑删除：0已删除，1未删除
    private Integer enableFlag;

	// 创建者id
    private Integer creatorId;

	// 创建时间
    private Date createTime;

	// 更新者id
    private Integer updaterId;

	// 更新时间
    private Date updateTime;

    //是否为全部功能权限: 1代表全部权限 非1代表部分权限
    private Integer isAllApi;

    //是否为全部数据权限: 1代表全部权限 非1代表部分权限
    private Integer isAllData;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsAllApi() {
		return isAllApi;
	}

	public void setIsAllApi(Integer isAllApi) {
		this.isAllApi = isAllApi;
	}

	public Integer getIsAllData() {
		return isAllData;
	}

	public void setIsAllData(Integer isAllData) {
		this.isAllData = isAllData;
	}

	public static final class RoleBuilder {
		// 角色id
		private Integer id;
		// 平台id
		private Integer clientId;
		// 角色名称
		private String roleName;
		// 是否逻辑删除：0已删除，1未删除
		private Integer enableFlag;
		// 创建者id
		private Integer creatorId;
		// 创建时间
		private Date createTime;
		// 更新者id
		private Integer updaterId;
		// 跟新时间
		private Date updateTime;

        //是否为全部权限:1代表全部权限 非1代表部分权限
        private Integer isAllApi;

		private RoleBuilder() {
		}

		public static RoleBuilder aRole() {
			return new RoleBuilder();
		}

		public RoleBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public RoleBuilder withClientId(Integer clientId) {
			this.clientId = clientId;
			return this;
		}

		public RoleBuilder withRoleName(String roleName) {
			this.roleName = roleName;
			return this;
		}

		public RoleBuilder withEnableFlag(Integer enableFlag) {
			this.enableFlag = enableFlag;
			return this;
		}

		public RoleBuilder withCreatorId(Integer creatorId) {
			this.creatorId = creatorId;
			return this;
		}

		public RoleBuilder withCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public RoleBuilder withUpdaterId(Integer updaterId) {
			this.updaterId = updaterId;
			return this;
		}

		public RoleBuilder withUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			return this;
		}

        public RoleBuilder withIsAllApi(Integer isAllApi) {
            this.isAllApi = isAllApi;
            return this;
        }

		public Role build() {
			Role role = new Role();
			role.setId(id);
			role.setClientId(clientId);
			role.setRoleName(roleName);
			role.setEnableFlag(enableFlag);
			role.setCreatorId(creatorId);
			role.setCreateTime(createTime);
			role.setUpdaterId(updaterId);
			role.setUpdateTime(updateTime);
            role.setIsAllApi(isAllApi);
			return role;
		}
	}

}
