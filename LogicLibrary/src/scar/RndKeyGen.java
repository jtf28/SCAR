package scar;

import org.spongycastle.crypto.prng.*;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.macs.HMac;

//Used to generate random bytes of various lengths based on keys as seed
public class RndKeyGen {
  SP800SecureRandom rnd;
  
  public RndKeyGen() {
    SP800SecureRandomBuilder rndbuild = new SP800SecureRandomBuilder();
    rnd = rndbuild.buildHMAC(new HMac(new SHA256Digest()), null, false);
  }
  
  public byte[] genBytes(int bytes) {
    byte[] ret = new byte[bytes];
    rnd.nextBytes(ret);
    return ret;
  }
  
  public byte nextByte(int max) {
    byte n = genBytes(1)[0];
    return (byte)(n % max);
  }

  public byte nextInt(int max) {
    byte[] i = genBytes(4);
    return (((i[0]        & 0x000000FF)) +
            ((i[1] << 8)  & 0x0000FF00) +
            ((i[2] << 16) & 0x00FF0000) +
            ((i[3] << 24) & 0xFF000000)) % max;
  }
  
  public void seed(byte[] seed) {
    rnd.setSeed(seed);
  }

  public void seed(long seed) {
    rnd.setSeed(seed);
  }
}
