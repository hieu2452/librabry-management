import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { BorrowService } from '../../_service/borrow.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { error } from 'console';
import { MATERIAL_MODULDE } from '../../material/material.module';
import { DatePipe } from '@angular/common';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-borrow-detail',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe],
  templateUrl: './borrow-detail.component.html',
  styleUrl: './borrow-detail.component.css'
})
export class BorrowDetailComponent implements OnInit {
  selection = new SelectionModel<any>(true, []);
  bill: any;
  onViewBorrow = new EventEmitter();
  dataSource: any;
  displayedColumns: string[] = ['bookId', 'bookTitle', 'borrowedDate', 'quantity', 'bookStatus', 'edit'];

  constructor(private borrowService: BorrowService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    public dialogRef: MatDialogRef<BorrowDetailComponent>) {
      this.bill = this.dialogData.data;
  }

  ngOnInit(): void {
    this.getBillDetail();
  }

  getBillDetail() {
    this.borrowService.getBillDetail(this.dialogData.data.id).subscribe({
      next: (response: any) => {
        console.log(response)
        this.dataSource = new MatTableDataSource(response)
      },
      error: error => {
        console.log(error);
      }
    })
  }

  handleReturnAction(e: any) {
    this.selection.toggle(e);
  }

  handleEditAction(e: any) {

  }

  handleDeleteAction(e: any) {

  }

}
