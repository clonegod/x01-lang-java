package clonegod.springdata.redis.service;

import clonegod.springdata.redis.domain.Cachable;


public interface IService<V extends Cachable> {

	public void put(V obj);

	public V get(V key);
	
	public void delete(V key);

}