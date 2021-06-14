package com.certja.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certja.api.model.CertificateSave;

@Repository
public interface CertificateSaveRepository extends JpaRepository<CertificateSave, Long> {
}
