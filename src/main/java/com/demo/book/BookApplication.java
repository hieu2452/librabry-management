package com.demo.book;

import com.demo.book.entity.Book;
import com.demo.book.entity.LectureBook;
import com.demo.book.entity.ScienceBook;
import com.demo.book.repository.LecturerBookRepository;
import com.demo.book.repository.ScienceBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookApplication implements CommandLineRunner {
	@Autowired
	private ScienceBookRepository scienceBookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		for(int i = 1; i <= 500 ; i++) {
//			ScienceBook scienceBook = new ScienceBook.BookBuilder("Science Book " + i,35000,"Jason " + i).build();
//			scienceBookRepository.save(scienceBook);
//		}

	}
}
