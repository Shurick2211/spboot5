package com.sn.org.spboot5.kraken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KrakenAuthMessageClient {
  public static String signMessage1(String path, String nonce, String postData, String apiPrivateKey) {
    // Algorithm: HMAC-SHA512 of (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key
      try {
        byte[] bytePath = path.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha256 = md.digest((nonce + postData).getBytes(StandardCharsets.UTF_8));
        byte[] hmacMessage = concatArrays(bytePath, sha256);
        byte[] hmacKey = Base64.getDecoder().decode(apiPrivateKey);
        Mac hmacsha512 = Mac.getInstance("HmacSHA512");
        hmacsha512.init(new SecretKeySpec(hmacKey, "HmacSHA512"));
        return Base64.getEncoder().encodeToString(hmacsha512.doFinal(hmacMessage));
      } catch (InvalidKeyException | NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
  }

  private static byte[] concatArrays(byte[] a, byte[] b) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
      outputStream.write(a);
      outputStream.write(b);
      return outputStream.toByteArray();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }


  public static String signMessage2(String endpoint, String nonce, String postData, String apiPrivateKey)
      throws NoSuchAlgorithmException, InvalidKeyException {
    // Step 1: concatenate postData, nonce + endpoint
    String message = postData + nonce + endpoint;
    // Step 2: hash the result of step 1 with SHA256
    byte[] hash = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
    // step 3: base64 decode apiPrivateKey
    byte[] secretDecoded = Base64.getDecoder().decode(apiPrivateKey);
    // step 4: use result of step 3 to hash the resultof step 2 with
    // HMAC-SHA512
    Mac hmacsha512 = Mac.getInstance("HmacSHA512");
    hmacsha512.init(new SecretKeySpec(secretDecoded, "HmacSHA512"));
    byte[] hash2 = hmacsha512.doFinal(hash);
    // step 5: base64 encode the result of step 4 and return
    return Base64.getEncoder().encodeToString(hash2);
  }

  public static String generateNonce() {
    return String.valueOf(System.currentTimeMillis() * 17);
  }
}
