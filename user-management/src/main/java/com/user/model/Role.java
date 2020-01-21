package com.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="ROLE")
public class Role implements EaoEntity {
	private static final long serialVersionUID = 5071529250059322904L;
	@Id
	@GeneratedValue(generator = "role-generator")
    @GenericGenerator(name = "role-generator", 
      parameters = @Parameter(name = "prefix", value = "role"), 
      strategy = "com.user.model.EntityIdGenerator")
	@Column(name = "ID")
	private String id;
	
	@Column(name="NAME")
	private String name;	
	
	@Column(name="IS_DEFAULT")
	private boolean isDefault;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", isDefault=" + isDefault + "]";
	}
	
	
}
