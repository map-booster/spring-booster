package com.coo.gis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coo.gis.event.CascadeSaveMongoEventListener;

@Configuration
public class MongoConfig {

	@Bean
    public CascadeSaveMongoEventListener cascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }

}
