package org.mryao.ucas.controller;

import lombok.extern.slf4j.Slf4j;
import org.mryao.ucas.view.UserAuthorizationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mryao
 */
@RestController
@RequestMapping("/api/cstcloud")
@Slf4j
public class CstCloudController {

    @GetMapping("/users")
    public UserAuthorizationResponse userGrantAuthorization(@RequestParam String code) {
        return null;
    }
}
