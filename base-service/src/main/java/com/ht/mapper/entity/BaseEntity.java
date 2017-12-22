package com.ht.mapper.entity;

import java.time.LocalDateTime;

/**
 * 数据表映射基类
 * @author swt
 */
public class BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String id;                      // ID
    private LocalDateTime addTime;          // 添加时间
    private LocalDateTime modifyTime;       // 更新时间

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
}
