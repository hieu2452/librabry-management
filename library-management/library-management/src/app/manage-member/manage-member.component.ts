import { Component, OnInit } from '@angular/core';
import { MemberService } from '../_service/member.service';
import { MATERIAL_MODULDE } from '../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-manage-member',
  standalone: true,
  imports: [MATERIAL_MODULDE, DatePipe],
  templateUrl: './manage-member.component.html',
  styleUrl: './manage-member.component.css'
})
export class ManageMemberComponent implements OnInit {
  members: any = [];
  displayedColumns: string[] = ['id', 'userType', 'fullName', 'libraryCard', 'age', 'email', 'address', 'createdDate'];
  dataSource: any;
  constructor(private memberService: MemberService) {

  }
  ngOnInit(): void {
    this.getMember()
  }

  getMember() {
    this.memberService.getMembers().subscribe({
      next: (response: any) => {
        if (response) {
          this.members = response;
          this.dataSource = new MatTableDataSource(response);
        }
      }
    })
  }
  handleEditAction(e: any) {

  }
  handleDeleteAction(e: any) {

  }
}
