package com.teamtreehouse.blog;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.BlogDaoImpl;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import com.teamtreehouse.blog.model.Tag;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

  public static void main(String[] args) {

    staticFileLocation("/public");

    BlogDao blogDao = new BlogDaoImpl();

    BlogEntry blogEntry1 = new BlogEntry("NOT THAT INTER YOU",
        "Safet Garic",
        "ANTONIO CONTE has been linked with a shock Chelsea exit in favour of a return to Italy.");
    blogEntry1.addComment(new Comment("Jose Mourinho", "Hahahhahhahha."));
    blogEntry1.addTag(new Tag("chelsea coach byebye"));
    blogDao.addEntry(blogEntry1);

    BlogEntry blogEntry2 = new BlogEntry("N'Golo Kante",
        "Eden Hazard",
        "He has been the biggest difference in the title race - and Leicester's demise has shown how important he was to them.");
    blogEntry2.addComment(new Comment("Antonio Conte", "Simply unplayable."));
    blogEntry2.addTag(new Tag("midfielder chelsea"));
    blogDao.addEntry(blogEntry2);

    BlogEntry blogEntry3 = new BlogEntry("TAKING THE VIC",
        "Senad Felix",
        "Moses is now a first team regular at Stamford Bridge after being shipped out on loan earlier in his Blues career");
    blogEntry3.addComment(new Comment("Juan Mata", "Great lad."));
    blogEntry3.addTag(new Tag("Machine Powerful"));
    blogDao.addEntry(blogEntry3);

    before((req, res) -> {
      if (req.cookie("username") != null) {
        req.attribute("username", req.cookie("username"));
      }
    });


    before("/new", (req,res) -> {
      if (req.attribute("username") == null || !req.attribute("username").equals("admin")) {
        res.redirect("/password");
        halt();
      }
    });

    get("/", (req,res) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("blogEntry", blogDao.findAllEntries());

      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get("/detail/:slug", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("blogEntry", blogDao.findEntryBySlug(req.params("slug")));
      return new ModelAndView(model, "detail.hbs");
    }, new HandlebarsTemplateEngine());


    get("/new", (req, res) ->{
      Map<String, String> model = new HashMap<>();

      return new ModelAndView(null, "new.hbs");
    }, new HandlebarsTemplateEngine());

    post("/new", (req, res) -> {
      String title = req.queryParams("title");
      String author = req.queryParams("author");
      String entry = req.queryParams("entry");

      BlogEntry blogEntry = new BlogEntry(title, author, entry);
      blogDao.addEntry(blogEntry);

      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());

    get("details/edit/:slug", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));

      model.put("blogEntry", blogEntry);
      return new ModelAndView(model, "edit.hbs");
    }, new HandlebarsTemplateEngine());

    post("details/edit/:slug", (req, res) -> {
      BlogEntry blogEntry = blogDao.findEntryBySlug(req.params(":slug"));
      String title = req.queryParams("title");
      String author = req.queryParams("author");
      String entry = req.queryParams("entry");

      blogEntry.editEntry(title, author, entry);
      res.redirect("/details" + blogEntry.getSlug());
      return null;
    }, new HandlebarsTemplateEngine());

    get("/password", (req, res) -> {

      return new ModelAndView(null, "password.hbs");
    }, new HandlebarsTemplateEngine());

    post("/password", (req,res) -> {
      Map<String, String> model= new HashMap<>();
      String username = req.queryParams("username");
      model.put("username", username);

      res.cookie("username", username);
      res.redirect("/new");

      return new ModelAndView(model, "new.hbs");
    }, new HandlebarsTemplateEngine());
  }

}
