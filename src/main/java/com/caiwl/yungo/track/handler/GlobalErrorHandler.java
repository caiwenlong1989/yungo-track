package com.caiwl.yungo.track.handler;

import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.http.customize.ErrorHandler;
import org.rapidoid.log.Log;

/**
 * @author caiwl
 * @date 2019/1/16 11:09
 */
public class GlobalErrorHandler implements ErrorHandler {
    @Override
    public Object handleError(Req req, Resp resp, Throwable error) throws Exception {
        Log.error("global error handler", error);
        return null;
    }
}
