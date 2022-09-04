package org.mryao.ucas.controller;

import lombok.extern.slf4j.Slf4j;
import org.mryao.ucas.service.SepService;
import org.mryao.ucas.view.UserAuthorizationRequest;
import org.mryao.ucas.view.UserAuthorizationResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sep")
@Validated
@Slf4j
public class SepController {

    private final SepService sepService;

    public SepController(SepService sepService) {
        this.sepService = sepService;
    }

    @GetMapping("/users")
    public UserAuthorizationResponse userGrantAuthorization(@RequestParam String code) {
        return sepService.userGrantAuthorization(code);
    }

    @PostMapping("/users")
    public UserAuthorizationResponse userGrantAuthorization(@RequestBody UserAuthorizationRequest request) {
        return sepService.userGrantAuthorization(request);
    }
}
