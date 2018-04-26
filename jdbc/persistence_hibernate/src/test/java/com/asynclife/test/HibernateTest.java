package com.asynclife.test;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asynclife.entity.Category;
import com.asynclife.entity.Product;

public class HibernateTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static SessionFactory sessionFactory;  
	
	@BeforeClass
	public static void beforeClass() {
		sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory(); 
	}
	
	public static void afterClass() {
		sessionFactory.close();
	}
	
	@Test
	public void testSave() {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.getTransaction().begin();
			Category c = new Category("空气净化器1", "北京空气太脏了");
			session.save(c);
			
			Product p = new Product("碧水源空气净化器1", "3500.88", "没用过");
			p.setCategory(c);
			session.save(p);
			
			session.getTransaction().commit();
			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}
		
	}

	@Test
	public void testGet() {
		Session session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		
		Category c = (Category) session.get(Category.class, 1L);
		logger.info("id: {}, name:{}", c.getId(), c.getName());
		Set<Product> p = c.getProducts();
		for (Product product : p) {
			logger.info("id:{}, name:{},description:{}", product.getId(), product.getName(), product.getDescription());
		}
		
		session.getTransaction().commit();
	}
}
