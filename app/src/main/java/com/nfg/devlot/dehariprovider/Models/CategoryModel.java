package com.nfg.devlot.dehariprovider.Models;

/**
 * Created by hassan on 3/31/18.
 */

public class CategoryModel {

    private String cid;
    private String cname;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
