package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlogEntry {

    private String slug;
    private String title;
    private String author;
    private String blogPost;
    private String date;
    private List<Comment> commentList;
    private Set<Tag> tagSet;

    public BlogEntry(String title, String author, String blogPost) {

        commentList = new ArrayList<>();
        tagSet = new HashSet<>();

        this.title = title;
        this.author = author;
        this.blogPost = blogPost;
        date = setDate();

        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
    }

    public void editEntry(String title, String author, String blogPost) {
        date = setDate();
        this.title = title;
        this.author = author;
        this.blogPost = blogPost;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBlogPost() {
        return blogPost;
    }

    public String getDate() {
        return date;
    }

    public String getSlug() {
        return slug;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public boolean addComment(Comment comment) {
        return commentList.add(comment);
    }

    public boolean addTag(Tag tag) {
        return tagSet.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) {
            return false;
        }
        if (author != null ? !author.equals(blogEntry.author) : blogEntry.author != null) {
            return false;
        }
        return blogPost != null ? blogPost.equals(blogEntry.blogPost) : blogEntry.blogPost == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (blogPost != null ? blogPost.hashCode() : 0);
        return result;
    }

    private String setDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        return dateString;
    }
}
