package ru.tigran.urlshortener.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tigran.urlshortener.database.entity.UrlInfo;
import ru.tigran.urlshortener.database.repository.UrlInfoRepository;
import ru.tigran.urlshortener.utils.URLEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UrlInfoService {
    @Autowired
    UrlInfoRepository urlInfoRepository;

    private Set<UrlInfo> saveList = new HashSet<>();



    public UrlInfo getByUrl(String url) {
        UrlInfo urlInfo = urlInfoRepository.findByUrl(url);
        if (urlInfo == null) return create(url);
        return urlInfo;
    }

    public UrlInfo create(String url){
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setUrl(url);
        urlInfo = urlInfoRepository.save(urlInfo);
        String hash = URLEncoder.encode(urlInfo);
        for (int i = 7; i < hash.length(); ++i) {
            String tempHash = hash.substring(0, i);
            UrlInfo tempUrlInfo = urlInfoRepository.findByHash(tempHash);
            if (tempUrlInfo != null) continue;
            urlInfo.setHash(tempHash);
            break;
        }
        for (long i = 0L; urlInfo.getHash() == null; ++i){
            String tempHash = URLEncoder.encode(urlInfo, i);
            UrlInfo tempUrlInfo = urlInfoRepository.findByHash(tempHash);
            if (tempUrlInfo != null) continue;
            urlInfo.setHash(tempHash);
            break;
        }
        return urlInfoRepository.save(urlInfo);
    }

    public UrlInfo getByHash(String hash) throws EntityNotFoundException {
        UrlInfo info = urlInfoRepository.findByHash(hash);
        if (info != null) return info;

        throw new EntityNotFoundException("Can't find url by hash '" + hash + "'");
    }

    public void save(UrlInfo info){
        urlInfoRepository.save(info);
    }


}
