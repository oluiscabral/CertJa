package com.certja.api.dao;

import java.util.Date;
import java.util.List;

import com.certja.api.model.CertificateSave;

public interface ICertificateSaveDAO {

	List<CertificateSave> findAllCertificateSaves();

	List<CertificateSave> findCertificateSaves(String holderName, Date initialDate, Date endDate);

	List<CertificateSave> findCertificateSavesByHolderName(String holderName);

	List<CertificateSave> findCertificateSavesByInitialDate(Date initialDate);

	List<CertificateSave> findCertificateSavesByInitialDate(String holderName, Date initialDate);

	List<CertificateSave> findCertificateSavesByEndDate(Date endDate);

	List<CertificateSave> findCertificateSavesByEndDate(String holderName, Date endDate);

	CertificateSave findCertificateSaveById(long id);

	CertificateSave saveCertificateSave(CertificateSave certificateSave);

	long deleteCertificateSaveById(long id);
}