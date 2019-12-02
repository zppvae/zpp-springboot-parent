package org.zpp.springboot.es.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * 云盘
 */
@Data
@Document(indexName = "index_disk", type = "disk")
public class CloudDisk {

    @Id
    private String id;
    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String describe;
    /**
     * 分享时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date shareTime;

    /**
     * 浏览次数
     */
    private Long views;

    /**
     * 文件大小
     */
    private Double fileSize;

    /**
     * 分享人
     */
    private String shareUser;

    /**
     * 文件地址
     */
    private String address;

}