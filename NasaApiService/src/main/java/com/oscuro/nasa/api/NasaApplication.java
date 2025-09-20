//Quarkus doesn't need to have this starting point however it still supports JAX-RS application definition.
//Implemented in order to set the application path

package com.oscuro.nasa.api;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("nasaApplication")
public class NasaApplication extends Application {
}
