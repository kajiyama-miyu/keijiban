package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class ArticleRepository {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate templete;
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER 
	= (rs, i) -> {
		Article article = new Article();
		Comment comment = new Comment();
		int id = rs.getInt("artid");
		article.setId(id);
		article.setName(rs.getString("artname"));
		article.setContent(rs.getString("artcontent"));
		comment.setId(rs.getInt("comid"));
		comment.setName(rs.getString("comname"));
		comment.setContent(rs.getString("comcontent"));
		comment.setArticleId(rs.getInt("comarticleid"));
		article.setComment(comment);
		return article;
	};
	
	
	public List<Article> findAll(){
		String sql = "select art.id as artid,art.name as artname,art.content as artcontent,com.id as comid,com.name as comname,com.content as comcontent,com.article_id as comarticleid from articles as art left outer join comments as com on art.id = com.article_id order by art.id desc,com.id desc";
		List<Article> articleList = templete.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
	
	public void post(Article article) {
		String sql = "insert into articles (name,content) values(:name,:content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		templete.update(sql, param);
	}
	
	public void delete(int id) {
		commentRepository.deleteComment(id);
		String sql = "delete from articles where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		templete.update(sql, param);
	}
}
