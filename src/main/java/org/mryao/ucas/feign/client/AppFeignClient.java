package org.mryao.ucas.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "app", url = "https://app.ucas.ac.cn")
public interface AppFeignClient {
}
