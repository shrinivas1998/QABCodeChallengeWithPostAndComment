package com.docManageSystem.QDBChallnge.entity;

public class Comment {
   public String body;
   public Long postId;
   public Long id;
public String getBody() {
	return body;
}
public void setBody(String body) {
	this.body = body;
}
public Long getPostId() {
	return postId;
}
public void setPostId(Long postId) {
	this.postId = postId;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
@Override
public String toString() {
	return "Comment [body=" + body + ", postId=" + postId + ", id=" + id + "]";
}
   
}
