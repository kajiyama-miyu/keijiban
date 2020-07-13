package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.service.ArticleService;
import com.example.service.CommentService;

@Controller
@RequestMapping("/keijiban")
public class ArticleController {
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("")
	public String index(Model model) {
		//List<Article> articleList = 
		System.out.println("indexメソッド");
		List<Article> articleList = articleService.findAll();
		model.addAttribute("articleList", articleList);
		return "index";
	}
	
	@RequestMapping("/post-article")
	public String postArticle(ArticleForm form){
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		articleService.insertArticle(article);
		
		return "redirect:/keijiban";
	}
	
	@RequestMapping("/post-comment")
	public String postComment(CommentForm form) {
		Comment comment = new Comment();
		comment.setName(form.getName());
		comment.setContent(form.getContent());
		Integer articleId = Integer.parseInt(form.getArticleId());
		comment.setArticleId(articleId);
		commentService.insertComment(comment);
		return "redirect:/keijiban";
		
	}
}
