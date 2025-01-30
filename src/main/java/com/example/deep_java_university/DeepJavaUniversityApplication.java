package com.example.deep_java_university;

import com.example.deep_java_university.configuration.DeepJavaUniversityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {DeepJavaUniversityProperties.class})
public class DeepJavaUniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepJavaUniversityApplication.class, args);
    }

}
