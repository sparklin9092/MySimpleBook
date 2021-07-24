package net.spark9092.MySimpleBook.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String userName;
	
	private String userPwd;
	
	private LocalDateTime lastLoginDateTime;
	
	private boolean isActive;
	
	private boolean isDelete;
	
	private boolean isGuest;
	
	private int guestSeq;
	
	private int createUserId;
	
	private LocalDateTime createDateTime;

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public LocalDateTime getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
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

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	public int getGuestSeq() {
		return guestSeq;
	}

	public void setGuestSeq(int guestSeq) {
		this.guestSeq = guestSeq;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public String toString() {
		return String.format(
				"UserInfoEntity [id=%s, userName=%s, lastLoginDateTime=%s, isActive=%s, isDelete=%s, isGuest=%s, guestSeq=%s, createUserId=%s, createDateTime=%s]",
				id, userName, lastLoginDateTime, isActive, isDelete, isGuest, guestSeq, createUserId, createDateTime);
	}
	
}
