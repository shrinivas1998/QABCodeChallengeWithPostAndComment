package com.docManageSystem.QDBChallnge.entity;

public class Post {
	public Long id;
	public String docName;
	public Long getdId() {
		return id;
	}
	public void setdId(Long id) {
		this.id = id;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	@Override
	public String toString() {
		return "Post [dId=" + id + ", docName=" + docName + "]";
	}
	
	
}
