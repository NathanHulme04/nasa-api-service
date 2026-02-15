package com.oscuro.nasa.api.entities;

import jakarta.persistence.*;

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
    public String explanation;

    public String hdurl;
    public String media_type;
    public String service_version;
    public String date;
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

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
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
                ", date='" + date + '\'' +
                '}';
    }
}
