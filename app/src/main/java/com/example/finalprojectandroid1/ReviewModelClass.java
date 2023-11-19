package com.example.finalprojectandroid1;

public class ReviewModelClass {

    private String review;
    private String rating;
    private String dateAndTime;

    public ReviewModelClass(String review, String rating, String dateAndTime) {
        this.review = review;
        this.rating = rating;
        this.dateAndTime = dateAndTime;
    }

    public ReviewModelClass() {
    }


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
