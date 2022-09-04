package org.mryao.ucas.util;

import com.google.crypto.tink.*;
import com.google.crypto.tink.hybrid.HybridConfig;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author mryao
 */
@Slf4j
public class CryptoUtil {

    private static final String PRIVATE_KEY_STR = "{\"primaryKeyId\":2048429499,\"key\":[{\"keyData\":{\"typeUrl\":\"type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey\",\"value\":\"EowBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYAhohALR4YrIhlkrTqQaEIeyyS1k6kNuXzS1H5fsk91wy7sOrIiEAw6mkQnwwQLcxZ8CdIQbp6L6qe/co8fmKU0dL0Ag9UPIaIQCuY+XOByNIvrR9c49/MjpDcxVCq7HyxCAwaujzkJEWKA==\",\"keyMaterialType\":\"ASYMMETRIC_PRIVATE\"},\"status\":\"ENABLED\",\"keyId\":2048429499,\"outputPrefixType\":\"RAW\"}]}";

    private static final String PUBLIC_KEY_STR = "{\"primaryKeyId\":2048429499,\"key\":[{\"keyData\":{\"typeUrl\":\"type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey\",\"value\":\"EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYAhohALR4YrIhlkrTqQaEIeyyS1k6kNuXzS1H5fsk91wy7sOrIiEAw6mkQnwwQLcxZ8CdIQbp6L6qe/co8fmKU0dL0Ag9UPI=\",\"keyMaterialType\":\"ASYMMETRIC_PUBLIC\"},\"status\":\"ENABLED\",\"keyId\":2048429499,\"outputPrefixType\":\"RAW\"}]}";

    public static String base64encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static KeysetHandle getKeysetHandle(String input) {
        try {
            return CleartextKeysetHandle.read(JsonKeysetReader.withString(input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] eciesEncrypt(KeysetHandle publicKeysetHandle, byte[] plaintext, byte[] contextInfo) {
        try {
            HybridEncrypt hybridEncrypt = publicKeysetHandle.getPrimitive(HybridEncrypt.class);
            return hybridEncrypt.encrypt(plaintext, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] eciesDecrypt(KeysetHandle privateKeysetHandle, byte[] ciphertext, byte[] contextInfo) {
        try {
            HybridDecrypt hybridDecrypt = privateKeysetHandle.getPrimitive(HybridDecrypt.class);
            return hybridDecrypt.decrypt(ciphertext, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        HybridConfig.register();

//        KeysetHandle privateKeysetHandle = KeysetHandle.generateNew(KeyTemplates.get("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM_RAW"));
//        ByteArrayOutputStream privateKeyByteArrayOutputStream = new ByteArrayOutputStream();
//        CleartextKeysetHandle.write(privateKeysetHandle, JsonKeysetWriter.withOutputStream(privateKeyByteArrayOutputStream));
//        String privateKeyStr = privateKeyByteArrayOutputStream.toString();
//        log.info("P {}", privateKeyStr);
//
//        KeysetHandle publicKeysetHandle = privateKeysetHandle.getPublicKeysetHandle();
//        ByteArrayOutputStream publicKeyByteArrayOutputStream = new ByteArrayOutputStream();
//        CleartextKeysetHandle.write(publicKeysetHandle, JsonKeysetWriter.withOutputStream(publicKeyByteArrayOutputStream));
//        String publicKeyStr = publicKeyByteArrayOutputStream.toString();
//        log.info("R {}", publicKeyStr);

        KeysetHandle privateKeysetHandle = getKeysetHandle(PRIVATE_KEY_STR);
        KeysetHandle publicKeysetHandle = getKeysetHandle(PUBLIC_KEY_STR);

        byte[] ciphertext = eciesEncrypt(publicKeysetHandle, "0123456789012345".getBytes(StandardCharsets.UTF_8), "777".getBytes(StandardCharsets.UTF_8));
        log.info("ciphertext {}", Base64.getEncoder().encodeToString(ciphertext));
        byte[] plaintext = eciesDecrypt(privateKeysetHandle, ciphertext, "777".getBytes(StandardCharsets.UTF_8));
        log.info("plaintext {}", new String(plaintext));
    }
}
