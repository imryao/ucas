package org.mryao.ucas.service.impl;

import com.google.crypto.tink.KeysetHandle;
import lombok.extern.slf4j.Slf4j;
import org.mryao.ucas.feign.client.ScholarinFeignClient;
import org.mryao.ucas.service.SepService;
import org.mryao.ucas.service.UcasService;
import org.mryao.ucas.util.CryptoUtil;
import org.mryao.ucas.util.JacksonUtil;
import org.mryao.ucas.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author mryao
 */
@Service
@Slf4j
public class SepServiceImpl implements SepService {

    private static final String PUBLIC_KEY_STR = "{\"primaryKeyId\":2048429499,\"key\":[{\"keyData\":{\"typeUrl\":\"type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey\",\"value\":\"EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYAhohALR4YrIhlkrTqQaEIeyyS1k6kNuXzS1H5fsk91wy7sOrIiEAw6mkQnwwQLcxZ8CdIQbp6L6qe/co8fmKU0dL0Ag9UPI=\",\"keyMaterialType\":\"ASYMMETRIC_PUBLIC\"},\"status\":\"ENABLED\",\"keyId\":2048429499,\"outputPrefixType\":\"RAW\"}]}";

    @Autowired
    private ScholarinFeignClient scholarinFeignClient;

    @Autowired
    private UcasService ucasService;

    private static final long START_TIME = 1484280023;

    @Override
    public UserAuthorizationResponse userGrantAuthorization(String code) {
        UcasToken meToken = ucasService.meLogin(code);
        UcasToken workflowToken = ucasService.workflowLogin(code);
        ucasService.workflowGetName(workflowToken);
        ucasService.meGetAllInfo(meToken);

        return null;
    }

    @Override
    public UserAuthorizationResponse userGrantAuthorization(UserAuthorizationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        SepLoginRequest loginRequest = new SepLoginRequest(email, password);
        String code = scholarinFeignClient.login(loginRequest);
        if (StringUtils.hasText(code)) {
//            UcasToken ucasToken = ucasService.appLogin(email, password);


            UserAuthorizationResponse response = userGrantAuthorization(code);
            String ucasId = response.getUcasId();
            saveSecret(ucasId, password);
            return response;
        }
        return null;
    }

    private void saveSecret(String ucasId, String password) {
        Watermark watermark = new Watermark(ucasId);
        byte[] watermarkBytes = JacksonUtil.writeJsonValueAsBytes(watermark);
        String watermarkStr = Base64.getEncoder().encodeToString(watermarkBytes);

        KeysetHandle publicKeysetHandle = CryptoUtil.getKeysetHandle(PUBLIC_KEY_STR);
        byte[] ciphertext = CryptoUtil.eciesEncrypt(publicKeysetHandle, password.getBytes(StandardCharsets.UTF_8), watermarkBytes);
        String ciphertextStr = Base64.getEncoder().encodeToString(ciphertext);

        log.info("{} {}", watermarkStr, ciphertextStr);
    }
}
