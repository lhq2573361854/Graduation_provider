package com.tianling.controller;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpUtil;
import com.tianling.entities.ResponseInfo;
import com.tianling.utils.HttpResponseMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Tianling
 * @email 859073143@qq.com
 * @since 2021/3/9 10:13
 */
@RestController
@RequestMapping("/common-api")
@Slf4j
public class CommonApi {

    @GetMapping("getWeather/{city}")
    public Mono<ResponseInfo<String>> getWeatherByCity(@PathVariable String city){
        String str = "https://tianqiapi.com/api?version=v1&appid=58657392&appsecret=2n6XJ4Dd&city="+city;
        String s = HttpUtil.get(str, StandardCharsets.UTF_8);
        s = UnicodeUtil.toString(s);
        log.info(s);
        return HttpResponseMessageUtils.querySuccessResponse(s).defaultIfEmpty(HttpResponseMessageUtils.queryFailedCommonResponse());
    }
}
