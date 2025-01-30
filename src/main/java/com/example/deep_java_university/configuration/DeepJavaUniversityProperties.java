package com.example.deep_java_university.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "university")
@ConfigurationPropertiesScan
public record DeepJavaUniversityProperties(String remoteUrl) {
}
