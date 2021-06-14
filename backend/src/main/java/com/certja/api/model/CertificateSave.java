package com.certja.api.model;

import java.security.cert.Certificate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.certja.api.serializer.CertificateSaveSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name = "certificate_save")
@Table(name = "certificate_save")
@EntityListeners(AuditingEntityListener.class)
@JsonSerialize(using = CertificateSaveSerializer.class)
public class CertificateSave {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "holder_name", nullable = false)
	private String holderName;

	@Column(name = "initial_date", nullable = false)
	private Date initialDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "certificate", nullable = false)
	private Certificate certificate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
}