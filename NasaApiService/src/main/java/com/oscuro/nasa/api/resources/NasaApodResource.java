package com.oscuro.nasa.api.resources;

import com.oscuro.nasa.api.clients.NasaApodResponse;
import com.oscuro.nasa.api.services.NasaApodService;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

import javax.management.Query;
import java.util.List;

@Path("apod")
public class NasaApodResource {

    @Inject
    NasaApodService nasaApodService;

    @GET
    @Transactional
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApod(
            @QueryParam("fromDate") String fromDate,
            @QueryParam("toDate") String toDate,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder,
            @QueryParam("page") String page,
            @QueryParam("searchString") String searchString
    ) {
        List<NasaApodResponse> apod = nasaApodService.fetchApods(fromDate, toDate, sortBy, sortOrder, page, searchString);
        return Response.ok(apod).build();
    }
}

