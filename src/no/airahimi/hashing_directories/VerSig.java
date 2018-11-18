package no.airahimi.hashing_directories;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class VerSig {
	
	/* Variables */
	private static String ksName = "keystore";
	private static String ksAlias = "hamid";
	private static char[] ksPass = "password".toCharArray();
	private static String sigFile = "sig";
	private static String dataFile = "sum.check";
	
	public static boolean verify() {
		boolean verified = false;
		try {
			FileInputStream fis = new FileInputStream(ksName);
			KeyStore ks = KeyStore.getInstance("JKS");
			BufferedInputStream ksfis = new BufferedInputStream(fis);
			
			ks.load(ksfis, ksPass);
			
			Certificate cert = ks.getCertificate(ksAlias);
			PublicKey pubKey = cert.getPublicKey();
			
			/* input the Signature bytes from the file */
			FileInputStream sigfis = new FileInputStream(sigFile);
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();
			
			/* Get a Signature Object and Initialize the Signature Object with the public key */
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(pubKey);
			
			/* input and verify the data */
			FileInputStream datafis = new FileInputStream(dataFile);
			BufferedInputStream bufin = new BufferedInputStream(datafis);
			byte[] buffer = new byte[1024];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sig.update(buffer, 0, len);
			}
			bufin.close();
			
			/* print out result of verification */
			verified = sig.verify(sigToVerify);
			
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
		return verified;
	}

}
