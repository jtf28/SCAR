package scar.pc;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;  //import bouncy castle providor

public class PCEncrypt extends scar.Encryption {

  static {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  //set bouncy castle as provider
  }

  public PCEncrypt()  //constructor
  {
    super();
  }

  Cipher cipher;	//cipher to encrypt/decrypt

  public byte[] encrypt(byte[] data, String stringOfKey)  //takes the original data and transforms it into cipher text
  {
    try
      {
        //set up
        SecretKey AESkey = getAESKey(stringOfKey);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC"); //set up cipher for AES
        cipher.init(Cipher.ENCRYPT_MODE, AESkey, new IvParameterSpec( stringOfKey.substring(0, 16).getBytes() ) );    //set the cipher as encrypt mode with the key NOTE: we can figure out a better IV later

        byte[] cipherText = cipher.doFinal(data);   //perform encryption
        return cipherText;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
  }

  public byte[] decrypt(byte[] cipherText, String stringOfKey)    //takes the cipherText and returns the original data, NOTE: same as encrypt method
  {
    try
      {
        //set up
        SecretKey AESkey = getAESKey(stringOfKey);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC"); //set up cipher for AES
        cipher.init(Cipher.DECRYPT_MODE, AESkey, new IvParameterSpec( stringOfKey.substring(0, 16).getBytes() ) );    //set the cipher as encrypt mode with the key NOTE: we can figure out a better IV later

        byte[] plainText = cipher.doFinal(cipherText);  //perform decryption
        return plainText;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
  }

  private SecretKey getAESKey( String stringOfKey )  throws NoSuchAlgorithmException  //takes the stringOfKey and transforms it into a usuable 256-bit AES key, NOTE: From reading online, I am supposed to through this exception
  {
    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");   //used to hash the key to get 256-bit sha
    SecretKey AESkey;	//AES key used for cipher
    sha256.update( stringOfKey.getBytes() );   //perform the hash on the stringOfKey
    AESkey = new SecretKeySpec( sha256.digest(), "AES" );  //creates AESkey from the stringOfKey
    return AESkey;
  }
}
