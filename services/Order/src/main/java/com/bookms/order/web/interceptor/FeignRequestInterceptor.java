package com.bookms.order.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("X-SERVICE-REQUEST","ORDER-SERVICE");
    }
}
