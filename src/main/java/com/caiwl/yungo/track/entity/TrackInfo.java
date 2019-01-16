package com.caiwl.yungo.track.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author caiwl
 * @date 2019/1/16 10:28
 */
@Entity(name = "t_track_info")
@Data
@Builder
public class TrackInfo {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
    private String phone;
    private String uid;
    @Column(name = "user_agent")
    private String userAgent;
    private String referer;
    /** 设置指纹 */
    private String fingerprint;
    /** 纬度 */
    private String latitude;
    /** 经度 */
    private String longitude;
    private String ip;
    /** GEO_HASH值 */
    @Column(name = "geo_hash")
    private String geoHash;
    /** GEO_HASH值对应的区域信息 */
    private String region;
}
