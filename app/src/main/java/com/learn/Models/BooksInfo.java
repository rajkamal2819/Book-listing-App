package com.learn.Models;

import java.util.ArrayList;

public class BooksInfo {

    private String bookTitle;
    private String publisher;
    private String publishingDate;
    private String description;
    private int pageCount;
    private ArrayList authors;
    private String thumbnailLink;
    private String language;
    private String previewLink;
    private String buyingLink;
    private double rating;
    private int ratingCount;

    public BooksInfo(){}

    public BooksInfo(String bookTitle,ArrayList<String> authors,String thumbnailLink){
        this.authors = authors;
        this.bookTitle = bookTitle;
        this.thumbnailLink = thumbnailLink;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList authors) {
        this.authors = authors;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getBuyingLink() {
        return buyingLink;
    }

    public void setBuyingLink(String buyingLink) {
        this.buyingLink = buyingLink;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
