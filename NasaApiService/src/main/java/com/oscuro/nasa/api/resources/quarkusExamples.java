package com.oscuro.nasa.api.resources;

import com.oscuro.nasa.api.config.NasaConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import org.jboss.resteasy.reactive.RestCookie;
import org.jboss.resteasy.reactive.RestMatrix;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

@Path("quarkus-example")
public class quarkusExamples {

    //Example of how to inject
    @Inject
    NasaConfig nasaConfig;

    @GET
    public String hello() {
        return "Hello, World" + nasaConfig.getApiKey();
    }

    //URL builder
    //Example of getting information from the request
    @Path("/requestInformation")
    @POST
    public String requestInformation(@RestPath String path,
                                    @RestMatrix String matrix,
                                    @RestQuery String query1,
                                    @RestQuery String query2,
                                    @RestCookie String cookie) {
        return path + "/" + matrix + "/" + query1 + "/" + query2 + "/" + cookie;
    }
}
