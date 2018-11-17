import java.io.*;
import java.security.*;


public class GenSig {
	
	/* Variables to save the args */
	private static String fileName = "sum.check";
	private static String ksName = "keystore";
	private static String ksAlias = "hamid";
	private static char[] ksPass = "password".toCharArray();
	private static char[] pkPass = "password".toCharArray();
	
	public static void generate() {
		
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			FileInputStream ksfis = new FileInputStream(ksName);
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
			
			ks.load(ksbufin, ksPass);
			PrivateKey priv = (PrivateKey) ks.getKey(ksAlias, pkPass);
			
			/* Get a Signature Object and Initialize the Signature Object with the private key */
			Signature dsa = Signature.getInstance("SHA1withRSA");
			dsa.initSign(priv);
			
			/* Supply the Signature Object the Data to Be Signed */
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bufin = new BufferedInputStream(fis);
			byte[] buffer = new byte[1024];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				dsa.update(buffer, 0, len);
			}
			
			bufin.close();
			
			/* Generate the signature */
			byte[] realSign = dsa.sign();
			
			/* Save the Signature */
			FileOutputStream sigfos = new FileOutputStream("sig");
			sigfos.write(realSign);
			sigfos.close();
			
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
	}

}
