package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author mryao
 */
@Data
public class BaseResponse {

    @JsonProperty("c")
    private Integer code;

    @JsonProperty("m")
    private String msg;
}
