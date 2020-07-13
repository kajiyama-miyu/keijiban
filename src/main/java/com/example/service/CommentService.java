package com.example.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	
	public List<Comment> findbyArticleId(int articleId){
		return commentRepository.findByArticleId(articleId);
	}
	
	public void insertComment(Comment comment) {
		commentRepository.postComment(comment);
	}
	
	public void deleteByCommnet(int articleId) {
		commentRepository.deleteComment(articleId);
	}
	
}
