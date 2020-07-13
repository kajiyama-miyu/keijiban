package com.example.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public List<Article> findAll() {
		List<Article> articleList = articleRepository.findAll();
		Map<Integer, Comment> commentMap = new HashMap<Integer, Comment>();
		for (Article article : articleList) {
			commentMap.put(article.getId(), new Comment());
		}

		for (Article article : articleList) {
			Comment comment = commentMap.get(article.getId());
			comment.setComment(article.getComment());
		}

		Map<Integer, Article> articleMap = new HashMap<Integer, Article>();
		for (Article article : articleList) {
			article.setComment(commentMap.get(article.getId()));
			articleMap.put(article.getId(), article);
		}

		List<Article> articles = new ArrayList<>(articleMap.values());

		return articles;
	}

	public void insertArticle(Article article) {
		articleRepository.post(article);
	}

	public void deleteByArticle(int id) {
		articleRepository.delete(id);
	}
}
