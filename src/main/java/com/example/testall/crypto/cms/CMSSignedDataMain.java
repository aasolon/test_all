package com.example.testall.crypto.cms;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CMSSignedDataMain {

    public static void main(String[] args) throws Exception {
        getInfoFromCMS();
//        signDateWithCert();
    }

    private static void getInfoFromCMS() throws CMSException {
        String base64sign = "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";
        byte[] sign = Base64.decodeBase64(base64sign);
        CMSSignedData cmsSignedData = new CMSSignedData(sign);
        Collection<SignerInformation> signers = cmsSignedData.getSignerInfos().getSigners();
        for (SignerInformation signer : signers) {
            String certIssuer = signer.getSID().getIssuer().toString();
            String certSerialNumber = signer.getSID().getSerialNumber().toString(16);;
            System.out.printf("Signer info:\nIssuer: %s\nSerial Number: %s\n%n", certIssuer, certSerialNumber);
        }
        int i = 0;
    }

    private static void signDateWithCert() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory certFactory= CertificateFactory
                .getInstance("X.509", "BC");

        X509Certificate certificate = (X509Certificate) certFactory
                .generateCertificate(new FileInputStream("Baeldung.cer"));

        char[] keystorePassword = "password".toCharArray();
        char[] keyPassword = "password".toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("Baeldung.p12"), keystorePassword);
        PrivateKey key = (PrivateKey) keystore.getKey("baeldung", keyPassword);

        CMSSignedData cmsSignedData = signData(new byte[]{1}, certificate, key);
        String base64sign = new String(Base64.encodeBase64(cmsSignedData.getEncoded()));
        Collection<SignerInformation> signers = cmsSignedData.getSignerInfos().getSigners();
        for (SignerInformation signer : signers) {
            SignerId sid = signer.getSID();
            X500Name issuer = sid.getIssuer();
            BigInteger serialNumber = sid.getSerialNumber();
            int i = 0;
        }

        int i = 0;
    }

    public static CMSSignedData signData(
            byte[] data,
            X509Certificate signingCertificate,
            PrivateKey signingKey) throws Exception {

        byte[] signedMessage = null;
        List<X509Certificate> certList = new ArrayList<>();
        CMSTypedData cmsData= new CMSProcessableByteArray(data);
        certList.add(signingCertificate);
        Store certs = new JcaCertStore(certList);

        CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
        ContentSigner contentSigner
                = new JcaContentSignerBuilder("SHA256withRSA").build(signingKey);
        cmsGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC")
                        .build()).build(contentSigner, signingCertificate));
        cmsGenerator.addCertificates(certs);

        CMSSignedData cms = cmsGenerator.generate(cmsData, true);
        return cms;
    }
}
