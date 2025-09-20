package com.oscuro.nasa.api.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class NasaConfig {

    @ConfigProperty(name = "nasa.api.key")
    String apiKey;

    @ConfigProperty(name = "NASA_BASE_URL")
    String nasaBaseURL;

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseURL() {
        System.out.println("NASA BASE URL : " + nasaBaseURL);
        return nasaBaseURL;
    }
}
