package org.example.tutorial4_2.controller;

import org.example.tutorial4_2.entity.Article;
import org.example.tutorial4_2.entity.Blog;
import org.example.tutorial4_2.entity.Role;
import org.example.tutorial4_2.entity.User;
import org.example.tutorial4_2.exceptions.ArticleNotFoundException;
import org.example.tutorial4_2.exceptions.BlogNotFoundException;
import org.example.tutorial4_2.exceptions.RoleNotFoundException;
import org.example.tutorial4_2.exceptions.UserNotFoundException;
import org.example.tutorial4_2.service.ArticleService;
import org.example.tutorial4_2.service.BlogService;
import org.example.tutorial4_2.service.RoleService;
import org.example.tutorial4_2.service.UserService;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final Scanner in;
    private final UserService userService;
    private final RoleService roleService;
    private final ArticleService articleService;

    public BlogController(BlogService blogService, UserService userService, RoleService roleService, ArticleService articleService) {
        this.blogService = blogService;
        this.roleService = roleService;
        this.articleService = articleService;
        this.userService = userService;
        this.in = new Scanner(System.in);
    }

    public void start(){
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View users");
            System.out.println("2. Add new user");
            System.out.println("3. Search user");
            System.out.println("4. Delete user");

            System.out.println("\n--- Blog Menu ---");
            System.out.println("5. View all blogs");
            System.out.println("6. Add new blog");
            System.out.println("7. Search blog");
            System.out.println("8. Delete blog");

            System.out.println("\n--- Role menu ---");
            System.out.println("9. View all roles");
            System.out.println("10. Add new role");
            System.out.println("11. Search role");
            System.out.println("12. Delete role");

            System.out.println("\n--- Article menu ---");
            System.out.println("13. View all articles");
            System.out.println("14. Add new article");
            System.out.println("15. Search article");
            System.out.println("16. Delete article");

            System.out.println("\n0. Exit");
            System.out.print("Choose an option: ");
            String option = in.nextLine();

            switch (option) {
                case "1" -> viewUsers();
                case "2" -> addNewUser();
                case "3" -> searchUser();
                case "4" -> deleteUser();

                case "5" -> viewBlogs();
                case "6" -> addNewBlog();
                case "7" -> searchBlog();
                case "8" -> deleteBlog();

                case "9" -> viewRoles();
                case "10" -> addNewRole();
                case "11" -> searchRole();
                case "12" -> deleteRole();

                case "13" -> viewArticles();
                case "14" -> addNewArticle();
                case "15" -> searchArticle();
                case "16" -> deleteArticle();

                case "0" -> System.exit(0);
                default -> System.out.println("Incorrect input, try again");
            }
        }
    }

    private void viewUsers() {
        if (userService.findAllUsers().isEmpty()){
            System.out.println("No users in the database");
            return;
        }

        for(var u : userService.findAllUsers()){
            System.out.println(u);
        }
    }

    private void addNewUser() {
        System.out.println("Enter email: ");
        String email = in.nextLine();

        User user = new User(email);

        System.out.println("Do you want to assign role to the user?(y/n)");
        String decision = in.nextLine();
        if(decision.equals("y")){
            viewRoles();
            System.out.println("Enter space-separated id(s) of the role you want to assign to the user: ");

            String[] ids = in.nextLine().split("\\s+");

            long[] numbers = new long[ids.length];

            for (int i = 0; i < ids.length; i++) {
                try {
                    numbers[i] = Integer.parseInt(ids[i]);
                } catch (NumberFormatException e) {
                    numbers[i] = 0;
                }
            }

            Set<Role> roles = new HashSet<>();

            for (long number : numbers) {
                Optional<Role> optionalRole = roleService.findRoleById(number);

                optionalRole.ifPresent(roles::add);
            }

            user.setRoles(roles);
        }

        userService.createNewUser(user);

        System.out.println("User has been successfully added to the database");
    }

    private void searchUser() {
        System.out.println("Enter email of the user you want to find: ");
        String email = in.nextLine();

        User user;
        try{
            user = userService.findUser(email);
        } catch(UserNotFoundException e){
            System.out.println("User not found");
            return;
        }

        System.out.println("User found:");
        System.out.println(user);
    }

    private void deleteUser() {
        System.out.println("Enter email of the user you want to delete: ");
        String email = in.nextLine();

        User user;
        try{
            user = userService.findUser(email);
        } catch(UserNotFoundException e){
            System.out.println("User not found");
            return;
        }

        blogService.detachManager(user);
        articleService.detachAuthor(user);

        user.getRoles().clear();

        userService.deleteUser(email);
        System.out.println("User has been successfully deleted");
    }

    private void viewBlogs() {
        if (blogService.findAllBlogs().isEmpty()){
            System.out.println("No blogs in the database");
            return;
        }

        for(var b : blogService.findAllBlogs()){
            System.out.println(b);
        }
    }

    private void addNewBlog(){
        System.out.println("Enter name of the blog: ");
        String name = in.nextLine();

        Blog blog = new Blog(name);

        System.out.println("Do you want to assign manager to this blog?(y/n)");
        String decision = in.nextLine();
        if(decision.equals("y")){
            for (var u : userService.findAllUsers()){
                System.out.println(u);
            }

            System.out.println("Enter id of the user that will be the manager of the blog: ");

            long id = 0;
            try {
                id = in.nextLong();
                in.nextLine();
            } catch (Exception e){
                System.out.println("Incorrect input, no user will be assigned to the blog");
                in.nextLine();
            }

            if (id != 0 && userService.findUserById(id).isPresent()){
                User manager = userService.findUserById(id).get();

                if(manager.getManagedBlog() != null){
                    System.out.println("This user already manages a blog.");
                    return;
                }

                blog.setManager(manager);
            }
        }

        System.out.println("Do you want to add articles to the blog?(y/n)");
        decision = in.nextLine();

        if(decision.equals("y")){
            for (var a : articleService.findAllArticles()){
                System.out.println(a);
            }

            System.out.println("Enter space-separated id(s) of articles you want to add to this blog.");
            String[] ids = in.nextLine().split("\\s+");

            long[] numbers = new long[ids.length];

            for (int i = 0; i < ids.length; i++) {
                try {
                    numbers[i] = Integer.parseInt(ids[i]);
                } catch (NumberFormatException e) {
                    numbers[i] = 0;
                }
            }

            Set<Article> articles = new HashSet<>();

            for (long number : numbers) {
                Optional<Article> optionalArticle = articleService.findArticleById(number);

                if(optionalArticle.isPresent() && optionalArticle.get().getBlog() != null){
                    System.out.println("Article #" + optionalArticle.get().getId() + " already has a blog assigned to it.");
                    return;
                }

                optionalArticle.ifPresent(articles::add);
            }

            blog.setArticles(articles);
        }

        blogService.createNewBlog(blog);
        System.out.println("Blog has been successfully added to the database");
    }

    private void searchBlog() {
        System.out.println("Enter name of the blog: ");
        String name = in.nextLine();

        Blog blog;
        try{
            blog = blogService.findBlog(name);
        } catch(BlogNotFoundException e){
            System.out.println("Blog not found");
            return;
        }

        System.out.println("Blog found:");
        System.out.println(blog);
    }

    private void deleteBlog() {
        System.out.println("Enter name of the blog you want to delete: ");
        String name = in.nextLine();

        try{
            blogService.findBlog(name);
        } catch(BlogNotFoundException e){
            System.out.println("Blog not found");
            return;
        }

        blogService.deleteBlog(name);
        System.out.println("Blog was successfully deleted from the database");
    }

    private void viewRoles() {
        if (roleService.findAllRoles().isEmpty()){
            System.out.println("No roles in the database");
            return;
        }

        for(var r : roleService.findAllRoles()){
            System.out.println(r);
        }
    }

    private void addNewRole() {
        System.out.println("Enter name of the role: ");
        String name = in.nextLine();

        Role role = new Role(name);

        System.out.println("Do you want to add users to this role?(y/n)");
        String decision = in.nextLine();
        if (decision.equals("y")){
            for (var u : userService.findAllUsers()){
                System.out.println(u);
            }

            System.out.println("Enter space-separated id(s) of the users you want to assign to this role: ");
            String[] ids = in.nextLine().split("\\s+");

            long[] numbers = new long[ids.length];

            for (int i = 0; i < ids.length; i++) {
                try {
                    numbers[i] = Integer.parseInt(ids[i]);
                } catch (NumberFormatException e) {
                    numbers[i] = 0;
                }
            }

            Set<User> users = new HashSet<>();

            for (long number : numbers) {
                Optional<User> optionalUser = userService.findUserById(number);

                optionalUser.ifPresent(users::add);
            }

            role.setUsers(users);
        }

        roleService.createNewRole(role);
        System.out.println("Role has been successfully added to the database");
    }

    private void searchRole() {
        System.out.println("Enter name of the role: ");
        String name = in.nextLine();

        Role role;
        try {
            role = roleService.findRole(name);
        } catch (RoleNotFoundException e) {
            System.out.println("Role not found");
            return;
        }

        System.out.println("Role found:");
        System.out.println(role);
    }

    private void deleteRole() {
        System.out.println("Enter name of the role you want to delete: ");
        String name = in.nextLine();

        Role role;
        try {
            role = roleService.findRole(name);
        } catch (RoleNotFoundException e) {
            System.out.println("Role not found");
            return;
        }
        userService.detachRole(role);
        roleService.deleteRole(name);

        System.out.println("Role has been successfully deleted from the database");
    }

    private void viewArticles() {
        if (articleService.findAllArticles().isEmpty()){
            System.out.println("No articles in the database");
            return;
        }

        for(var a : articleService.findAllArticles()){
            System.out.println(a);
        }
    }

    private void addNewArticle() {
        System.out.println("Enter title of the article: ");
        String title = in.nextLine();

        Article article = new Article(title);

        boolean authorAssigned = false;
        while (!authorAssigned){
            for (var u : userService.findAllUsers()){
                System.out.println(u);
            }

            System.out.println("Enter id of the user, who is the author of the article: ");

            long id;
            try {
                id = in.nextLong();
                in.nextLine();
            } catch (Exception e){
                System.out.println("Incorrect input, try again");
                in.nextLine();
                continue;
            }

            if (userService.findUserById(id).isPresent()){
                article.setAuthor(userService.findUserById(id).get());
            } else{
                System.out.println("User wasn't found, try again");
                continue;
            }

            authorAssigned = true;
        }

        boolean blogAssigned = false;
        while (!blogAssigned){
            for (var b : blogService.findAllBlogs()){
                System.out.println(b);
            }
            System.out.println("Enter id of the blog, to which the article will belong to: ");

            long id;
            try {
                id = in.nextLong();
                in.nextLine();
            } catch (Exception e){
                System.out.println("Incorrect input, try again");
                in.nextLine();
                continue;
            }

            if (blogService.findBlogById(id).isPresent()){
                article.setBlog(blogService.findBlogById(id).get());
            } else{
                System.out.println("Blog was not found, try again");
                continue;
            }

            blogAssigned = true;
        }

        articleService.createNewArticle(article);
        System.out.println("Article has been successfully added to the database");
    }

    private void searchArticle(){
        System.out.println("Enter title of the article: ");
        String title = in.nextLine();

        Article article;
        try{
            article = articleService.findArticle(title);
        } catch(ArticleNotFoundException e){
            System.out.println("Article not found");
            return;
        }

        System.out.println("Article found:");
        System.out.println(article);
    }

    private void deleteArticle() {
        System.out.println("Enter title of the article you want to delete: ");
        String title = in.nextLine();

        try{
            articleService.findArticle(title);
        } catch(ArticleNotFoundException e){
            System.out.println("Article not found");
            return;
        }

        articleService.deleteArticle(title);
        System.out.println("Article has been successfully deleted from the database");
    }
}