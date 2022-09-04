package org.mryao.ucas.feign.codec;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author mryao
 */
@Slf4j
public class ScholarinFeignResponseDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String responseBody = IOUtils.toString(response.body().asInputStream());
        log.info("[scholarin] [Response] {}", responseBody);

        if (responseBody.startsWith("\"")) {
            return responseBody.replace("\"", "");
        }
        return null;
    }
}
