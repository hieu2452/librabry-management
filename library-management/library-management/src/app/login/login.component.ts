import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../_service/auth.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  form: any = FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {


  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    })
  }

  handleSubmit() {
    this.authService.login(this.form.value).subscribe({
      next: (response: any) => {
        console.log(response);
        this.router.navigate(["/dashboard/book"]);
      },
      error: error => {
        console.log(error.error.detail)
      }
    })
  }

}
