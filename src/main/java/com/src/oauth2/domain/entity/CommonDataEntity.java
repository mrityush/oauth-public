package com.src.oauth2.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;


@MappedSuperclass
public abstract class CommonDataEntity extends CommonVersionEntity {

	public CommonDataEntity() {
		this.setDateCreated(Calendar.getInstance().getTime());
		this.setLastUpdated(Calendar.getInstance().getTime());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
