package org.mryao.ucas.feign.client;

import org.mryao.ucas.view.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wx-callback", url = "http://wx-callback:8082")
public interface WxCallbackFeignClient {

    @GetMapping("/api/clients/{clientId}/token")
    TokenResponse getClientToken(@PathVariable String clientId);
}
