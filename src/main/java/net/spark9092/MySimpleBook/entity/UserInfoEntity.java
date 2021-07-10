package net.spark9092.MySimpleBook.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	private String password;
	private LocalDateTime lastLoginTime;
	private boolean isActive;
	private boolean isDelete;
	private LocalDateTime createDateTime;
	private int createUserId;
	private LocalDateTime modifyDateTime;
	private int modifyUserId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public LocalDateTime getModifyDateTime() {
		return modifyDateTime;
	}
	public void setModifyDateTime(LocalDateTime modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}
	public int getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(int modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	
	@Override
	public String toString() {
		return String.format(
				"UserInfo [id=%s, userName=%s, password=%s, lastLoginTime=%s, isActive=%s, isDelete=%s, createDateTime=%s, createUserId=%s, modifyDateTime=%s, modifyUserId=%s]",
				id, userName, password, lastLoginTime, isActive, isDelete, createDateTime, createUserId, modifyDateTime,
				modifyUserId);
	}
}
