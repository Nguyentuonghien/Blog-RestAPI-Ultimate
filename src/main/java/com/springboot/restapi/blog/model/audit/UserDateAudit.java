package com.springboot.restapi.blog.model.audit;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @MappedSuperclass : các lớp cha, không được là entity. Khi các entity con kế thừa entity cha -> cơ sở dữ liệu sẽ chỉ có các bảng là các entity con,
 *                     các thuộc tính của class cha sẽ nằm trong các bảng của entity con      
 * @JsonIgnoreProperties: Chú thích cấp class này có thể được sử dụng để loại trừ các thuộc tính nhất định trong quá trình 
 *                        Serialization and Deserialization dữ liệu JSON -> chúng sẽ không được ánh xạ tới nội dung JSON.                
 */

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(
		value = {"createdBy", "updatedBy"},
		allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

	private static final long serialVersionUID = 1L;
	
	@CreatedBy
	private Long createdBy;
	
	@LastModifiedBy
	private Long updatedBy;
	
}



