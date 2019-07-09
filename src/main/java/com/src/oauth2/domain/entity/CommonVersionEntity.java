package com.src.oauth2.domain.entity;

import com.src.oauth2.constants.ApplicationConstant;
import com.src.oauth2.utils.DateTimeUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bijoy on 24/5/16.
 */
@MappedSuperclass
public abstract class CommonVersionEntity implements BaseEntity {
	@Transient
	public static final String DATE_CREATED = "dateCreated";
	@Transient
	public static final String LAST_UPDATED = "lastUpdated";

	@CreatedDate
	@Column(nullable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(nullable = true)
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@Column
	private Long createdBy;

	@Column
	private Long updatedBy;

	public CommonVersionEntity() {
		setDateCreated(DateTimeUtils.getCurrentDate());
		setCreatedBy(ApplicationConstant.SYSTEM_ID);
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(nullable = false)
	@Version
	private Long version;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreate) {
		this.dateCreated = dateCreate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
