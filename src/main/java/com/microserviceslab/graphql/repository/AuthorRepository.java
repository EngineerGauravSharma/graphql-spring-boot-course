/**
 * 
 */
package com.microserviceslab.graphql.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.microserviceslab.graphql.model.Author;

import reactor.core.publisher.Mono;

/**
 * @author Gaurav Sharma
 */
@Repository
public class AuthorRepository {
	
	@Autowired
	private DatabaseClient client;
	
	public Mono<UUID> createAuthor(Author author) {
		UUID authorId = UUID.randomUUID();
		author.setId(authorId);
		return client.insert().into(Author.class).using(author).then().thenReturn(authorId);
	}
	
	public Mono<Author> getAuthor(UUID bookId) {
		return client.select().from(Author.class).matching(Criteria.where("book_id").is(bookId)).fetch().one();
	}
}
