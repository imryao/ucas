package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxSendTemplateMessageRequest {

    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    private String url;

    @JsonProperty("miniprogram")
    private MiniProgram miniProgram;

    private Keywords data;

    @Data
    public static class MiniProgram {

        @JsonProperty("appid")
        private String clientId;

        @JsonProperty("pagepath")
        private String pagePath;
    }

    @Data
    public static class Keywords {

        private Keyword first;

        private Keyword keyword1;

        private Keyword keyword2;

        private Keyword keyword3;

        private Keyword keyword4;

        private Keyword keyword5;

        private Keyword remark;
    }

    @Data
    public static class Keyword {

        private String value;

        private String color;

        public Keyword(String value) {
            this.value = value;
        }
    }
}
