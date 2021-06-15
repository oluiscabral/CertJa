package com.certja.api.serializer;

import java.io.IOException;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;

import com.certja.api.model.CertificateSave;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CertificateSaveSerializer extends StdSerializer<CertificateSave> {

	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

	private static final long serialVersionUID = 4647923688335211623L;

	public CertificateSaveSerializer() {
		this(null);
	}

	public CertificateSaveSerializer(Class<CertificateSave> t) {
		super(t);
	}

	@Override
	public void serialize(CertificateSave certificateSave, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException {

		String initialDateParsed = dateFormatter.format(certificateSave.getInitialDate());
		String endDateParsed = dateFormatter.format(certificateSave.getEndDate());
		Certificate certificate = certificateSave.getCertificate();

		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", certificateSave.getId());
		jsonGenerator.writeStringField("holder_name", certificateSave.getHolderName());
		jsonGenerator.writeStringField("initial_date", initialDateParsed);
		jsonGenerator.writeStringField("end_date", endDateParsed);
		jsonGenerator.writeStringField("certificate", certificate.toString());
		jsonGenerator.writeEndObject();
	}

}
