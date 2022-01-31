package ru.tigran.urlshortener.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class UrlInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String hash;
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @Transient
    private Integer redirectCount = 0;
    @Transient
    private Integer uniqueRedirectCount = 0;

    @Transient
    @OneToMany(targetEntity = RedirectStats.class, cascade = CascadeType.ALL, mappedBy = "urlHash", orphanRemoval = true)
    private List<RedirectStats> rs;

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

    public Integer getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(Integer redirectCount) {
        this.redirectCount = redirectCount;
    }

    public Integer getUniqueRedirectCount() {
        return uniqueRedirectCount;
    }

    public void setUniqueRedirectCount(Integer uniqueRedirectCount) {
        this.uniqueRedirectCount = uniqueRedirectCount;
    }
}
