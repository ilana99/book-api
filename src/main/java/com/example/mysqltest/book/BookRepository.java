package com.example.mysqltest.book;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.mysqltest.book.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
	List<Book> findByAuthor(String author);
	List<Book> findAll();
}
