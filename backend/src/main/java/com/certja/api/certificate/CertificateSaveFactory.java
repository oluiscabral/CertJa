package com.certja.api.certificate;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.operator.OperatorCreationException;

import com.certja.api.model.CertificateSave;

public class CertificateSaveFactory {

	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	private final CertificateFactory certificateFactory;

	public CertificateSaveFactory() throws Exception {
		certificateFactory = new CertificateFactory();
	}

	public CertificateSave create(LinkedHashMap request) throws Exception {
		String holderName = getHolderName(request);
		Date[] validity = getValidity(request);
		Certificate certificate = createCertificate(validity);
		return buildCertificateSave(holderName, validity, certificate);
	}

	private String getHolderName(LinkedHashMap request) {
		return (String) request.get("holder_name");
	}

	private Date[] getValidity(LinkedHashMap request) throws Exception {
		Date initialDateParsed = dateFormatter.parse((String) request.get("initial_date"));
		Date endDateParsed = dateFormatter.parse((String) request.get("end_date"));
		return new Date[] { initialDateParsed, endDateParsed };
	}

	private Certificate createCertificate(Date[] validity)
			throws OperatorCreationException, CertIOException, CertificateException {
		return certificateFactory.create(validity);
	}

	private CertificateSave buildCertificateSave(String holderName, Date[] validity, Certificate certificate) {
		CertificateSave certificateSave = new CertificateSave();
		certificateSave.setHolderName(holderName);
		certificateSave.setInitialDate(validity[0]);
		certificateSave.setEndDate(validity[1]);
		certificateSave.setCertificate(certificate);
		return certificateSave;
	}
}