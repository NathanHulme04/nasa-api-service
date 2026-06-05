package com.oscuro.nasa.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.oscuro.nasa.api.clients.NasaApodResponse;
import com.oscuro.nasa.api.entities.NasaApodEntity;
import com.oscuro.nasa.api.repositories.NasaApodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.logging.Log;


@ApplicationScoped
public class NasaApodService {

    @Inject
    NasaApodRepository nasaApodRepository;

    /**
     * Fetched the Astronomy Picture of the Day (APOD) from the database.
     * Since no date has been provided, it will return the last APOD entry in the database, which should be the most recent one.
     * If there is no entry in the database, it will return null.
     *
     * @return NasaApodResponse containing the APOD information for today.
     */

    public List<NasaApodResponse> fetchApods(String fromDate, String toDate, String sortBy, String sortOrder, String pageStr, String searchString) {

        LocalDate from = null;
        LocalDate to = null;
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
            from = LocalDate.parse(fromDate);
            to = LocalDate.parse(toDate);
        }

        int page = Objects.nonNull(pageStr) ? Integer.parseInt(pageStr) : 1;

        List<NasaApodEntity> apodEntities = nasaApodRepository.findByDateRange(from, to, sortBy, sortOrder, page, searchString);
        return apodEntities.stream().map(NasaApodService::convertToNasaApodResponse).toList();
    }

    public static NasaApodResponse convertToNasaApodResponse(NasaApodEntity nasaApodEntity) {
        NasaApodResponse nasaApodResponse = new NasaApodResponse();
        nasaApodResponse.title = nasaApodEntity.getTitle();
        nasaApodResponse.url = nasaApodEntity.getUrl();
        nasaApodResponse.explanation = nasaApodEntity.getExplanation();
        nasaApodResponse.hdurl = nasaApodEntity.getHdurl();
        nasaApodResponse.media_type = nasaApodEntity.getMediaType();
        nasaApodResponse.service_version = nasaApodEntity.getServiceVersion();
        nasaApodResponse.date = nasaApodEntity.getDate();
        nasaApodResponse.copyright = nasaApodEntity.getCopyright();

        return nasaApodResponse;
    }

    public static NasaApodEntity convertToNasaApodEntity(NasaApodResponse nasaApodResponse) {
        NasaApodEntity nasaApodEntity = new NasaApodEntity();
        nasaApodEntity.setTitle( nasaApodResponse.title );
        nasaApodEntity.setUrl( nasaApodResponse.url );
        nasaApodEntity.setExplanation( nasaApodResponse.explanation );
        nasaApodEntity.setHdurl( nasaApodResponse.hdurl );
        nasaApodEntity.setMediaType( nasaApodResponse.media_type );
        nasaApodEntity.setServiceVersion( nasaApodResponse.service_version );
        nasaApodEntity.setDate( nasaApodResponse.date );
        nasaApodEntity.setCopyright( nasaApodResponse.copyright );

        return nasaApodEntity;
    }
}
