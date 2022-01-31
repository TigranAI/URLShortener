package ru.tigran.urlshortener.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.tigran.urlshortener.database.entity.UrlInfo;
import ru.tigran.urlshortener.database.services.UrlInfoService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@EnableAsync
public class UrlLifetimeChecker {
    @Value("${config.url.lifetime.days}")
    private int days;

    @Autowired
    UrlInfoService urlInfoService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void deleteLifetimeEndUrl(){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().plusDays(days));
        List<UrlInfo> urlInfoList = urlInfoService.getAllUrls();
        urlInfoList.removeIf(item -> item.getTimestamp().compareTo(timestamp) <= 0);
        urlInfoService.deleteAllUrls(urlInfoList);
    }
}
