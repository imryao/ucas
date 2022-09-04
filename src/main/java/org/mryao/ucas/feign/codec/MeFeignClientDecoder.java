package org.mryao.ucas.feign.codec;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class MeFeignClientDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> strings = headers.get(HttpHeaders.SET_COOKIE);
        log.info("cookies {}", strings);
        return null;
    }
}
