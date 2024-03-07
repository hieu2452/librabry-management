import { Component, OnInit } from '@angular/core';
import { BorrowService } from '../_service/borrow.service';
import { MATERIAL_MODULDE } from '../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { DatePipe } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BorrowDetailComponent } from '../dialog/borrow-detail/borrow-detail.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-borrow',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe],
  templateUrl: './manage-borrow.component.html',
  styleUrl: './manage-borrow.component.css'
})
export class ManageBorrowComponent implements OnInit {

  dataSource: any;
  displayedColumns: string[] = ['id', 'createdDate', 'fullname', 'status', 'edit'];

  constructor(private borrowService: BorrowService, 
    private dialog: MatDialog,
    private router: Router,) {

  }

  ngOnInit(): void {
    this.getBills();
  }

  getBills() {
    this.borrowService.getBill().subscribe({
      next: (respsone: any) => {
        console.log(respsone)
        this.dataSource = new MatTableDataSource(respsone);
      }
    })
  }

  handleEditAction(e: any) {

  }
  handleDeleteAction(e: any) {

  }
  handleViewAction(e: any) {
    const dialogConfog = new MatDialogConfig();
    dialogConfog.data = {
      action: 'View',
      data: e
    };
    dialogConfog.width = "850px";
    dialogConfog.height = "60%";
    const dialogRef = this.dialog.open(BorrowDetailComponent, dialogConfog);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onViewBorrow.subscribe((response: any) => {
      // this.getBooks(this.bookParam);
    })
  }

}
