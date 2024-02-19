package com.demo.book.utils;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.entity.Book;
import com.demo.book.service.BookService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BookExcelImporter {
    private final BookService bookService;

    public BookExcelImporter(BookService bookService) {
        this.bookService = bookService;
    }

    public void importBooksFromExcel(MultipartFile multipartFile) {
        List<Book> books = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }

                BookDto bookDto = new BookDto();
                bookDto.setTitle(currentRow.getCell(0).getStringCellValue());
                bookDto.setCategory(currentRow.getCell(1).getStringCellValue());
                bookDto.setPublisher(currentRow.getCell(2).getStringCellValue());
                bookDto.setAuthor(currentRow.getCell(3).getStringCellValue());
                bookDto.setLanguage(currentRow.getCell(4).getStringCellValue());
                bookDto.setQuantity((int) currentRow.getCell(5).getNumericCellValue());


                bookService.createBook(bookDto);
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

    }
}
