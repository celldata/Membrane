package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 角色与功能权限的关系实体
 */
public class RoleApi {

	// 角色功能权限关系id
    private Integer id;

	// 角色id
    private Integer roleId;

	// 功能权限id
    private Integer apiId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getApiId() {
		return apiId;
	}

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
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

	public static final class RoleApiBuilder {
		// 角色功能权限关系id
		private Integer id;
		// 角色id
		private Integer roleId;
		// 功能权限id
		private Integer apiId;
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

		private RoleApiBuilder() {
		}

		public static RoleApiBuilder aRoleApi() {
			return new RoleApiBuilder();
		}

		public RoleApiBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public RoleApiBuilder withRoleId(Integer roleId) {
			this.roleId = roleId;
			return this;
		}

		public RoleApiBuilder withApiId(Integer apiId) {
			this.apiId = apiId;
			return this;
		}

		public RoleApiBuilder withEnableFlag(Integer enableFlag) {
			this.enableFlag = enableFlag;
			return this;
		}

		public RoleApiBuilder withCreatorId(Integer creatorId) {
			this.creatorId = creatorId;
			return this;
		}

		public RoleApiBuilder withCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public RoleApiBuilder withUpdaterId(Integer updaterId) {
			this.updaterId = updaterId;
			return this;
		}

		public RoleApiBuilder withUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			return this;
		}

		public RoleApi build() {
			RoleApi roleApi = new RoleApi();
			roleApi.setId(id);
			roleApi.setRoleId(roleId);
			roleApi.setApiId(apiId);
			roleApi.setEnableFlag(enableFlag);
			roleApi.setCreatorId(creatorId);
			roleApi.setCreateTime(createTime);
			roleApi.setUpdaterId(updaterId);
			roleApi.setUpdateTime(updateTime);
			return roleApi;
		}
	}

}
