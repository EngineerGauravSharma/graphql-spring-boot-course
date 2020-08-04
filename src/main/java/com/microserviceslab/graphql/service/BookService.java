/**
 * 
 */
package com.microserviceslab.graphql.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microserviceslab.graphql.constant.Category;
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
	@Autowired
	private AuthorService authorService;
	
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
			String bookName = env.getArgument("bookName");
			String authorName = env.getArgument("authorName");
			
			int pages = env.getArgument("pages");
			int age = env.getArgument("age");
			Category category = Category.valueOf(env.getArgument("category"));
			
			Book book = new Book();
			book.setName(bookName);
			book.setPages(pages);
			book.setCategory(category);
			
			return bookRepository.createBook(book).flatMap(
					bookId -> authorService.createAuthor(authorName, age, bookId)
					.map(authorId -> bookId.toString()))
			.toFuture();
		};
	}
}
