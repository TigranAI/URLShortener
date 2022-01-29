package ru.tigran.urlshortener.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tigran.urlshortener.database.entity.UrlInfo;

@Repository
public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {
    UrlInfo findByUrl(String url);
    UrlInfo findByHash(String hash);
}
