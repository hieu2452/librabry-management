import { Component, OnInit } from '@angular/core';
import { BookService } from '../_service/book.service';
import { MatTableDataSource } from '@angular/material/table';
import { MATERIAL_MODULDE } from '../material/material.module';
import { DatePipe, NgFor } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookComponent } from '../dialog/book/book.component';
import { Router, RouterModule } from '@angular/router';
import { CategoryService } from '../_service/category.service';
import { BookParam } from '../_modal/BookParam';

@Component({
  selector: 'app-manage-book',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe, RouterModule, NgFor],
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.css'
})
export class ManageBookComponent implements OnInit {
  books: any = [];
  displayedColumns: string[] = ['id', 'title', 'author', 'category', 'publisher', 'description', 'language', 'quantity', 'addedDate', 'edit'];
  dataSource: any;
  categories: any = [];
  publishers: any = [];
  category: any = null;
  publisher: any = null;
  bookParam = new BookParam();;

  constructor(private bookService: BookService,
    private dialog: MatDialog,
    private router: Router,
    private categoryService: CategoryService) {

  }

  ngOnInit(): void {
    this.getBooks(this.bookParam);
    this.getCategories();
  }

  handleFilter() {
    console.log(this.bookParam)
    this.getBooks(this.bookParam);
  }

  resetFilter() {
    this.bookParam = new BookParam();
    this.getBooks(this.bookParam)
  }

  handleAddAction() {
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      action: 'Add'
    };
    dialogConfog.width = "850px";
    const dialogRef = this.dialog.open(BookComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddProduct.subscribe((response: any) => {
      this.getBooks(this.bookParam);
    })
  }

  getBooks(bookParam: BookParam) {
    this.bookService.getBooks(bookParam).subscribe({
      next: (books: any) => {
        this.dataSource = new MatTableDataSource(books.content);
      }
    })
  }

  getCategories() {
    this.categoryService.getCategories().subscribe({
      next: (response: any) => {
        console.log(response)
        this.categories = response;
      },
      error: error => {
        console.log(error);
      }
    })
    this.categoryService.getpublisher().subscribe({
      next: (response: any) => {
        console.log(response)
        this.publishers = response;
      },
      error: error => {
        console.log(error);
      }
    })
  }

  handleEditAction(e: any) {
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      action: 'Edit',
      data: e
    };
    dialogConfog.width = "850px";
    const dialogRef = this.dialog.open(BookComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onEditProduct.subscribe((response: any) => {
      this.getBooks(this.bookParam);
    })
  }

  handleDeleteAction(e: any) {

  }
}
