package org.mryao.ucas.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mryao.ucas.feign.client.ScholarinFeignClient;
import org.mryao.ucas.feign.client.WxCallbackFeignClient;
import org.mryao.ucas.feign.client.WxFeignClient;
import org.mryao.ucas.util.JacksonUtil;
import org.mryao.ucas.view.SepLoginRequest;
import org.mryao.ucas.view.TokenResponse;
import org.mryao.ucas.view.WxSendTemplateMessageRequest;
import org.mryao.ucas.view.WxSendTemplateMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ScholarinFeignClient scholarinFeignClient;

    @Autowired
    private WxCallbackFeignClient wxCallbackFeignClient;

    @Autowired
    private WxFeignClient wxFeignClient;

    @Autowired
    private OkHttpClient okHttpClient;

    @GetMapping("")
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void test() {
        // step 1: get identity
        String s = scholarinFeignClient.login(new SepLoginRequest("", ""));
        String code = s.replace("\"", "");
        log.info("code {}", code);

        // step 2: login
        Request loginRequest = new Request.Builder()
                .url("https://jwxk.ucas.ac.cn/login?Identity=" + code)
                .build();

        String cookie;
        String location;
        try {
            Response response = okHttpClient.newCall(loginRequest).execute();
            List<String> cookies = response.headers(HttpHeaders.SET_COOKIE);
            cookie = cookies.get(0).split(";")[0];
            List<String> locations = response.headers(HttpHeaders.LOCATION);
            location = locations.get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // step 3: redirect to main
        Request mainRequest = new Request.Builder()
                .url("https://jwxk.ucas.ac.cn" + location)
                .build();

        try {
            Response response = okHttpClient.newCall(mainRequest).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> courseIdList = new ArrayList<>();
        courseIdList.add("081203M04001H");
//        courseIdList.add("030500MGB001H-06");
//        courseIdList.add("030500MGB001H-07");
//        courseIdList.add("030500MGB001H-08");
//        courseIdList.add("081201M04002H");
//        courseIdList.add("081203M04001H");
//        courseIdList.add("0839X6M05006H");
//
        TokenResponse tokenResponse = wxCallbackFeignClient.getClientToken("wxb2b75d5c3e1e0d8c");
        String token = tokenResponse.getToken();
//
//        for (String courseId : courseIdList) {
//            FormBody formBody = new FormBody.Builder()
//                    .add("isGraduate", "true")
//                    .add("deptId", "")
//                    .add("termId", "71119")
//                    .add("courseCode", courseId)
//                    .add("courseName", "")
//                    .add("courseAttribute", "")
//                    .add("isSummer", "")
//                    .build();
//            Request scheduleRequest = new Request.Builder()
//                    .url("https://jwxk.ucas.ac.cn/course/termSchedule")
//                    .addHeader(HttpHeaders.COOKIE, cookie)
//                    .addHeader(HttpHeaders.REFERER, "https://jwxk.ucas.ac.cn/notice/view/1")
//                    .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
//                    .post(formBody)
//                    .build();
//
//            try {
//                Response response = okHttpClient.newCall(scheduleRequest).execute();
//                String body = response.body().string();
//                Document document = Jsoup.parse(body);
//                Elements elements = document.select("a:contains(" + courseId + ")");
//                Element parent = elements.get(0).parent();
//                Elements siblingElements = parent.siblingElements();
//                Element courseNameElement = siblingElements.get(2);
//                Element limitElement = siblingElements.get(6);
//                Element countElement = siblingElements.get(7);
//                String courseName = courseNameElement.text();
//                Integer limit = Integer.parseInt(limitElement.text());
//                Integer count = Integer.parseInt(countElement.text());
//                log.info("{} {} {} {}", courseId, courseName, limit, count);
//                if (count < limit) {
//                    sendTemplateMessage(token, courseId, courseName, limit, count);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        // step 4: get new code
        Request courseMainRequest = new Request.Builder()
                .url("https://jwxk.ucas.ac.cn/courseManage/main")
                .addHeader(HttpHeaders.COOKIE, cookie)
                .addHeader(HttpHeaders.REFERER, "https://jwxk.ucas.ac.cn/notice/view/1")
                .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .build();

        String newCode;
        try {
            Response response = okHttpClient.newCall(courseMainRequest).execute();
            String mainBody = response.body().string();
            int i = mainBody.indexOf("/courseManage/savePhone?s=") + "/courseManage/savePhone?s=".length();
            newCode = mainBody.substring(i, i + "a9c8d534-75eb-4385-b2e2-885e9fa17b81".length());
            log.info("mainBody {}", newCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // step 5: save course
        FormBody formBody = new FormBody.Builder()
                .add("deptIds", "951")
                .add("sb", "0")
                .build();
        Request saveRequest = new Request.Builder()
                .url("https://jwxk.ucas.ac.cn/courseManage/selectCourse?s=" + newCode)
                .addHeader(HttpHeaders.COOKIE, cookie)
                .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(saveRequest).execute();
            String body = response.body().string();
            Document document = Jsoup.parse(body);

            for (String courseId : courseIdList) {
                Elements elements = document.select("a:contains(" + courseId + ")");
                Element parent = elements.get(0).parent();
                Elements siblingElements = parent.siblingElements();
                Element courseNameElement = siblingElements.get(3);
                Element limitElement = siblingElements.get(6);
                Element countElement = siblingElements.get(7);
                String courseName = courseNameElement.text();
                Integer limit = Integer.parseInt(limitElement.text());
                Integer count = Integer.parseInt(countElement.text());
                log.info("{} {} {} {}", courseId, courseName, limit, count);
                if (count < limit) {
                    sendTemplateMessage(token, courseId, courseName, limit, count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        return code;
//        List<String> cookies;
//        Request request = new Request.Builder()
//                .url("https://me.ucas.ac.cn/site/login/cas-login?Identity=" + code)
//                .build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
////            String cookie = response.header(HttpHeaders.SET_COOKIE);
//            long timestamp = System.currentTimeMillis() / 1000;
//            cookies = response.headers(HttpHeaders.SET_COOKIE);
//            log.info("cookie {} {}", timestamp, cookies);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Set<String> cookieSet = cookies.stream().map(cookie -> {
//            return cookie.split(";")[0];
//        }).collect(Collectors.toSet());
//        String cookie = String.join(";", cookieSet);
//        Request.Builder builder = new Request.Builder()
//                .addHeader(HttpHeaders.COOKIE, cookie)
//                .addHeader(HttpHeaders.REFERER, "https://me.ucas.ac.cn/")
//                .url("https://me.ucas.ac.cn/site/user/all-info");
//        Request infoRequest = builder.build();
//        try {
//            Response response = okHttpClient.newCall(infoRequest).execute();
//            String body = response.body().string();
//            log.info("body {}", body);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void sendTemplateMessage(String token, String courseId, String courseName, Integer limit, Integer count) {
        WxSendTemplateMessageRequest.Keywords keywords = new WxSendTemplateMessageRequest.Keywords();
        keywords.setFirst(new WxSendTemplateMessageRequest.Keyword("出现空余名额！"));
        keywords.setKeyword1(new WxSendTemplateMessageRequest.Keyword("Mr.YAO"));
        keywords.setKeyword2(new WxSendTemplateMessageRequest.Keyword(courseName));
        keywords.setKeyword3(new WxSendTemplateMessageRequest.Keyword(courseId));
        keywords.setKeyword4(new WxSendTemplateMessageRequest.Keyword(count + "/" + limit));
        keywords.setRemark(new WxSendTemplateMessageRequest.Keyword("请尽快选课！"));

        WxSendTemplateMessageRequest request = new WxSendTemplateMessageRequest();
        request.setTemplateId("IaSx5D810O25Ok0rr3yVE3M9cAJrHChtIOVLA1phVEE");
        request.setToUser("ohuEA5-PXBRrHqJ6Jz0m-ABkHyRo");
        request.setData(keywords);
        WxSendTemplateMessageResponse response = wxFeignClient.sendTemplateMessage(token, request);
        log.info("response {}", new String(JacksonUtil.writeJsonValueAsBytes(response)));
    }
}
