package com.exostar.fileupload.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users",uniqueConstraints=@UniqueConstraint(columnNames={"id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 455202739064202185L;
	
	@Id
	@Column(name = "id")
	@NotNull
	private long id;
	
	@Column(name = "firstName")
	@NotEmpty(message = "First name is required")
	private String firstName;
	
	@Column(name = "lastName")
	@NotEmpty(message = "Last name is required")
	private String lastName;	
	
	@Column(name = "email")
	@NotEmpty(message = "Email is required")
	@Email(message="Email Must be well formed")
	private String email;
	
	
	public static User newInstance(long id,String firstName,String lastName,String email) {
	        return new User(id,firstName,lastName,email);
	}

}
