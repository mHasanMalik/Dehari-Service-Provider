package com.nfg.devlot.dehariprovider.Models;

/**
 * Created by faraz on 03-Apr-18.
 */

public class JobModel {
    private String id;

    private String j_id;
    private String s_id;
    private String ss_id;
    private String sp_id;
    private String starting_time;
    private String ending_time;
    private String location;
    private String status;
    private String additional_charges;

    private String seekerName;
    private String SeekingService;
    private String seekerFcToken;


    public JobModel()
    {

    }

    public JobModel(String id, String j_id, String s_id, String ss_id, String sp_id, String starting_time, String ending_time, String location, String status, String additional_charges) {
        this.id = id;
        this.j_id = j_id;
        this.s_id = s_id;
        this.ss_id = ss_id;
        this.sp_id = sp_id;
        this.starting_time = starting_time;
        this.ending_time = ending_time;
        this.location = location;
        this.status = status;
        this.additional_charges = additional_charges;
    }


    public String getSeekerFcToken() {
        return seekerFcToken;
    }

    public void setSeekerFcToken(String seekerFcToken) {
        this.seekerFcToken = seekerFcToken;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getSeekingService() {
        return SeekingService;
    }

    public void setSeekingService(String seekingService) {
        SeekingService = seekingService;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJ_id() {
        return j_id;
    }

    public void setJ_id(String j_id) {
        this.j_id = j_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(String starting_time) {
        this.starting_time = starting_time;
    }

    public String getEnding_time() {
        return ending_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdditional_charges() {
        return additional_charges;
    }

    public void setAdditional_charges(String additional_charges) {
        this.additional_charges = additional_charges;
    }
}
