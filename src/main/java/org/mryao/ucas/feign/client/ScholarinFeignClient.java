package org.mryao.ucas.feign.client;

import org.mryao.ucas.feign.config.ScholarinFeignClientConfig;
import org.mryao.ucas.view.SepLoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author mryao
 */
@FeignClient(name = "scholarin", url = "https://scholarin.cn", configuration = ScholarinFeignClientConfig.class)
public interface ScholarinFeignClient {

    @PostMapping("/hky/oauth/signin/ucas_account")
    String login(@RequestBody SepLoginRequest request);
}
