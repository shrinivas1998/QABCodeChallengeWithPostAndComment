package com.docManageSystem.QDBChallnge.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.docManageSystem.QDBChallnge.entity.DocumentWithMetadata;

public interface DocumentWithMetadataRepo extends JpaRepository<DocumentWithMetadata, Long> {
	
	Optional<DocumentWithMetadata> findByUserName(String fileName);
	
	@Query("select dId from DocumentWithMetadata where nameOfDocument=:dName")
	Optional<Long> findByNameOfDocument(@Param("dName") String dName);
	
	
	@Query("select userId from DocumentWithMetadata where userName=:userName")
	Optional<String> findUserIdByUserName(@Param("userName") String userName);
	
//	Set<Optional<DocumentWithMetadata>> findAllByUserId(String userId);
	Set<Optional<DocumentWithMetadata>> findAllByUserName(String userName);
}
