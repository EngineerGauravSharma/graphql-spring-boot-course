/**
 * 
 */
package com.microserviceslab.graphql.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microserviceslab.graphql.model.Book;
import com.microserviceslab.graphql.repository.BookRepository;

import graphql.schema.DataFetcher;

/**
 * @author Gaurav Sharma
 */
@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public DataFetcher<CompletableFuture<Book>> getBook() {
		return env -> {
			String bookId = env.getArgument("id");
			return bookRepository.getBook(UUID.fromString(bookId)).toFuture();
		};
	}
	
	public DataFetcher<CompletableFuture<List<Book>>> getBooks() {
		return env -> bookRepository.getBooks().collectList().toFuture();
	}
	
	public DataFetcher<CompletableFuture<String>> createBook() {
		return env -> {
			String name = env.getArgument("name");
			int pages = env.getArgument("pages");
			return bookRepository.createBook(new Book(name, pages)).map(Object::toString).toFuture();
		};
	}
}
