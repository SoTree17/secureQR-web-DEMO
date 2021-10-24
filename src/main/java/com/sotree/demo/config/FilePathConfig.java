package com.sotree.demo.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class FilePathConfig  implements WebMvcConfigurer {
    /*@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){*//* Configuration for path when download secure QR Image *//*
       *//* registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
                //.addResourceLocations("file:///c:/TestQR/qrImg/");*//*
    }*/
}
