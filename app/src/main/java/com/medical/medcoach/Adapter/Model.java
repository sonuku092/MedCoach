package com.medical.medcoach.Adapter;

public class Model {
    String Title,Contents,Date,Author,ImgUrl, id;
    private Long ShareCount;

    public Model(String title, String contents, String date, String author, String imgUrl, Long shareCount, String id) {
        Title = title;
        Contents = contents;
        Date = date;
        Author = author;
        ImgUrl = imgUrl;
        ShareCount = shareCount;
        this.id = id;
    }

    public Model() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public Long getShareCount() {
        return ShareCount;
    }

    public void setShareCount(Long shareCount) {
        ShareCount = shareCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
