package com.oscuro.nasa.api.clients;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.oscuro.nasa.api.config.NasaConstants;

@Path( NasaConstants.Nasa_Apod_Endpoint )
@RegisterRestClient(configKey = "nasa-api-base-url")
public interface NasaApodClient {

    //Client to make the request to Nasa
    @GET
    NasaApodResponse getApod(@QueryParam("api_key") String apiKey);
}
