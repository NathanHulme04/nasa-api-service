package com.oscuro.nasa.api.resources;

import com.oscuro.nasa.api.clients.NasaApodResponse;
import com.oscuro.nasa.api.services.NasaApodService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

@Path("apod")
public class NasaApodResource {

    @Inject
    NasaApodService nasaApodService;

    @GET
    //quarkus-rest-jackson used but keeping this @Produces annotation as a reminder for future self
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApod() {
        NasaApodResponse apod = nasaApodService.fetchPictureOfTheDay();
        return Response.ok(apod).build();
    }
}

