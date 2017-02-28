package com.teamtreehouse.blog;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

  public static void main(String[] args) {
    staticFileLocation("/public");

    // TODO: Check cookie for specific word
    before("/new", (req,res) -> {
      if (req.cookie("password") == null) {
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


    get("password", (req, res) -> {
      return new ModelAndView(null, "password.hbs");
    }, new HandlebarsTemplateEngine());

    post("/password", (req,res) -> {
      Map<String, String> model= new HashMap<>();
      String password = req.queryParams("password");
      model.put("password", password);
      res.cookie("password", password);
      res.redirect("/new");
      return new ModelAndView(model, "new.hbs");
    }, new HandlebarsTemplateEngine());
  }
}
