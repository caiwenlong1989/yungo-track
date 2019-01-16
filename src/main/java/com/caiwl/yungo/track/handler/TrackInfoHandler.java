package com.caiwl.yungo.track.handler;

import org.rapidoid.http.Req;
import org.rapidoid.http.ReqRespHandler;
import org.rapidoid.http.Resp;

/**
 * @author caiwl
 * @date 2019/1/16 10:56
 */
public class TrackInfoHandler implements ReqRespHandler {
    @Override
    public Object execute(Req req, Resp resp) throws Exception {
        req.async();

        try {

        } catch (Exception e) {
            throw e;
        } finally {
            resp.done();
        }

        return resp;
    }
}
