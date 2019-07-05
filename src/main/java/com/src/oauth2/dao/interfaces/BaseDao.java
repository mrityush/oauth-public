package com.src.oauth2.dao.interfaces;


import com.src.oauth2.domain.entity.BaseEntity;
import com.src.oauth2.searchfilter.BaseItemFilter;

import java.util.List;

/**
 * Created by mj on 25/5/16.
 */
public interface BaseDao<T extends BaseEntity, U extends BaseItemFilter<T>> {
	List<T> findItems(U u);

	void save(T t);

	void update(T t);

	T get(Long id);

	List<T> findAll();

	long count();
}
