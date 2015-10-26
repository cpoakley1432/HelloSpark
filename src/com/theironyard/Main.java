package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// writes your code here
        ArrayList<User> users = new ArrayList();//creating list to hold users
        Spark.staticFileLocation("/public");
        Spark.init();
        Spark.post(
                "/create-account",
                ((request, response) -> {
                    User user = new User();//creating new user
                    user.name = request.queryParams("username");
                    user.password = request.queryParams("password");
                    users.add(user);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.get(
                "/accounts",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("count", users.size());
                    m.put("accounts", users);
                    return new ModelAndView(m, "accounts.html");
                }),
                new MustacheTemplateEngine()
        );
    }
}
