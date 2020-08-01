package com.example.instaappandroidprojectprojectbrain.Model;

public class Idea {


    private String title;

    private String context;

    private String content;

    private Profile1 author;

    public Idea() {}

    public Idea(String title, String context, String content, Profile1 author) {

        this.title = title;
        this.context = context;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Profile1 getAuthor() {
        return author;
    }

    public void setAuthor(Profile1 author) {
        this.author = author;
    }
}
