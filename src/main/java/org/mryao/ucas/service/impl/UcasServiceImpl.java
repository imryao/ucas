package org.mryao.ucas.service.impl;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.mryao.ucas.feign.client.MeFeignClient;
import org.mryao.ucas.feign.client.WorkflowFeignClient;
import org.mryao.ucas.service.UcasService;
import org.mryao.ucas.view.UcasResponse;
import org.mryao.ucas.view.UcasToken;
import org.mryao.ucas.view.WorkflowGetNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class UcasServiceImpl implements UcasService {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private MeFeignClient meFeignClient;

    @Autowired
    private WorkflowFeignClient workflowFeignClient;

    private String getCookieValue(List<String> cookies, String name) {
        String rawCookie = cookies.stream().filter(cookie -> cookie.startsWith(name)).findFirst().orElse(null);
        return rawCookie.split(";")[0].split("=")[1];
    }

    @Override
    public UcasToken meLogin(String code) {
        String url = String.format("https://me.ucas.ac.cn/site/login/cas-login?Identity=%s", code);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            List<String> cookies = response.headers(HttpHeaders.SET_COOKIE);
            Integer vjuid = Integer.valueOf(getCookieValue(cookies, "vjuid"));
            String vjvd = getCookieValue(cookies, "vjvd");
            log.info("[me] [login] [Response] {} {}", vjuid, vjvd);
            return new UcasToken(vjuid, vjvd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UcasToken workflowLogin(String code) {
        String url = String.format("https://ehall.ucas.ac.cn/site/login/cas-login?Identity=%s", code);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            List<String> cookies = response.headers(HttpHeaders.SET_COOKIE);
            Integer vjuid = Integer.valueOf(getCookieValue(cookies, "vjuid"));
            String vjvd = getCookieValue(cookies, "vjvd");
            log.info("[workflow] [login] [Response] {} {}", vjuid, vjvd);
            return new UcasToken(vjuid, vjvd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UcasToken appLogin(String email, String password) {
        String url = "https://app.ucas.ac.cn/uc/wap/login/check";
        FormBody formBody = new FormBody.Builder()
                .add("username", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String string = response.body().string();
            log.info("string {}", string);
            List<String> cookies = response.headers(HttpHeaders.SET_COOKIE);
            String eaiSess = getCookieValue(cookies, "eai-sess");
            String uuKey = getCookieValue(cookies, "UUkey");
            log.info("[app] [login] [Response] {} {}", eaiSess, uuKey);
            return new UcasToken(eaiSess, uuKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void workflowGetName(UcasToken token) {
        Integer vjuid = token.getVjuid();
        String vjvd = token.getVjvd();
        String cookie = String.format("vjuid=%d; vjvd=%s", vjuid, vjvd);
        UcasResponse<WorkflowGetNameResponse> response = workflowFeignClient.getName(cookie);
        WorkflowGetNameResponse data = response.getData();
        String ucasId = data.getUcasId();
        log.info("name {}", ucasId);
    }

    @Override
    public void meGetAllInfo(UcasToken token) {
        Integer vjuid = token.getVjuid();
        String vjvd = token.getVjvd();
        String cookie = String.format("vjuid=%d; vjvd=%s", vjuid, vjvd);
        String allInfo = meFeignClient.getAllInfo(cookie);
        log.info("allinfo {}", allInfo);
    }
}
