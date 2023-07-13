package com.docManageSystem.QDBChallnge.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docManageSystem.QDBChallnge.entity.Comment;
import com.docManageSystem.QDBChallnge.entity.DocumentWithMetadata;
import com.docManageSystem.QDBChallnge.entity.Post;
import com.docManageSystem.QDBChallnge.repo.FeignPost;
import com.docManageSystem.QDBChallnge.service.DocumentService;
import com.docManageSystem.QDBChallnge.service.MyRetryer;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@RestController
public class FileUploadController {
	@Autowired
	public DocumentService documentService;
	
//	@ResponseStatus(value = HttpStatus.OK)
//	@PostMapping("/upload")
//	public HttpStatus uploadImage(@RequestParam("pdfFile")MultipartFile file) throws IOException{
//		if(!file.getOriginalFilename().contains(".pdf")) {
//			return HttpStatus.NOT_ACCEPTABLE;
//		}
//		documentService.uploadDocument(file);
//		return HttpStatus.CREATED;
//	}
	@PostMapping("/upload")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> uploadImage(@RequestParam("pdfFile")MultipartFile file,@RequestParam("userId")String userId, @RequestParam("userName")String userName
			,@RequestParam("library")String library,@RequestParam("description")String description) throws IOException{
		try {
		
		if(!file.getOriginalFilename().contains(".pdf")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only PDF file is allowed");
		}
		DocumentWithMetadata dWMD = new DocumentWithMetadata();
		dWMD.setUserId(userId);
		dWMD.setUserName(userName);
		dWMD.setLibrary(library);
		dWMD.setDescription(description);
		DocumentWithMetadata dWMDfromDB = documentService.uploadDocument(file,dWMD);
		
		FeignPost client = Feign.builder()
	                .encoder(new GsonEncoder())
	                .decoder(new GsonDecoder())
	                .retryer(new MyRetryer(100,3))
//	                .target(FeignPost.class, "https://my-json-server.typicode.com");
	                .target(FeignPost.class, "http://localhost:3000");

//		DocumentWithMetadata data = new DocumentWithMetadata();
	        // Set the necessary data properties
		
	    client.postData(dWMDfromDB.getdId(),dWMDfromDB.getNameOfDocument());
	    
	 
		System.out.println(file.getOriginalFilename());
		return ResponseEntity.status(HttpStatus.CREATED).body("Entry is saved in DB");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	@GetMapping("/download/{fileName}")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		try {
			byte[] pdf = documentService.downloadDocument(fileName);
			return ResponseEntity.status(HttpStatus.OK).body(pdf);
			
		}catch(NoSuchElementException ex) {
			 ex.printStackTrace();
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Entry is not found in DB");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	@DeleteMapping("/delete/{docName}")
	public ResponseEntity<?>  deleteDocumentByName(@PathVariable String docName) {
		try {
			if(documentService.deleteDocument(docName)) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Entry is deleted in DB");
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Entry is not found in DB");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	@GetMapping("/getAllDocs/{userName}")
	public ResponseEntity<?> getAlldocByUserName(@PathVariable String userName){
		try {
			Set<Optional<DocumentWithMetadata>> listofDocForUser = documentService.getListofDocForUser(userName);
			if(listofDocForUser.size()<=0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Entry is not found in DB");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body(listofDocForUser);
			}
		}  catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	@GetMapping("/getAllPosts")
	public Set<Post> getAllposts(){

		try {
			FeignPost client = Feign.builder()
	                .encoder(new GsonEncoder())
	                .decoder(new GsonDecoder())
	                .retryer(new MyRetryer(100,3))
//	                .target(FeignPost.class, "https://my-json-server.typicode.com");
	                .target(FeignPost.class, "http://localhost:3000");
		
		Set<Post> setPost = client.getPosts();
		return setPost;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/createComment/{pId}/{body}")
	public void postComment(@PathVariable("pId") Long pId,@PathVariable("body") String body) {

		try {
			FeignPost client = Feign.builder()
	                .encoder(new GsonEncoder())
	                .decoder(new GsonDecoder())
	                .retryer(new MyRetryer(100,3))
//	                .target(FeignPost.class, "https://my-json-server.typicode.com");
	                .target(FeignPost.class, "http://localhost:3000");
			client.postComment(body, pId);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	@GetMapping("/getAllComments/{pId}")
	public Set<Comment> getAllComments(@PathVariable("pId") Long pId){

		try {
			FeignPost client = Feign.builder()
	                .encoder(new GsonEncoder())
	                .decoder(new GsonDecoder())
	                .retryer(new MyRetryer(100,3))
//	                .target(FeignPost.class, "https://my-json-server.typicode.com");
	                .target(FeignPost.class, "http://localhost:3000");
		
		Set<Comment> setComment = client.getCommentsPerPost(pId);
		return setComment;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
