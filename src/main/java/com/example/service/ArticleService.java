package com.example.service;

import org.springframework.transaction.annotation.Transactional;
import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public List<Article> findAll() {
		
		List<Article> oldArticleList = articleRepository.findAll();
		List<Integer> articleIdList = new ArrayList<Integer>();
		List<Comment> onlyCommentList = new ArrayList<Comment>();
		List<Article> articleList = new ArrayList<Article>();
		
		for(Article article : oldArticleList) {
			Comment comment = new Comment();
			comment.setId(article.getComment().getId());
			comment.setName(article.getComment().getName());
			comment.setContent(article.getComment().getContent());
			comment.setArticleId(article.getComment().getArticleId());
			onlyCommentList.add(comment);
		}
		
		for(Article article: oldArticleList) {
			articleIdList.add(article.getId());
		}
		
		//重複なしのarticleのIDリスト。
		List<Integer> articleIdWithoutDouble = new ArrayList<Integer>(new LinkedHashSet<>(articleIdList));
		
		
		for(Integer number: articleIdWithoutDouble) {
			List<Comment> commentList = new ArrayList<Comment>();
			Article article = new Article();
			for(Article oldArticle: oldArticleList) {
				if(number == oldArticle.getId()) {
					Comment comment = new Comment();
					article.setId(number);
					article.setName(oldArticle.getName());
					article.setContent(oldArticle.getContent());
					comment.setId(oldArticle.getComment().getId());
					comment.setName(oldArticle.getComment().getName());
					comment.setContent(oldArticle.getComment().getContent());
					comment.setArticleId(oldArticle.getComment().getArticleId());
					commentList.add(comment);
				}
			}
			article.setCommentList(commentList);
			articleList.add(article);
		}
		return articleList;
	}

	public void insertArticle(Article article) {
		articleRepository.post(article);
	}

	public void deleteByArticle(int id) {
		articleRepository.delete(id);
	}
}
