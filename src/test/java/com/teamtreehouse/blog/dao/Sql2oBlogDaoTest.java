package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oBlogDaoTest {

    private Sql2oBlogDao dao;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"", "");
        dao = new Sql2oBlogDao(sql2o);

        // Connection open through the entire test
        connection = sql2o.open();
    }

    @Test
    public void addingBlogEntrySetsId() throws Exception {
        BlogEntry blogEntry = newBlogEntry();
        int originalEntryId = blogEntry.getId();

        dao.addEntry(blogEntry);

        assertNotEquals(originalEntryId, blogEntry.getId());
    }







    @After
    public void tearDown() throws Exception {
        connection.close();
    }



    private BlogEntry newBlogEntry() {
        return new BlogEntry(
                "The Great Gatsby",
                "Scot Fitzgerald",
                "The Great Gatsby is a 1925 novel written by American author F. Scott " +
                        "Fitzgerald that follows a cast of characters living in the fictional town " +
                        "of West Egg on prosperous Long Island in the summer of 1922.");
    }
}