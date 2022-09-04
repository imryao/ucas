package org.mryao.ucas.feign.client;

import org.mryao.ucas.config.FeignClientConfiguration;
import org.mryao.ucas.feign.config.MeFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "me", url = "https://me.ucas.ac.cn", configuration = {FeignClientConfiguration.class, MeFeignClientConfig.class})
public interface MeFeignClient {

    @GetMapping("/site/user/all-info")
    String getAllInfo(@RequestHeader(HttpHeaders.COOKIE) String cookie);
}
