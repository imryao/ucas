package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author mryao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxBaseResponse {

    @JsonProperty("errcode")
    private Integer errorCode;

    @JsonProperty("errmsg")
    private String errorMessage;
}
