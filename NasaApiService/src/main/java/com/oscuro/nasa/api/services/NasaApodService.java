package com.oscuro.nasa.api.services;

import com.oscuro.nasa.api.clients.NasaApodClient;
import com.oscuro.nasa.api.clients.NasaApodResponse;
import com.oscuro.nasa.api.config.NasaConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class NasaApodService {

    @Inject
    @RestClient
    NasaApodClient nasaApodClient;

    @Inject
    NasaConfig nasaConfig;

    public NasaApodResponse fetchPictureOfTheDay() {
        return nasaApodClient.getApod(nasaConfig.getApiKey());
    }

    //This is the service. Make the call to NASA Client to get the image information
//    String endpoint = NasaConstants.Nasa_Apod_Endpoint;
}
