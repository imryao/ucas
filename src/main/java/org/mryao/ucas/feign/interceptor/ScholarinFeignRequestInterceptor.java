package org.mryao.ucas.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mryao
 */
@Slf4j
public class ScholarinFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String requestBody = new String(template.body());
        log.info("[scholarin] [Request] {}", requestBody);
    }
}
