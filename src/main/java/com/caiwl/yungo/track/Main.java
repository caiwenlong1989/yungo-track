package com.caiwl.yungo.track;

import com.caiwl.yungo.track.entity.TrackInfo;
import com.caiwl.yungo.track.util.Files;
import com.caiwl.yungo.track.util.GeoHash;
import com.caiwl.yungo.track.util.Ip2Region;
import com.caiwl.yungo.track.util.Util;
import org.rapidoid.http.MediaType;
import org.rapidoid.jpa.JPA;
import org.rapidoid.log.Log;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;

public class Main {
    public static void main(String[] args) {
        App.bootstrap(args).jpa();

        My.errorHandler((req, resp, error) -> {
            Log.error("global error handler", error);
            return null;
        });

        On.get("/track").plain((req, resp) -> {
            req.async();

            try {
                String uid = req.cookie("UID", null);

                String userAgent = req.header("User-Agent", null);
                String referer = req.header("Referer", null);
                String etag = req.header("If-None-Match", null);
                String host = req.header("Host", "");

                String phone = req.param("phone", null);
                String fingerprint = req.param("fp", null);
                String lat = req.param("lat", "0");
                String lon = req.param("lon", "0");

                String geoHash = GeoHash.encode(Double.valueOf(lat), Double.valueOf(lon));
                String ip = Util.getIp(req);
                String region = Ip2Region.getRegion(ip);

                if (Util.isEmpty(uid) && Util.isEmpty(etag)) {
                    uid = Util.getUid();
                    resp.header("ETag", '"' + uid + '"');
                    resp.header("Set-Cookie", Util.createUidCookie(uid, host));
                }
                if (Util.isEmpty(uid) && Util.notEmpty(etag)) {
                    uid = etag.substring(1, etag.length() - 1);
                    resp.code(304);
                    resp.body(Files.EMPTY_BODY);
                    resp.header("Set-Cookie", Util.createUidCookie(uid, host));
                }
                if (Util.notEmpty(uid) && Util.isEmpty(etag)) {
                    resp.header("ETag", '"' + uid + '"');
                }

                TrackInfo trackInfo = TrackInfo.builder()
                        .uid(uid).userAgent(userAgent).referer(referer)
                        .phone(phone).fingerprint(fingerprint)
                        .latitude(lat).longitude(lon).geoHash(geoHash)
                        .ip(ip).region(region)
                        .build();
                JPA.save(trackInfo);
            } catch (Exception e) {
                throw e;
            } finally {
                if (304 != resp.code()) {
                    resp.contentType(MediaType.IMAGE_PNG);
                    resp.body(Files.PIXEL);
                }
                resp.done();
            }

            return resp;
        });

        On.get("/favicon.ico").plain(Files.FAVICON);
    }
}
