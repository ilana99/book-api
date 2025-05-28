package com.example.mysqltest.book;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/book")
public class BookController {
	@Autowired

	private BookRepository bookRepository;

	@PostMapping(path = "/add")
	public ResponseEntity<BookDTO> addNewBook(@Valid @RequestBody BookDTO book) {
		String title = book.getTitle();
		String author = book.getAuthor();
		String synopsis = book.getSynopsis();

		Book n = new Book();
		n.setAuthor(author);
		n.setTitle(title);
		n.setSynopsis(synopsis);

		bookRepository.save(n);

		BookDTO dto = new BookDTO();
		dto.setAuthor(n.getAuthor());
		dto.setSynopsis(n.getSynopsis());
		dto.setTitle(n.getTitle());
		dto.setId(n.getId());

		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
		Optional<Book> bookOptional = bookRepository.findById(id);
		Book book = bookOptional.orElseThrow(() -> new RuntimeException("book not found with id:" + id));

		BookDTO dto = new BookDTO();
		dto.setAuthor(book.getAuthor());
		dto.setSynopsis(book.getSynopsis());
		dto.setTitle(book.getTitle());
		dto.setId(book.getId());

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<BookDTO> modifyBookDetails(@RequestBody Map<String, Object> updates, @PathVariable Integer id)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Optional<Book> existingBookOptional = bookRepository.findById(id);
		Book book = existingBookOptional.orElseThrow(() -> new RuntimeException("book not found with id:" + id));

		for (Map.Entry<String, Object> entry : updates.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			Field field = Book.class.getDeclaredField(key);
			field.setAccessible(true);
			field.set(book, value);
		}
		bookRepository.save(book);

		BookDTO dto = new BookDTO();
		dto.setAuthor(book.getAuthor());
		dto.setSynopsis(book.getSynopsis());
		dto.setTitle(book.getTitle());
		dto.setId(book.getId());

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
		Optional<Book> existingBookOptional = bookRepository.findById(id);
		Book book = existingBookOptional.orElseThrow(() -> new RuntimeException("book not found with id:" + id));

		bookRepository.delete(book);
		return new ResponseEntity<>("Book deleted!", HttpStatus.OK);

	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		List<BookDTO> bookListDTO = books.stream()
		.map(
			book ->	{
				BookDTO dto = new BookDTO();
				dto.setId(book.getId());
				dto.setTitle(book.getTitle());
				dto.setAuthor(book.getAuthor());
				dto.setSynopsis(book.getSynopsis());
				return dto;
			})
		.collect(Collectors.toList());
		
		return new ResponseEntity<>(bookListDTO, HttpStatus.OK);
	}

	@GetMapping(path = "/byAuthor")
	public ResponseEntity<List<BookDTO>> getAllBooksByAuthor(@RequestParam String author) {
		List<Book> books = bookRepository.findByAuthor(author);
		List<BookDTO> bookListDTO = books.stream()
		.map(
			book ->	{
				BookDTO dto = new BookDTO();
				dto.setId(book.getId());
				dto.setTitle(book.getTitle());
				dto.setAuthor(book.getAuthor());
				dto.setSynopsis(book.getSynopsis());
				return dto;
			})
		.collect(Collectors.toList());
		
		return new ResponseEntity<>(bookListDTO, HttpStatus.OK);
	}

}
