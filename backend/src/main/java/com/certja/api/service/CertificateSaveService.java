package com.certja.api.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.certja.api.dao.CertificateSaveDAO;
import com.certja.api.model.CertificateSave;

@Service
public class CertificateSaveService implements ICertificateSaveService {

	private CertificateSaveDAO certificateSaveDAO;

	@Autowired
	public CertificateSaveService(@Qualifier("certificateSaveDAO") CertificateSaveDAO certificateSaveDAO) {
		this.certificateSaveDAO = certificateSaveDAO;
	}

	@Override
	@Transactional
	public List<CertificateSave> findAllCertificateSaves() {
		return certificateSaveDAO.findAllCertificateSaves();
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSaves(String holderName, Date initialDate, Date endDate) {
		return certificateSaveDAO.findCertificateSaves(holderName, initialDate, endDate);
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSavesByHolderName(String holderName) {
		return certificateSaveDAO.findCertificateSavesByHolderName(holderName);
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSavesByInitialDate(Date initialDate) {
		return certificateSaveDAO.findCertificateSavesByInitialDate(initialDate);
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSavesByInitialDate(String holderName, Date initialDate) {
		return certificateSaveDAO.findCertificateSavesByInitialDate(holderName, initialDate);
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSavesByEndDate(Date endDate) {
		return certificateSaveDAO.findCertificateSavesByEndDate(endDate);
	}

	@Override
	@Transactional
	public List<CertificateSave> findCertificateSavesByEndDate(String holderName, Date endDate) {
		return certificateSaveDAO.findCertificateSavesByEndDate(holderName, endDate);
	}

	@Override
	@Transactional
	public CertificateSave findCertificateSaveById(long id) {
		return certificateSaveDAO.findCertificateSaveById(id);
	}

	@Override
	@Transactional
	public CertificateSave saveCertificateSave(CertificateSave certificateSave) {
		return certificateSaveDAO.saveCertificateSave(certificateSave);
	}

	@Override
	@Transactional
	public long deleteCertificateSaveById(long id) {
		return certificateSaveDAO.deleteCertificateSaveById(id);
	}

	@Override
	public List<CertificateSave> findCertificateSavesByDate(Date initialDate, Date endDate) {
		return certificateSaveDAO.findCertificateSavesByDate(initialDate, endDate);
	}
}
