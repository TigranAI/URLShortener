package ru.tigran.urlshortener.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class UrlInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String hash;
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    private Long redirectCount = 0L;
    private Long uniqueRedirectCount = 0L;

    public void increaseRedirectCount() {
        redirectCount++;
    }

    public UrlInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp creationTime) {
        this.timestamp = creationTime;
    }

    public Long getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(Long redirectCount) {
        this.redirectCount = redirectCount;
    }

    public Long getUniqueRedirectCount() {
        return uniqueRedirectCount;
    }

    public void setUniqueRedirectCount(Long uniqueRedirectCount) {
        this.uniqueRedirectCount = uniqueRedirectCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlInfo urlInfo = (UrlInfo) o;
        return Objects.equals(id, urlInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
