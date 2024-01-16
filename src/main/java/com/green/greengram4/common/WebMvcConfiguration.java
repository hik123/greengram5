package com.green.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    //@Value("${file.dir}")
    private final String imgFolder;

    public WebMvcConfiguration(@Value("${file.dir}") String imgFolder) {
        this.imgFolder = imgFolder;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**")
                        .addResourceLocations("file:" + imgFolder);



        registry
        .addResourceHandler("/**")
        .addResourceLocations("classpath:/static/**")
        .resourceChain(true)
        .addResolver(new PathResourceResolver() {
            @Override
            protected Resource getResource(String resourcePath, Resource location) throws IOException {
                Resource requestedResource = location.createRelative(resourcePath);
                // If we actually hit a file, serve that. This is stuff like .js and .css files.
                if (requestedResource.exists() && requestedResource.isReadable()) {
                    return requestedResource;
                }
                // Anything else returns the index.
                return new ClassPathResource("/static/index.html");
            }
        });
    }
}
