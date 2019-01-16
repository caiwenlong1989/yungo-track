package com.caiwl.yungo.track.util;

import org.rapidoid.http.Req;
import org.rapidoid.u.U;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * @author caiwl
 * @date 2019/1/16 11:39
 */
public class Util extends U {

    public static String getIp(Req req) {
        String ip = req.header("X-Forwarded-For", null);
        if (ipIsNull(ip)) {
            ip = req.header("Proxy-Client-IP", null);
            if (ipIsNull(ip)) {
                ip = req.header("WL-Proxy-Client-IP", null);
            }
            if (ipIsNull(ip)) {
                ip = req.header("HTTP_CLIENT_IP", null);
            }
            if (ipIsNull(ip)) {
                ip = req.header("HTTP_X_FORWARDED_FOR", null);
            }
            if (ipIsNull(ip)) {
                ip = req.clientIpAddress();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
    
    public static boolean ipIsNull(String ip) {
        return isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }

    public static String getUid() {
        return UUID.randomUUID().toString();
    }

    public static String createUidCookie(String uid, String host) {
        String domain;
        int idx = host.indexOf(":");
        if (idx >= 0) {
            domain = DomainNames.getPLD(host.substring(0, idx));
        } else {
            domain = DomainNames.getPLD(host);
        }
        StringBuilder cookie = new StringBuilder();
        cookie.append("UID").append("=").append(uid);
        long second = System.currentTimeMillis() / 1000 + Integer.MAX_VALUE;
        cookie.append("; Expires").append("=").append(rfc1123DateTime(second));
        cookie.append("; Max-Age").append("=").append(second);
        cookie.append("; Path=/");
        cookie.append("; Domain").append("=").append(domain);
        return cookie.toString();
    }

    private static String rfc1123DateTime(long second) {
        return RFC_1123_DATE_TIME.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(second), ZoneId.systemDefault()));
    }
}
