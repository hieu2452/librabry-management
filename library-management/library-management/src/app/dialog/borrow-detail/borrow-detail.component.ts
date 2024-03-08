import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { BorrowService } from '../../_service/borrow.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { error } from 'console';
import { MATERIAL_MODULDE } from '../../material/material.module';
import { DatePipe } from '@angular/common';
import { SelectionModel } from '@angular/cdk/collections';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-borrow-detail',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe],
  templateUrl: './borrow-detail.component.html',
  styleUrl: './borrow-detail.component.css'
})
export class BorrowDetailComponent implements OnInit {
  selection = new SelectionModel<any>(true, []);
  checkOut: any;
  onViewBorrow = new EventEmitter();
  dataSource: any;
  bookIds: any = [];
  displayedColumns: string[] = ['bookId', 'bookTitle', 'borrowedDate','returnedDate','quantity', 'bookStatus', 'edit'];

  constructor(private borrowService: BorrowService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    public dialogRef: MatDialogRef<BorrowDetailComponent>,
    private toastr: ToastrService) {
      this.checkOut = this.dialogData.data;
  }

  ngOnInit(): void {
    this.getBillDetail();
  }

  getBillDetail() {
    this.borrowService.getBillDetail(this.dialogData.data.id).subscribe({
      next: (response: any) => {
        this.dataSource = new MatTableDataSource(response)
      },
      error: error => {
        console.log(error);
      }
    })
  }

  handleReturnAction(e: any) {
    if(this.bookIds.includes(e.bookId)) {
      this.bookIds = this.bookIds.filter((id : any) => e.bookId != id)
      console.log(this.bookIds);
      return;
    }
    this.bookIds.push(e.bookId);
    console.log(this.bookIds);
  }

  handleEditAction(e: any) {

  }

  handleDeleteAction(e: any) {

  }

  handleReturnBook() {
    if(this.bookIds.length > 0) {
      this.borrowService.returnBooks(this.bookIds,this.dialogData.data.id).subscribe({
        next: response => {
          this.toastr.success("Return book successfully");
        },
        error: error => {
          console.log(error);
          this.toastr.error("Something went wrong");
        }
      })
    }
  }

  validateReturn() {
    if(this.bookIds.length > 0) {
      return false;
    }else {
      return true;
    }
  }

}
