package org.mryao.ucas.service;

import org.mryao.ucas.view.UcasToken;

public interface UcasService {
    UcasToken meLogin(String code);

    UcasToken workflowLogin(String code);

    UcasToken appLogin(String email, String password);

    void workflowGetName(UcasToken token);

    void meGetAllInfo(UcasToken token);
}
