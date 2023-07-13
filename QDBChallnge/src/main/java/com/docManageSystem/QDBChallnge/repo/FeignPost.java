package com.docManageSystem.QDBChallnge.repo;

import java.util.Set;

import com.docManageSystem.QDBChallnge.entity.Comment;
import com.docManageSystem.QDBChallnge.entity.Post;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FeignPost {
	
//	@RequestLine("POST /shrinivas1998/typicode/posts")
//	@Headers("Content-Type: application/json")
//	@Body("%7B\"id\": \"{dId}\", \"docName\": \"{docName}\"%7D")
//	void postData(@Param("id") Long dId, @Param("docName") String docName);
	
	@RequestLine("POST /posts")
	@Headers("Content-Type: application/json")
	@Body("%7B\"id\": \"{dId}\", \"docName\": \"{docName}\"%7D")
	void postData(@Param("id") Long dId, @Param("docName") String docName);
	
	@RequestLine("GET /posts")
	Set<Post> getPosts();
	
	@RequestLine("GET /posts/{pId}/comments")
	Set<Comment> getCommentsPerPost(@Param("pId") Long pId);
	
//	@RequestLine("POST /shrinivas1998/typicode/posts/1/comments")
//	@Headers("Content-Type: application/json")
//	@Body("%7B\"body\": \"{body}\", \"pId\": \"{pId}\"%7D")
//	void postComment(@Param("body") String body, @Param("pId") Long pId);
	@RequestLine("POST /posts/1/comments")
	@Headers("Content-Type: application/json")
	@Body("%7B\"body\": \"{body}\", \"pId\": \"{pId}\"%7D")
	void postComment(@Param("body") String body, @Param("pId") Long pId);
}
