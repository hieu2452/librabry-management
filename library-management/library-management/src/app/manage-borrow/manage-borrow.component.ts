import { Component, OnInit } from '@angular/core';
import { BorrowService } from '../_service/borrow.service';
import { MATERIAL_MODULDE } from '../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { DatePipe } from '@angular/common';

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

  constructor(private borrowService: BorrowService) {

  }

  ngOnInit(): void {
    this.getBills();
  }

  getBills() {
    this.borrowService.getBill().subscribe({
      next: (respsone: any) => {
        this.dataSource = new MatTableDataSource(respsone);
      }
    })
  }

  handleEditAction(e: any) {

  }
  handleDeleteAction(e: any) {

  }


}
