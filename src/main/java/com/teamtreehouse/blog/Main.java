package com.teamtreehouse.blog;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.CommentDao;
import com.teamtreehouse.blog.dao.Sql2oBlogDao;
import com.teamtreehouse.blog.dao.Sql2oCommentDao;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import com.teamtreehouse.blog.model.Tag;

import java.util.HashMap;
import java.util.Map;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

  private static final String FLASH_MESSAGE_KEY = "flash_message";

  public static void main(String[] args) {

    staticFileLocation("/public");

    String datasource = "jdbc:h2:./blog.db";

    Sql2o sql2o = new Sql2o(
            String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource),
            "", ""
    );
    BlogDao blogDao = new Sql2oBlogDao(sql2o);
    CommentDao commentDao = new Sql2oCommentDao(sql2o);


    before((req, res) -> {
      if (req.cookie("username") != null) {
        req.attribute("username", req.cookie("username"));
      }
    });

    passwordProtection("/new");
    passwordProtection("/detail/:slug/edit");

    get("/", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("blogEntry", blogDao.findAllEntries());
      model.put("flashMessage", captureFlashMessage(req));
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get("/new", (req, res) -> {
      return new ModelAndView(null, "new.hbs");
    }, new HandlebarsTemplateEngine());

    post("/new", (req, res) -> {
      String title = req.queryParams("title");
      String author = req.queryParams("author");
      String entry = req.queryParams("entry");

      BlogEntry blogEntry = new BlogEntry(title, author, entry);
      blogDao.addEntry(blogEntry);
      setFlashMessage(req, "New entry added.");
      res.redirect("/");
      return null;
    });

    get("/password", (req, res) -> {
      Map<String, String> model = new HashMap<>();

      model.put("flashMessage", captureFlashMessage(req));
      return new ModelAndView(model, "password.hbs");
    }, new HandlebarsTemplateEngine());

    post("/password", (req, res) -> {
      Map<String, String> model = new HashMap<>();
      String username = req.queryParams("username");
      model.put("username", username);

      res.cookie("username", username);
      res.redirect("/new");

      return new ModelAndView(model, "new.hbs");
    }, new HandlebarsTemplateEngine());

    get("/detail/:id/:slug", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int id = Integer.parseInt(req.params("id"));
      BlogEntry blogEntry = blogDao.findEntryById(id);
      model.put("blogEntry", blogEntry);
      return new ModelAndView(model, "detail.hbs");
    }, new HandlebarsTemplateEngine());

    post("/detail/:slug/comment", (req, res) -> {
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
      String author = req.queryParams("author");
      String commentText = req.queryParams("comment");

      if (author.equals("")) {
        author = "Anonymous";
      }
      Comment comment = new Comment(blogEntry.getId(),author, commentText);
      commentDao.addComment(comment);
      res.redirect("/detail/" + blogEntry.getSlug());
      return null;
    });

    get("detail/:slug/edit", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));

      model.put("blogEntry", blogEntry);
      return new ModelAndView(model, "edit.hbs");
    }, new HandlebarsTemplateEngine());

    post("detail/:slug/edit", (req, res) -> {
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));
      String title = req.queryParams("title");
      String author = req.queryParams("author");
      String entry = req.queryParams("entry");
      setFlashMessage(req, "Entry updated");
      blogEntry.editEntry(title, author, entry);
      res.redirect("/");
      return null;
    });

    post("detail/:slug/edit/delete", (req, res) -> {
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
      blogDao.deleteEntry(blogEntry.getId());
      setFlashMessage(req, "Entry deleted.");
      res.redirect("/");
      return null;
    });

    post("detail/:slug/edit/tag", (req, res) -> {
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params("slug"));
      String tag = req.queryParams("tag");
/*      blogEntry.addTag(new Tag(tag));*/
      setFlashMessage(req, "Tag added");
      res.redirect("/");
      return null;
    });
  }

  private static void passwordProtection(String path) {
    before(path, (req, res) -> {
      if (req.attribute("username") == null) {
        setFlashMessage(req, "You must be signed in.");
        res.redirect("/password");
        halt();
      }

      if (!req.attribute("username").equals("admin")) {
        setFlashMessage(req, "Incorrect username.");
        res.redirect("/password");
        halt();
      }
    });
  }

  private static void setFlashMessage(Request request, String message) {
    request.session().attribute(FLASH_MESSAGE_KEY, message);
  }

  private static String getFlashMessage(Request request) {
    if (request.session(false) == null) {
      return null;
    }
    if (!request.session().attributes().contains(FLASH_MESSAGE_KEY)) {
      return null;
    }
    return (String) request.session().attribute(FLASH_MESSAGE_KEY);
  }

  private static String captureFlashMessage(Request request) {
    String message = getFlashMessage(request);
    if (message != null) {
      request.session().removeAttribute(FLASH_MESSAGE_KEY);
    }
    return message;
  }

}
