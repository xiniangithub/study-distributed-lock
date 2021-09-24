package com.wez.study.distribute.lock.po;

/**
 * DistributeBusinessLock
 *
 * @Author wez
 * @Time 2021/9/24 16:29
 */
public class DistributeBusinessLock {

    private Long id;

    private String businessCode;

    private String businessName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public String toString() {
        return "DistributeBusinessLock{" +
                "id=" + id +
                ", businessCode='" + businessCode + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}
