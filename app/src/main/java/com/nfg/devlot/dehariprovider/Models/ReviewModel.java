package com.nfg.devlot.dehariprovider.Models;

/**
 * Created by hassan on 4/6/18.
 */

public class ReviewModel {

    private String serviceProviderRatingId;
    private String serviceSeekerId;
    private String serviceProviderId;
    private String reviewerName;
    private String rating;
    private String comment;
    private String time;

    public String getServiceProviderRatingId() {
        return serviceProviderRatingId;
    }

    public void setServiceProviderRatingId(String serviceProviderRatingId) {
        this.serviceProviderRatingId = serviceProviderRatingId;
    }

    public String getServiceSeekerId() {
        return serviceSeekerId;
    }

    public void setServiceSeekerId(String serviceSeekerId) {
        this.serviceSeekerId = serviceSeekerId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
