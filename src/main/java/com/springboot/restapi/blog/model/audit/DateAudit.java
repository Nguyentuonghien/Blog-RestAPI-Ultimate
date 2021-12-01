package com.springboot.restapi.blog.model.audit;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @MappedSuperclass : các lớp cha, không được là entity. Khi các entity con kế thừa entity cha -> cơ sở dữ liệu sẽ chỉ có các bảng là các entity con,
 *                     các thuộc tính của class cha sẽ nằm trong các bảng của entity con      
 * @JsonIgnoreProperties: Chú thích cấp class này có thể được sử dụng để loại trừ các thuộc tính nhất định trong quá trình 
 *                        Serialization and Deserialization dữ liệu JSON -> chúng sẽ không được ánh xạ tới nội dung JSON.                
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
		value = {"createdAt", "updatedAt"}, 
		allowGetters = true
)
public abstract class DateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@CreatedDate
	@Column(nullable = false)
	private Instant createdAt;
	
	@LastModifiedDate
	@Column(nullable = false)
	private Instant updatedAt;
	
}









