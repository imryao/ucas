package org.mryao.ucas.service;

import org.mryao.ucas.view.UserAuthorizationRequest;
import org.mryao.ucas.view.UserAuthorizationResponse;

public interface SepService {
    UserAuthorizationResponse userGrantAuthorization(String code);

    UserAuthorizationResponse userGrantAuthorization(UserAuthorizationRequest request);
}
