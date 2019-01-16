package com.caiwl.yungo.track.handler;

import org.rapidoid.http.Req;
import org.rapidoid.http.ReqRespHandler;
import org.rapidoid.http.Resp;
import org.rapidoid.job.Jobs;

/**
 * @author caiwl
 * @date 2019/1/16 10:56
 */
public class ChunkedHttpResponseHandler implements ReqRespHandler {
    @Override
    public Object execute(Req req, Resp resp) throws Exception {
        // mark asynchronous request processing
        req.async();

        // send part 1
        resp.chunk("part 1".getBytes());

        // after some time, send part 2 and finish
        Jobs.after(100).milliseconds(() -> {
            resp.chunk(" & part 2".getBytes());
            resp.done();
        });

        return resp;
    }
}
