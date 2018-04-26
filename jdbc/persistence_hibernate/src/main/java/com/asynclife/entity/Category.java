package com.asynclife.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="category")
public class Category {
	
	@GenericGenerator(name = "id_generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "id_generator")
	@Column(name = "id", unique = true, nullable = false)
	private long id;
	
	@Column(name = "name", length = 500)
	private String name;
	
	@Column(name = "description", length = 500)
	private String description;
	
	// mappedBy 在多的一端通过字段"category"来维护关联关系
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
	@LazyCollection(LazyCollectionOption.EXTRA) //　　LazyCollection属性设置成EXTRA指定了当如果查询数据的个数时候，只会发出一条 count(*)的语句，提高性能
	private Set<Product> products = new HashSet<Product>(0);
	
	
	public Category() {
		super();
	}

	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
}
