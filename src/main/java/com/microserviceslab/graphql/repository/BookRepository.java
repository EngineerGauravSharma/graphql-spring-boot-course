/**
 * 
 */
package com.microserviceslab.graphql.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.microserviceslab.graphql.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gaurav Sharma
 */
@Repository
public class BookRepository {
	
	@Autowired
	private DatabaseClient databaseClient;
	
	public Mono<Book> getBook(UUID id) {
		return databaseClient.select().from(Book.class).matching(Criteria.where("id").is(id)).fetch().one();
	}
	
	public Flux<Book> getBooks() {
		return databaseClient.select().from(Book.class).fetch().all();
	}

	public Mono<UUID> createBook(Book book) {
		UUID bookId = UUID.randomUUID();
		book.setId(bookId);
		return databaseClient.insert().into(Book.class).using(book).then().thenReturn(bookId);
	}
}
