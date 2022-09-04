package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxSendTemplateMessageResponse extends WxBaseResponse {

    @JsonProperty("msgid")
    private Long msgId;
}
