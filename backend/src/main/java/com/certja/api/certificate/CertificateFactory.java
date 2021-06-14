package com.certja.api.certificate;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Random;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateFactory {

	private KeyPairGenerator keyPairGenerator;
	private JcaX509CertificateConverter jcaX509CertificateConverter;
	private final X500Name x500Name = new X500Name("C=Brazil, ST=Santa Catarina, L=Florianópolis, CN=CertJa");
	private final JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSA");
	private final ASN1ObjectIdentifier objectIdentifier = new ASN1ObjectIdentifier("2.5.29.19");
	private final BasicConstraints constraints = new BasicConstraints(true);

	public CertificateFactory() throws Exception {
		try {
			initializeKeyPairGenerator("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Could not initialize key pair generator.");
		}
		initializeCertificateConverter();
	}

	private void initializeKeyPairGenerator(String keyPairAlgorithm) throws NoSuchAlgorithmException {
		keyPairGenerator = KeyPairGenerator.getInstance(keyPairAlgorithm);
		keyPairGenerator.initialize(512, new SecureRandom());
	}

	private void initializeCertificateConverter() {
		BouncyCastleProvider bouncyCastleProvider = createBouncyCastleProvider();
		jcaX509CertificateConverter = new JcaX509CertificateConverter().setProvider(bouncyCastleProvider);
	}

	private BouncyCastleProvider createBouncyCastleProvider() {
		BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
		Security.addProvider(bouncyCastleProvider);
		return bouncyCastleProvider;
	}

	public Certificate create(Date[] interval) throws OperatorCreationException, CertIOException, CertificateException {
		X509CertificateHolder certificateHolder = createCertificateHolder(interval);
		return jcaX509CertificateConverter.getCertificate(certificateHolder);
	}

	private X509CertificateHolder createCertificateHolder(Date[] interval)
			throws OperatorCreationException, CertIOException {
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return createCertificateHolder(interval, keyPair);
	}

	private X509CertificateHolder createCertificateHolder(Date[] interval, KeyPair keyPair)
			throws OperatorCreationException, CertIOException {
		JcaX509v3CertificateBuilder certificateBuilder = createCertificateBuilder(interval, keyPair);
		ContentSigner contentSigner = jcaContentSignerBuilder.build(keyPair.getPrivate());
		return certificateBuilder.build(contentSigner);
	}

	private JcaX509v3CertificateBuilder createCertificateBuilder(Date[] interval, KeyPair keyPair)
			throws CertIOException {
		BigInteger serialNumber = new BigInteger(Long.toString(new Random().nextLong()));
		JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(x500Name, serialNumber,
				interval[0], interval[1], x500Name, keyPair.getPublic());
		certificateBuilder.addExtension(objectIdentifier, true, constraints);
		return certificateBuilder;
	}
}