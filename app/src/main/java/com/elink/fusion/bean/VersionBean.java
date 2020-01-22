package com.elink.fusion.bean;

/**
 * @author Evloution
 * @date 2020-01-22
 * @email 15227318030@163.com
 * @description
 */
public class VersionBean {

    /**
     * path : docCenter/app/fusion-0.1.apk
     * publishdesc : 第一版APP
     * version : 0.1
     * versionnum : 0.1
     */

    public String path;
    public String publishdesc;
    public String version;
    public double versionnum;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPublishdesc() {
        return publishdesc;
    }

    public void setPublishdesc(String publishdesc) {
        this.publishdesc = publishdesc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(double versionnum) {
        this.versionnum = versionnum;
    }
}
