package com.caiwl.yungo.track.util;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.rapidoid.log.Log;

import java.io.IOException;

/**
 * @author caiwl
 * @date 2019/1/16 11:41
 */
public class Ip2Region {
    private static DbSearcher searcher;

    static {
        try {
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, Files.load("ip2region.db"));
        } catch (DbMakerConfigException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param ip
     * @return 国家|区域|省份|城市|ISP(Internet Service Provider)，互联网服务提供商
     */
    public static String getRegion(String ip) {
        if (Util.ipIsNull(ip)) {
            return null;
        }
        try {
            DataBlock block = searcher.memorySearch(ip);
            return block.getRegion();
        } catch (IOException e) {
            Log.error("Ip2Region error", e);
            return null;
        }
    }
}
