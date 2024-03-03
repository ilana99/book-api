package com.example.mysqltest.book;


import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(path="/book")
public class BookController {
	@Autowired
	
	private BookRepository bookRepository;
	
	
	@PostMapping(path="/add")
	public ResponseEntity<Book> addNewBook(@RequestBody Book book, @RequestParam String user) {	
		String title = book.getTitle();
		String author = book.getAuthor();
		String synopsis = book.getSynopsis();
		
		Book n = new Book();
		n.setUser(user);
		n.setAuthor(author);
		n.setTitle(title);
		n.setSynopsis(synopsis);
		
		Book addedBook = bookRepository.save(n);
		
	
		return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
	}
	
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getBookById(@PathVariable Integer id) {
	    Optional<Book> bookOptional = bookRepository.findById(id);

	    if (bookOptional.isPresent()) {
		    	Iterable<Book> bookIterable = Collections.singleton(bookOptional.get());
		    	return new ResponseEntity<>(bookIterable, HttpStatus.OK);
	    } else {
	    		return new ResponseEntity<>("Book not found for id: " + id, HttpStatus.NOT_FOUND);
	    }
	}
	
	
	
	@PatchMapping(path="/{id}")
	public  ResponseEntity<?> modifyBookDetails(@RequestBody Map<String, Object> updates, @PathVariable Integer id) {
		 Optional<Book> existingBookOptional = bookRepository.findById(id);
		 
		 
		 if (existingBookOptional.isPresent()) {
			 Book existingBook = existingBookOptional.get();
			 
			 try {
				 for (Map.Entry<String, Object> entry : updates.entrySet()) {
			            String key = entry.getKey();
			            Object value = entry.getValue();
    
			            Field field = Book.class.getDeclaredField(key);
		                field.setAccessible(true);
		                field.set(existingBook, value);
			        }
				  bookRepository.save(existingBook);
				  return new ResponseEntity<>(existingBook, HttpStatus.OK); 
			 } catch (NoSuchFieldException | IllegalAccessException e){
				 return new ResponseEntity<>("Error updating book" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			 }
		 } else {
			 return new ResponseEntity<>("Book not found for id: " + id, HttpStatus.NOT_FOUND);
		 }
		 
	}
	

	// 
	

	@DeleteMapping(path="/{id}") 
	public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
		Optional<Book> existingBookOptional = bookRepository.findById(id);
		
		if (existingBookOptional.isPresent()) {
			Book existingBook = existingBookOptional.get();
			bookRepository.delete(existingBook);
			return new ResponseEntity<>("Book deleted!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Book not found!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(path="/all")
	public  @ResponseBody Iterable<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	@GetMapping(path="/byAuthor")
	public @ResponseBody Iterable<Book> getAllBooksByAuthor(@RequestParam String author) {
		return bookRepository.findByAuthor(author);
	}
	
	
}
