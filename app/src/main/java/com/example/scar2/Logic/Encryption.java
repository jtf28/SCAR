package com.example.scar2.Logic;

import java.security.*;
import javax.crypto.*;
import org.spongycastle.*;  //remove for PC
import javax.crypto.Cipher; //remove for PC
import javax.crypto.spec.SecretKeySpec;
//import org.bouncycastle.jce.provider.BouncyCastleProvider; add this for the PC, also if the bouncy castle jar is in the same directory to compile javac -cp *;(for all jars) *.java

public class Encryption {

    /*This static statement must be put into any file using spongy castle crypto so that Android knows to use the spongy castle jars*/
    static
    {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());   Remove the static portion above this and add something(possibly a constructor) that has this line of code for PC

    Cipher cipher;	//cipher to encryp/decrypt

    public byte[] encrypt(byte[] data, String stringOfKey)  //takes the original data and transforms it into cipher text
    {
        try
        {
            //set up
            SecretKey AESkey = getAESKey(stringOfKey);
            cipher = Cipher.getInstance("AES", "SC");   //set up cipher for AES
            //cipher = Cipher.getInstance("AES", "BC"); remove above line and replace with this line for PC
            cipher.init(Cipher.ENCRYPT_MODE, AESkey);    //set the cipher as encrypt mode with the key

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
            cipher = Cipher.getInstance("AES", "SC");   //set up cipher for AES
            //cipher = Cipher.getInstance("AES", "BC"); remove above line and replace with this line for PC
            cipher.init(Cipher.DECRYPT_MODE, AESkey);    //set the cipher as encrypt mode with the key

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