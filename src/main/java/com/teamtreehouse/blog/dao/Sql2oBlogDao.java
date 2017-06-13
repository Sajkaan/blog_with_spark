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
    public void addEntry(BlogEntry blogEntry) throws DaoException {
        String sql = "INSERT INTO blogEntry (title, author, blogPost, date) VALUES (:title, :author, :blogPost, :date)";
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
        try (Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM blogEntry")
                    .executeAndFetch(BlogEntry.class);
        }
    }

    @Override
    public BlogEntry findEntryById(int id) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FORM blogEntry WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(BlogEntry.class);
        }
    }

    @Override
    public void removeEntryById(int id) throws DaoException {
        try (Connection connection = sql2o.open()){
            String sql = String.format("DELETE * FROM blogEntry WHERE id = %d", id);
            connection.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem removing entry");
        }
    }
}
