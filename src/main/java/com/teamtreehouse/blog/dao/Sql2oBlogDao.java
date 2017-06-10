package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oBlogDao implements BlogDao {

    private final Sql2o sql2o;

    public Sql2oBlogDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public int addEntry(BlogEntry blogEntry) {

    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return null;
    }

    @Override
    public BlogEntry findEntryById(int id) {
        return null;
    }

    @Override
    public void removeEntryById(int id) {

    }
}
