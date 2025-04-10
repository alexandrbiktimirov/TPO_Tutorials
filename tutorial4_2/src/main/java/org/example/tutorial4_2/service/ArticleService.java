package org.example.tutorial4_2.service;

import jakarta.transaction.Transactional;
import org.example.tutorial4_2.entity.Article;
import org.example.tutorial4_2.entity.User;
import org.example.tutorial4_2.exceptions.ArticleNotFoundException;
import org.example.tutorial4_2.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAllArticles(){
        return articleRepository.findAll();
    }

    public void createNewArticle(Article article){
        articleRepository.save(article);
    }

    public Article findArticle(String title) throws ArticleNotFoundException {
        return articleRepository.findByTitle(title).orElseThrow(ArticleNotFoundException::new);
    }

    @Transactional
    public void deleteArticle(String title){
        try{
            articleRepository.delete(findArticle(title));
        } catch (ArticleNotFoundException ignored) {}
    }

    public Optional<Article> findArticleById(long id){
        return articleRepository.findById(id);
    }

    @Transactional
    public void detachAuthor(User user) {
        List<Article> articles = articleRepository.findAllByAuthor(user);

        for (Article article : articles) {
            article.setAuthor(null);
        }

        articleRepository.saveAll(articles);
    }
}
