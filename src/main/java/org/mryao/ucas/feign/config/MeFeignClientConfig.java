package org.mryao.ucas.feign.config;

import org.mryao.ucas.feign.interceptor.MeFeignRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class MeFeignClientConfig {

    @Bean
    public MeFeignRequestInterceptor meFeignRequestInterceptor() {
        return new MeFeignRequestInterceptor();
    }
}
