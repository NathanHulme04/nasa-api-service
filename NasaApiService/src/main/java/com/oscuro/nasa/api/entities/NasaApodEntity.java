package com.oscuro.nasa.api.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "nasa_apod")
public class NasaApodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String title;
    public String url;

    @Lob
    @Column(columnDefinition = "TEXT")
    public String explanation;

    public String hdurl;
    public String media_type;
    public String service_version;
    public LocalDate date;
    @Column(columnDefinition = "TEXT")
    public String copyright;

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getExplanation(){
        return this.explanation;
    }
    public void setExplanation(String explanation){
        this.explanation = explanation;
    }

    public String getHdurl(){
        return this.hdurl;
    }
    public void setHdurl(String hdurl){
        this.hdurl = hdurl;
    }

    public String getMediaType(){
        return this.media_type;
    }
    public void setMediaType(String mediaType){
        this.media_type = mediaType;
    }

    public String getServiceVersion(){
        return this.service_version;
    }
    public void setServiceVersion(String serviceVersion){
        this.service_version = serviceVersion;
    }

    public LocalDate getDate(){
        return this.date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getCopyright(){
        return this.copyright;
    }
    public void setCopyright(String copyRight){
        this.copyright = copyRight;
    }

    @Override
    public String toString() {
        return "NasaApodEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", explanation='" + (explanation != null ? explanation.substring(0, Math.min(100, explanation.length())) + "..." : null) + '\'' +
                ", date='" + date.toString() + '\'' +
                '}';
    }
}
