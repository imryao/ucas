package org.mryao.ucas.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

@Slf4j
public class MeFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        template
                .header(HttpHeaders.REFERER, "https://me.ucas.ac.cn/page/m_backHome/m_index");
    }
}
