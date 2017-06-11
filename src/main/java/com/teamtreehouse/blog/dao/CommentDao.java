package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;

import java.util.List;

public interface CommentDao {

    void addComment(String comment, String author);

    List<Comment> findAllComments();

    List<Comment> findByBlogEntry(int blogEntryId);
}
