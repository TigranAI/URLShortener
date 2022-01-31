package ru.tigran.urlshortener.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tigran.urlshortener.database.entity.RedirectStats;

@Repository
public interface RedirectStatsRepository extends JpaRepository<RedirectStats, Long> {

    Integer countByUrlHash(String urlHash);

    @Query(value = "select count(distinct rs.ipAddress) from RedirectStats rs where rs.urlHash = ?1")
    Integer countDistinctIpAddressByUrlHash(String urlHash);
}
