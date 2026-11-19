package com.sip.vcm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource resolveResourceInternal(HttpServletRequest request,
                                                               String requestPath,
                                                               List<? extends Resource> locations,
                                                               org.springframework.web.servlet.resource.ResourceResolverChain chain) {
                        Resource requested = super.resolveResourceInternal(request, requestPath, locations, chain);
                        if (requested != null) {
                            return requested;
                        }
                        // Keep API requests as 404s; only client-side routes receive the SPA shell.
                        if (requestPath.startsWith("api/")) {
                            return null;
                        }
                        Resource index = new ClassPathResource("static/index.html");
                        return index.exists() ? index : null;
                    }
                });
    }
}
