package ru.tigran.urlshortener.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tigran.urlshortener.database.entity.RedirectStats;
import ru.tigran.urlshortener.database.repository.RedirectStatsRepository;

@Service
public class RedirectStatsService {
    @Autowired
    RedirectStatsRepository redirectStatsRepository;

    public void save(RedirectStats redirectStats){
        redirectStatsRepository.save(redirectStats);
    }

    public Integer getUniqueRedirects(String urlHash){
        return redirectStatsRepository.countDistinctIpAddressByUrlHash(urlHash);
    }

    public Integer getRedirects(String urlHash){
        return redirectStatsRepository.countByUrlHash(urlHash);
    }
}
