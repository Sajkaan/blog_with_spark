package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.List;

public interface BlogDao {

    int addEntry(BlogEntry blogEntry);

    List<BlogEntry> findAllEntries();

    BlogEntry findEntryById(int id);

    void removeEntryById(int id);


}
