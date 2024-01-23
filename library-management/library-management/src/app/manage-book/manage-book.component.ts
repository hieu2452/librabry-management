import { Component, OnInit } from '@angular/core';
import { BookService } from '../_service/book.service';
import { MatTableDataSource } from '@angular/material/table';
import { MATERIAL_MODULDE } from '../material/material.module';
import { DatePipe } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookComponent } from '../dialog/book/book.component';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-manage-book',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe, RouterModule],
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.css'
})
export class ManageBookComponent implements OnInit {
  books: any = [];
  displayedColumns: string[] = ['id', 'title', 'author', 'category', 'subtitle', 'description', 'language', 'quantity', 'addedDate', 'edit'];
  dataSource: any;
  constructor(private bookService: BookService, private dialog: MatDialog, private router: Router) {

  }
  ngOnInit(): void {
    this.getBooks();
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
      this.getBooks();
    })
  }
  getBooks() {
    this.bookService.getBooks().subscribe({
      next: (books: any) => {
        this.dataSource = new MatTableDataSource(books);
      }
    })
  }
  handleEditAction(e: any) {

  }

  handleDeleteAction(e: any) {

  }
}
