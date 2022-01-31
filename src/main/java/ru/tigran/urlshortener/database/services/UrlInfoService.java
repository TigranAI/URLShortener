package ru.tigran.urlshortener.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tigran.urlshortener.database.entity.UrlInfo;
import ru.tigran.urlshortener.database.repository.UrlInfoRepository;
import ru.tigran.urlshortener.utils.URLEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UrlInfoService {
    @Autowired
    UrlInfoRepository urlInfoRepository;
    @Autowired
    RedirectStatsService redirectStatsService;

    private static final long TryAttempts = 500L;

    public UrlInfo getByUrl(String url) throws Exception {
        UrlInfo urlInfo = urlInfoRepository.findByUrl(url);
        if (urlInfo == null) return create(url);
        return urlInfo;
    }

    public UrlInfo create(String url) throws Exception {
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setUrl(url);
        urlInfo = urlInfoRepository.save(urlInfo);

        String hash = URLEncoder.encode(urlInfo).substring(0, 7);
        if (urlInfoRepository.findByHash(hash) != null) hash = findUniqueHash(urlInfo);
        urlInfo.setHash(hash);

        return urlInfoRepository.save(urlInfo);
    }

    private String findUniqueHash(UrlInfo urlInfo) throws Exception {
        for (long i = 0L; i < TryAttempts; ++i) {
            String tempHash = URLEncoder.encode(urlInfo, i).substring(0, 7);
            UrlInfo tempUrlInfo = urlInfoRepository.findByHash(tempHash);
            if (tempUrlInfo == null) return tempHash;
        }
        return hopelessUrl(urlInfo);
    }

    private String hopelessUrl(UrlInfo urlInfo) throws Exception {
        String hash = URLEncoder.encode(urlInfo);
        for (int i = 8; i < hash.length(); ++i) {
            String tempHash = hash.substring(0, i);
            UrlInfo tempUrlInfo = urlInfoRepository.findByHash(tempHash);
            if (tempUrlInfo == null) return tempHash;
        }
        throw new Exception("Can't create unique hash for this link!");
    }

    public UrlInfo getByHash(String hash) throws EntityNotFoundException {
        UrlInfo info = urlInfoRepository.findByHash(hash);
        if (info != null) return info;

        throw new EntityNotFoundException("Can't find url by hash '" + hash + "'");
    }

    public void delete(Long urlId) {
        urlInfoRepository.deleteById(urlId);
    }

    public List<UrlInfo> getAllUrlsWithStats(){
        List<UrlInfo> result = urlInfoRepository.findAll();
        result.forEach(item -> {
            item.setRedirectCount(redirectStatsService.getRedirects(item.getHash()));
            item.setUniqueRedirectCount(redirectStatsService.getUniqueRedirects(item.getHash()));
        });
        return result;
    }

    public List<UrlInfo> getAllUrls(){
        return urlInfoRepository.findAll();
    }

    public void deleteAllUrls(List<UrlInfo> urlInfoList){
        urlInfoRepository.deleteAll(urlInfoList);
    }
}
