package com.coolweather.app.model;

/**
 * Created by yiuon on 2016/1/7.
 */
public class Province {
    private int id;//每个实体类中都应该有的字段
    private String provinceName;//省的名字
    private String provinceCode;//省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
