package org.mryao.ucas.feign.client;

import org.mryao.ucas.view.UcasResponse;
import org.mryao.ucas.view.WorkflowGetNameResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author mryao
 */
@FeignClient(name = "workflow", url = "https://ehall.ucas.ac.cn")
public interface WorkflowFeignClient {

    @GetMapping("/site/user/get-name")
    UcasResponse<WorkflowGetNameResponse> getName(@RequestHeader(HttpHeaders.COOKIE) String cookie);
}
