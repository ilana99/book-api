package com.example.mysqltest.book;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookDTO {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Integer id;
		private String title;
		private String author;
		@Size(max = 500)
		private String synopsis; 
		
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
		
		public String getTitle() {
			return title;
		}
		
		public void setAuthor(String author) {
			this.author = author;
			}
		
		public String getAuthor() {
			return author;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getSynopsis() {
			return synopsis;
		}
		
		public void setSynopsis(String synopsis) {
			this.synopsis = synopsis;
		}

		
		


}
