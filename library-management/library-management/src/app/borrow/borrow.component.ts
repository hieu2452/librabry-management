import { Component, OnInit } from '@angular/core';
import { BookComponent } from '../dialog/book/book.component';
import { ManageBookComponent } from '../manage-book/manage-book.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MATERIAL_MODULDE } from '../material/material.module';
import { MemberService } from '../_service/member.service';
import { error } from 'console';
import { DataSource } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { BorrowService } from '../_service/borrow.service';
import e, { response } from 'express';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-borrow',
  standalone: true,
  imports: [ManageBookComponent, ReactiveFormsModule, MATERIAL_MODULDE, FormsModule],
  templateUrl: './borrow.component.html',
  styleUrl: './borrow.component.css'
})
export class BorrowComponent implements OnInit {
  userForm = {
    cardId: null,
    userId: '',
    fullName: '',
    phoneNumber: '',
    address: '',
    email: '',
  }
  books: any = [];
  dataSource: any;
  displayedColumns: string[] = ['id', 'title', 'quantity', 'edit'];

  constructor(private formBulider: FormBuilder,
    private memberService: MemberService,
    private borrowService: BorrowService,
    private toastr: ToastrService) {

  }

  ngOnInit(): void {

  }

  onChange(e: any) {
    console.log(this.userForm.cardId)
    this.memberService.getMemberByLibraryCard(this.userForm.cardId!).subscribe({
      next: (resposne: any) => {
        console.log(resposne)
        this.userForm.userId = resposne.id;
        this.userForm.fullName = resposne.fullName;
        this.userForm.phoneNumber = resposne.phoneNumber;
        this.userForm.address = resposne.address;
        this.userForm.email = resposne.email;
      },
      error: error => {
        this.userForm = {
          userId: '',
          cardId: null,
          fullName: '',
          phoneNumber: '',
          address: '',
          email: '',
        }
        console.log(error);
      }
    })
  }

  addBook(e: any) {
    if (this.books.some((b: any) => b.id === e.id)) return;
    const item: any = { ...e, quantity: 1 }
    this.books.push(item);
    this.dataSource = new MatTableDataSource(this.books);
  }

  handleAddAction() {
    const books: any = [];

    for (let book of this.books) {
      let item: any = {};
      item.bookId = book.id;
      item.quantity = book.quantity;
      books.push(item);
    }

    const borrowRequest = {
      userId: this.userForm.userId,
      books: books
    }

    this.borrowService.borrowBooks(borrowRequest).subscribe({
      next: (response: any) => {
        this.toastr.success("Borrow book successfully");
        console.log(response);
      },
      error: error => {
        this.toastr.error("Somthing went wrong");
        console.log(error);
      }
    })
  }

  handleRemoveAction(e: any) {
    this.books = this.books.filter((b: any) => b.id !== e.id)
    this.dataSource = new MatTableDataSource(this.books);
  }

  validateForm() {
    if (this.userForm.userId == '' || this.userForm.fullName == '' || this.books.length < 0) {
      return true;
    }
    return false
  }
}
