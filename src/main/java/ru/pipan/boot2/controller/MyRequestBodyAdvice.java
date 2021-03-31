package ru.pipan.boot2.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@ControllerAdvice
public class MyRequestBodyAdvice implements RequestBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(MyRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        logger.info("beforeBodyRead({}, {}, {}, {})", inputMessage, parameter, targetType, converterType);
        String url = ((PostMapping) parameter.getExecutable().getDeclaredAnnotations()[0]).value()[0];
        byte[] body = FileCopyUtils.copyToByteArray(inputMessage.getBody());
        logger.info("{}: {}", url, new String(body));
        return new MyHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(body));
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.info("afterBodyRead({}, {}, {}, {}, {})", body, inputMessage, parameter, targetType, converterType);
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.info("handleEmptyBody({}, {}, {}, {}, {})", body, inputMessage, parameter, targetType, converterType);
        return body;
    }

    @AllArgsConstructor
    private static class MyHttpInputMessage implements HttpInputMessage {

        private final HttpHeaders httpHeaders;
        private final InputStream body;

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return httpHeaders;
        }
    }
}
