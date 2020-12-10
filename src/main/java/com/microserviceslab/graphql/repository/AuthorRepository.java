/**
 * 
 */
package com.microserviceslab.graphql.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;

import com.microserviceslab.graphql.model.Author;

import reactor.core.publisher.Mono;

/**
 * @author Gaurav Sharma
 */
@Repository
public class AuthorRepository {
	
	@Autowired
	private R2dbcEntityTemplate template;
	
	public Mono<UUID> createAuthor(Author author) {
		UUID authorId = UUID.randomUUID();
		author.setId(authorId);
		return template.insert(Author.class).using(author).thenReturn(authorId);
	}
	
	public Mono<Author> getAuthor(UUID bookId) {
		return template.select(Author.class).matching(Query.query(Criteria.where("book_id").is(bookId))).one();
	}
}
