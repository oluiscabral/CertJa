package com.certja.api.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certja.api.certificate.CertificateSaveFactory;
import com.certja.api.model.CertificateSave;
import com.certja.api.service.CertificateSaveService;

@RestController
@RequestMapping("/api/certificate")
@CrossOrigin(origins = "http://localhost:3000")
public class CertificateSaveController {

	private final CertificateSaveService certificateSaveService;

	private final CertificateSaveFactory certificateSaveFactory;

	@Autowired
	public CertificateSaveController(CertificateSaveService certificateSaveService) throws Exception {
		this.certificateSaveService = certificateSaveService;
		this.certificateSaveFactory = new CertificateSaveFactory();
	}

	@GetMapping(value = "/")
	public ResponseEntity<List<CertificateSave>> findAllCertificateSaves() {
		List<CertificateSave> certificateSaves = certificateSaveService.findAllCertificateSaves();
		return new ResponseEntity<List<CertificateSave>>(certificateSaves, HttpStatus.OK);
	}

	@GetMapping(value = "/find")
	public ResponseEntity<List<CertificateSave>> findCertificateSaves(
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "holder_name", required = false) String holderName,
			@RequestParam(name = "initial_date", required = false) Date initialDate,
			@RequestParam(name = "end_date", required = false) Date endDate) {
		try {
			List<CertificateSave> certificateSaves = getCertificateSaves(id, holderName, initialDate, endDate);
			return new ResponseEntity<List<CertificateSave>>(certificateSaves, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CertificateSave>>(HttpStatus.BAD_REQUEST);
		}
	}

	private List<CertificateSave> getCertificateSaves(Long id, String holderName, Date initialDate, Date endDate)
			throws Exception {
		if (id != null) {
			return Arrays.asList(new CertificateSave[] { certificateSaveService.findCertificateSaveById(id) });
		}
		if (holderName != null) {
			if (initialDate != null && endDate != null) {
				return certificateSaveService.findCertificateSaves(holderName, initialDate, endDate);
			} else if (initialDate != null) {
				return certificateSaveService.findCertificateSavesByInitialDate(holderName, initialDate);
			} else if (endDate != null) {
				return certificateSaveService.findCertificateSavesByEndDate(holderName, endDate);
			} else {
				return certificateSaveService.findCertificateSavesByHolderName(holderName);
			}
		} else if (initialDate != null && endDate != null) {
			return certificateSaveService.findCertificateSavesByDate(initialDate, endDate);
		} else if (initialDate != null) {
			return certificateSaveService.findCertificateSavesByInitialDate(initialDate);
		} else if (endDate != null) {
			return certificateSaveService.findCertificateSavesByEndDate(endDate);
		} else {
			throw new Exception("Could not find any request query parameters.");
		}
	}

	@PostMapping(value = "/create")
	public ResponseEntity<CertificateSave> createCertificateSave(@RequestBody LinkedHashMap request) {
		CertificateSave certificateSave = null;
		try {
			certificateSave = certificateSaveFactory.create(request);
			certificateSaveService.saveCertificateSave(certificateSave);
		} catch (Exception e) {
			return new ResponseEntity<CertificateSave>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CertificateSave>(certificateSave, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<Long> deleteCertificateSave(@RequestParam(value = "id") long id) {
		try {
			long deletedId = certificateSaveService.deleteCertificateSaveById(id);
			return new ResponseEntity<Long>(deletedId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
