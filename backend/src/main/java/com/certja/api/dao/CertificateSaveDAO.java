package com.certja.api.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.certja.api.model.CertificateSave;

@Repository
public class CertificateSaveDAO implements ICertificateSaveDAO {

	private EntityManager entityManager;

	@Autowired
	public CertificateSaveDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<CertificateSave> findAllCertificateSaves() {
		Query query = entityManager.createQuery("FROM certificate_save");
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSaves(String holderName, Date initialDate, Date endDate) {
		Query query = entityManager.createQuery(
				"FROM certificate_save WHERE holder_name=:hn_var AND initial_date>=:init_var AND end_date<=:end_var");
		query.setParameter("hn_var", holderName);
		query.setParameter("init_var", initialDate.getTime());
		query.setParameter("end_var", endDate.getTime());
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSavesByHolderName(String holderName) {
		Query query = entityManager.createQuery("FROM certificate_save WHERE holder_name=:hn_var");
		query.setParameter("hn_var", holderName);
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSavesByInitialDate(Date initialDate) {
		Query query = entityManager.createQuery("FROM certificate_save WHERE initial_date>=:init_var");
		query.setParameter("init_var", initialDate.getTime());
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSavesByInitialDate(String holderName, Date initialDate) {
		Query query = entityManager
				.createQuery("FROM certificate_save WHERE holder_name=:hn_var AND initial_date>=:init_var");
		query.setParameter("hn_var", holderName);
		query.setParameter("init_var", initialDate.getTime());
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSavesByEndDate(Date endDate) {
		Query query = entityManager.createQuery("FROM certificate_save WHERE end_date<=:end_var");
		query.setParameter("end_var", endDate.getTime());
		return query.getResultList();
	}

	@Override
	public List<CertificateSave> findCertificateSavesByEndDate(String holderName, Date endDate) {
		Query query = entityManager
				.createQuery("FROM certificate_save WHERE holder_name=:hn_var AND end_date<=:end_var");
		query.setParameter("hn_var", holderName);
		query.setParameter("end_var", endDate.getTime());
		return query.getResultList();
	}

	@Override
	public CertificateSave findCertificateSaveById(long id) {
		CertificateSave certificateSave = entityManager.find(CertificateSave.class, id);
		return certificateSave;
	}

	@Override
	public CertificateSave saveCertificateSave(CertificateSave certificateSave) {
		CertificateSave dbCertificateSave = entityManager.merge(certificateSave);
		certificateSave.setId(dbCertificateSave.getId());
		return certificateSave;
	}

	@Override
	public long deleteCertificateSaveById(long id) {
		Query query = (Query) entityManager.createQuery("DELETE FROM certificate_save WHERE id=:id_var");
		query.setParameter("id_var", id);
		query.executeUpdate();
		return id;
	}

	public List<CertificateSave> findCertificateSavesByDate(Date initialDate, Date endDate) {
		Query query = entityManager
				.createQuery("FROM certificate_save WHERE initial_date>=:init_var AND end_date<=:end_var");
		query.setParameter("init_var", initialDate.getTime());
		query.setParameter("end_var", endDate.getTime());
		return query.getResultList();
	}
}