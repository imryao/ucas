package org.mryao.ucas.feign.client;

import org.mryao.ucas.view.WxSendTemplateMessageRequest;
import org.mryao.ucas.view.WxSendTemplateMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wx", url = "https://api.weixin.qq.com")
public interface WxFeignClient {

    @PostMapping("/cgi-bin/message/template/send")
    WxSendTemplateMessageResponse sendTemplateMessage(@RequestParam("access_token") String token,
                                                      @RequestBody WxSendTemplateMessageRequest request);
}
