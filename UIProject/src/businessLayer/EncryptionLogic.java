package businessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.GeneralSecurityException;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.integration.gcpkms.GcpKmsClient;

public class EncryptionLogic {

	String keysetFilename = "predictorKeyStore.json";
	String masterKeyUri = "gcp-kms://projects/predictor-268800/locations/global/keyRings/PredictorKeyRing/cryptoKeys/appMasterKey";

	public void createKeystore() throws FileAlreadyExistsException {

		try {

			TinkConfig.register();

			AeadConfig.register();

			// Generate the key
			KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);

			//write to file - encrypted with the key in GCP KMS		    
			keysetHandle.write(JsonKeysetWriter.withFile(new File(keysetFilename)),
					new GcpKmsClient().getAead(masterKeyUri));
		}
		catch(FileAlreadyExistsException e) {
			System.out.println(e.getMessage());
			throw new FileAlreadyExistsException(keysetFilename);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public byte[] encrypt(String plainText) throws FileNotFoundException{

		try {

			TinkConfig.register();

			AeadConfig.register();

			// The keyset is encrypted with the this key in GCP KMS.
			KeysetHandle keysetHandle = KeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFilename)), 
					new GcpKmsClient().getAead(masterKeyUri));

			Aead aead = keysetHandle.getPrimitive(Aead.class);

			System.out.println(plainText);

			byte[] ciphertext = aead.encrypt(plainText.getBytes(), null);

			return ciphertext;

		}
		catch(GeneralSecurityException e) {
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}

		return null;

	}

	public String decrypt(byte[] encText) {

		try {

			// The keyset is encrypted with the this key in GCP KMS.
			KeysetHandle keysetHandle = KeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFilename)), 
					new GcpKmsClient().getAead(masterKeyUri));

			Aead aead = keysetHandle.getPrimitive(Aead.class);

			byte[] decrypted = aead.decrypt(encText, null);

			System.out.println(decrypted.toString());
		}
		catch(GeneralSecurityException e) {
			System.out.println(e.getMessage());
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;

	}




}
