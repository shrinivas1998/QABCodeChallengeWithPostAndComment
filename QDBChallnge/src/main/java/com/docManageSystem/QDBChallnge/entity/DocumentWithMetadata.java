package com.docManageSystem.QDBChallnge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "document")
public class DocumentWithMetadata {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dId;
	private String userId;
	private String userName;
    private String nameOfDocument;
    private String library;
    private String description;
    @Lob	
    @Column(name = "pdfFile")
    private byte[] pdfFile;
	public Long getdId() {
		return dId;
	}
	public void setdId(Long dId) {
		this.dId = dId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNameOfDocument() {
		return nameOfDocument;
	}
	public void setNameOfDocument(String nameOfDocument) {
		this.nameOfDocument = nameOfDocument;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPdfFile() {
		return pdfFile;
	}
	public void setPdfFile(byte[] pdfFile) {
		this.pdfFile = pdfFile;
	}
	
	
}
