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
		
		return articleRepository.findAll();
	}

	public void insertArticle(Article article) {
		articleRepository.post(article);
	}

	public void deleteByArticle(int id) {
		articleRepository.delete(id);
	}
}
