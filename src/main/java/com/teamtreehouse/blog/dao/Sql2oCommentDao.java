package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.exceptions.DaoException;
import com.teamtreehouse.blog.model.Comment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCommentDao implements CommentDao {

    private final Sql2o sql2o;

    public Sql2oCommentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addComment(Comment comment) throws DaoException {
        try (Connection connection = sql2o.open()){
            String sqlQuery = "INSERT INTO comments (entry_id, author, author_comment) " +
                    "VALUES (:entry_id, :author, :author_comment)";
            int id = (int) connection.createQuery(sqlQuery)
                    .bind(comment)
                    .executeUpdate()
                    .getKey();
            comment.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding comment");
        }
    }

    @Override
    public List<Comment> findAllComments() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM comments")
                    .addColumnMapping("ENTRY_ID", "entry_id")
                    .executeAndFetch(Comment.class);
        }
    }

    @Override
    public List<Comment> findByBlogEntry(int blogEntryId) {
        try (Connection connection = sql2o.open()){
            String sql = "SELECT * FROM comments WHERE entry_id = :blogEntryId";
            return connection.createQuery(sql)
                    .addColumnMapping("ENTRY_ID", "entry_id")
                    .addParameter("blogEntryId", blogEntryId)
                    .executeAndFetch(Comment.class);
        }
    }
}
