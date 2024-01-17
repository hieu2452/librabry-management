package com.demo.book;

import com.demo.book.entity.Book;
import com.demo.book.entity.LecturerBook;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.LecturerBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@EnableAsync
public class BookApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
