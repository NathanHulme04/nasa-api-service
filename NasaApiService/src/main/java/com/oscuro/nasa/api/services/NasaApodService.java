package com.oscuro.nasa.api.services;

import com.oscuro.nasa.api.clients.NasaApodClient;
import com.oscuro.nasa.api.clients.NasaApodResponse;
import com.oscuro.nasa.api.config.NasaConfig;
import com.oscuro.nasa.api.entities.NasaApodEntity;
import com.oscuro.nasa.api.repositories.NasaApodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class NasaApodService {

    @Inject
    @RestClient
    NasaApodClient nasaApodClient;

    @Inject
    NasaApodRepository nasaApodRepository;

    @Inject
    NasaConfig nasaConfig;

    public NasaApodResponse fetchPictureOfTheDay() {

        NasaApodEntity nasaApodDate = nasaApodRepository.findByDate("2025-09-21");
        System.out.println( "WE'VE GOT A MATCH " + nasaApodDate );

        if( nasaApodDate != null ) {
            return nasaApodClient.getApod(nasaConfig.getApiKey());
        }
        else {
            NasaApodResponse nasaApodResponse = nasaApodClient.getApod(nasaConfig.getApiKey());

            NasaApodEntity nasaApodEntity = new NasaApodEntity();
            nasaApodEntity.setTitle( nasaApodResponse.title );
            nasaApodEntity.setUrl( nasaApodResponse.url );
            nasaApodEntity.setExplanation( nasaApodResponse.explanation );
            nasaApodEntity.setHdurl( nasaApodResponse.hdurl );
            nasaApodEntity.setMediaType( nasaApodResponse.media_type );
            nasaApodEntity.setServiceVersion( nasaApodResponse.service_version );
            nasaApodEntity.setDate( nasaApodResponse.date );
            nasaApodEntity.setCopyright( nasaApodResponse.copyright );

            nasaApodRepository.save( nasaApodEntity );

            return nasaApodResponse;
        }
    }

    //This is the service. Make the call to NASA Client to get the image information
//    String endpoint = NasaConstants.Nasa_Apod_Endpoint;
}
