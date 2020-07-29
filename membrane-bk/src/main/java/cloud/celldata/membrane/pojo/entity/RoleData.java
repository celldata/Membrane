package cloud.celldata.membrane.pojo.entity;

import java.util.Date;

/**
 * 角色与数据权限关系实体
 */
public class RoleData {

	// 角色数据权限关系id
    private Integer id;

	// 角色id
    private Integer roleId;

	// 数据权限id
    private Integer dataId;

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

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
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

	public static final class RoleDataBuilder {
		// 角色数据权限关系id
		private Integer id;
		// 角色id
		private Integer roleId;
		// 数据权限id
		private Integer dataId;
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

		private RoleDataBuilder() {
		}

		public static RoleDataBuilder aRoleData() {
			return new RoleDataBuilder();
		}

		public RoleDataBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public RoleDataBuilder withRoleId(Integer roleId) {
			this.roleId = roleId;
			return this;
		}

		public RoleDataBuilder withDataId(Integer dataId) {
			this.dataId = dataId;
			return this;
		}

		public RoleDataBuilder withEnableFlag(Integer enableFlag) {
			this.enableFlag = enableFlag;
			return this;
		}

		public RoleDataBuilder withCreatorId(Integer creatorId) {
			this.creatorId = creatorId;
			return this;
		}

		public RoleDataBuilder withCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public RoleDataBuilder withUpdaterId(Integer updaterId) {
			this.updaterId = updaterId;
			return this;
		}

		public RoleDataBuilder withUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			return this;
		}

		public RoleData build() {
			RoleData roleData = new RoleData();
			roleData.setId(id);
			roleData.setRoleId(roleId);
			roleData.setDataId(dataId);
			roleData.setEnableFlag(enableFlag);
			roleData.setCreatorId(creatorId);
			roleData.setCreateTime(createTime);
			roleData.setUpdaterId(updaterId);
			roleData.setUpdateTime(updateTime);
			return roleData;
		}
	}

}
