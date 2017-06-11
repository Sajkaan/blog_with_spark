package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.exceptions.DaoException;
import com.teamtreehouse.blog.model.BlogEntry;

import java.util.List;

public interface BlogDao {

    void addEntry(BlogEntry blogEntry) throws DaoException;

    List<BlogEntry> findAllEntries();

    BlogEntry findEntryById(int id);

    void removeEntryById(int id) throws DaoException;


}
