package org.example.tutorial4_2.service;

import jakarta.transaction.Transactional;
import org.example.tutorial4_2.entity.Article;
import org.example.tutorial4_2.entity.Blog;
import org.example.tutorial4_2.entity.User;
import org.example.tutorial4_2.exceptions.BlogNotFoundException;
import org.example.tutorial4_2.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> findAllBlogs(){
        return blogRepository.findAll();
    }

    public void createNewBlog(Blog blog){
        blogRepository.save(blog);
    }

    public Blog findBlog(String name) throws BlogNotFoundException {
        return blogRepository.findByName(name).orElseThrow(BlogNotFoundException::new);
    }

    @Transactional
    public void deleteBlog(String name){
        try{
            Blog blog = findBlog(name);

            User manager = blog.getManager();
            if (manager != null) {
                manager.setManagedBlog(null);
                blog.setManager(null);
            }

            blog.setManager(null);

            for (Article article : blog.getArticles()) {
                article.setBlog(null);
            }

            blog.getArticles().clear();

            blogRepository.delete(blog);
        } catch(BlogNotFoundException ignored){}
    }

    public Optional<Blog> findBlogById(long id){
        return blogRepository.findById(id);
    }

    @Transactional
    public void detachManager(User manager){
        Optional<Blog> blog = blogRepository.findByManager(manager);

        if(blog.isPresent()){
            blog.get().setManager(null);
            blogRepository.save(blog.get());
        }
    }
}