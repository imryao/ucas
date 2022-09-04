package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UcasResponse<T> {

    @JsonProperty("e")
    private Integer code;

    @JsonProperty("m")
    private String msg;

    @JsonProperty("d")
    private T data;
}
