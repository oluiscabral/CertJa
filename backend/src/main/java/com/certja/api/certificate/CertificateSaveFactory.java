package com.certja.api.certificate;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
		Date[] validity = new Date[2];
		List<String> validityRaw = (List) request.get("validity");
		for (int i = 0; i < 2; i++) {
			String dateRaw = validityRaw.get(i);
			Date dateParsed = dateFormatter.parse(dateRaw);
			validity[i] = dateParsed;
		}
		if (validity[0].getTime() > validity[1].getTime()) {
			throw new Exception("Invalid validity: initial date is after end date.");
		}
		return validity;
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