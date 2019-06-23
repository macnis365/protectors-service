package com.protectors.app.protectorsservice.config;

import org.apache.catalina.servlets.WebdavServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public class H2BeanConfig {
    @Bean
    ServletRegistrationBean<WebdavServlet> h2servletRegistration() {
        ServletRegistrationBean<WebdavServlet> registrationBean = new ServletRegistrationBean<>(new WebdavServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
