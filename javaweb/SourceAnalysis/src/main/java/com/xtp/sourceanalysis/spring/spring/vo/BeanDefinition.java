package com.xtp.sourceanalysis.spring.spring.vo;

import java.io.Serializable;

/**
 * VO (value object)
 * 基于此对象存储Bean的配置信息
 */
public class BeanDefinition implements Serializable {
    private String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPkgClass() {
        return pkgClass;
    }

    public void setPkgClass(String pkgClass) {
        this.pkgClass = pkgClass;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    private String pkgClass;
    private Boolean lazy = false;

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "id='" + id + '\'' +
                ", pkgClass='" + pkgClass + '\'' +
                ", lazy=" + lazy +
                '}';
    }
}
