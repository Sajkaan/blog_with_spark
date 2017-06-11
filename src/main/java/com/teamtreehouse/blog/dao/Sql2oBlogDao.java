package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.exceptions.DaoException;
import com.teamtreehouse.blog.model.BlogEntry;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oBlogDao implements BlogDao {

    private final Sql2o sql2o;

    public Sql2oBlogDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public int addEntry(BlogEntry blogEntry) throws DaoException {
        String sql = "INSERT INTO blog_entry (title, author, blogPost) VALUES (:title, :author, : blogPost)";
        try (Connection connection = sql2o.open()){
            int id = (int)connection.createQuery(sql)
                    .bind(blogEntry)
                    .executeUpdate()
                    .getKey();
            blogEntry.setId(id);

        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding entry");
        }
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
