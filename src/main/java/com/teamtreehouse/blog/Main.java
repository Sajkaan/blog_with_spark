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


    // TODO: Check cookie for specific word
    before("/new", (req,res) -> {
      if (req.cookie("username") == null || !req.cookie("username").equals("admin")) {
        res.redirect("/password");
        halt();
      }
    });

    get("/", (req,res) -> {
      return new ModelAndView(null, "index.hbs");
    }, new HandlebarsTemplateEngine());

    // TODO:SG - Enable selecting specific elements and displaying it with proper links
    get("/detail", (req, res) -> {
      return new ModelAndView(null, "detail.hbs");
    }, new HandlebarsTemplateEngine());


    // TODO:SG - Make it store new post
    get("/new", (req, res) ->{
      return new ModelAndView(null, "new.hbs");
    }, new HandlebarsTemplateEngine());

    //TODO:SG - Redesign edit to make it not static
    get("/edit", (req,res) -> {
      return new ModelAndView(null, "edit.hbs");
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
