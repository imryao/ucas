package org.mryao.ucas.feign.config;

import org.mryao.ucas.feign.codec.ScholarinFeignResponseDecoder;
import org.mryao.ucas.feign.interceptor.ScholarinFeignRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author mryao
 */
public class ScholarinFeignClientConfig {

    @Bean
    public ScholarinFeignRequestInterceptor scholarinFeignRequestInterceptor() {
        return new ScholarinFeignRequestInterceptor();
    }

    @Bean
    public ScholarinFeignResponseDecoder scholarinFeignResponseDecoder() {
        return new ScholarinFeignResponseDecoder();
    }
}
