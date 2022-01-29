package ru.tigran.urlshortener.utils;

import org.hashids.Hashids;
import ru.tigran.urlshortener.database.entity.UrlInfo;

public class URLEncoder {
    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
    private final static Integer hashSize = 7;
    private final static Hashids encoder = new Hashids("", hashSize, alphabet);

    public static String encode(UrlInfo info){
        return encoder.encode(info.getId(), info.getTimestamp().getTime());
    }

    public static String encode(UrlInfo info, long number){
        return encoder.encode(info.getId(), info.getTimestamp().getTime(), number);
    }
}
