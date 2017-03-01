package com.teamtreehouse.blog.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
  private String author;
  private String comment;
  private String date;

  public Comment(String author, String comment) {
    this.author = author;
    this.comment = comment;
    this.date = getDate();
  }

  public String getAuthor() {
    return author;
  }

  public String getComment() {
    return comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Comment comment1 = (Comment) o;

    if (author != null ? !author.equals(comment1.author) : comment1.author != null) {
      return false;
    }
    if (comment != null ? !comment.equals(comment1.comment) : comment1.comment != null) {
      return false;
    }
    return date != null ? date.equals(comment1.date) : comment1.date == null;
  }

  @Override
  public int hashCode() {
    int result = author != null ? author.hashCode() : 0;
    result = 31 * result + (comment != null ? comment.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    return result;
  }

  private String getDate() {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    String dateString = format.format(date);
    return dateString;
  }
}
