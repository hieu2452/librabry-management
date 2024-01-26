import { Component, EventEmitter, Inject } from '@angular/core';
import { MATERIAL_MODULDE } from '../../material/material.module';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { BookService } from '../../_service/book.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { error } from 'console';
import { NgFor, NgIf } from '@angular/common';
import { CategoryService } from '../../_service/category.service';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [MATERIAL_MODULDE, ReactiveFormsModule, NgIf, NgFor],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent {
  onAddProduct = new EventEmitter();
  onEditProduct = new EventEmitter();
  bookForm: any = FormGroup;
  languages = ['English', 'Vietnamese']
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;
  categories: any = [];
  publishers: any = [];
  display: FormControl = new FormControl("", Validators.required);
  file_store: FileList | undefined;
  file_list: Array<string> = [];
  selectedFile: any = undefined;
  data = {};
  selected: any;


  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBulider: FormBuilder,
    protected bookService: BookService,
    public dialogRef: MatDialogRef<BookComponent>,
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.bookForm = this.formBulider.group({
      id: [null],
      title: [null, [Validators.required]],
      author: [null, [Validators.required]],
      category: [null, Validators.required],
      language: [null, Validators.required],
      quantity: [null, Validators.required],
      description: [null],
      publisher: [null, Validators.required],
    });
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Edit";
      this.bookForm.patchValue(this.dialogData.data);
      // console.log(this.dialogData.data.category)
      this.selected = this.dialogData.data.category;
      this.bookForm.controls['category'].setValue(this.dialogData.data.category.categoryName)
      this.bookForm.controls['publisher'].setValue(this.dialogData.data.publisher.name)
    }



    this.getCategories();
  }

  handleFileInputChange(l: FileList): void {
    this.file_store = l;
    if (l.length) {
      const f = l[0];
      const count = l.length > 1 ? `(+${l.length - 1} files)` : "";
      this.display.patchValue(`${f.name}${count}`);
    } else {
      this.display.patchValue("");
    }
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

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.selectedFile = file;
    }
  }

  validateForm() {
    if (this.action == "Add") {
      if (!(this.bookForm.valid && this.bookForm.dirty)) {
        return true
      }
    }
    if (this.action == "Edit") {
      if (!(this.bookForm.valid)) {
        return true
      }
    }

    return false
  }


  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    } else {
      this.add();
    }
  }
  add() {
    const form_Data: FormData = new FormData();
    var formData = this.bookForm.value;
    var data = { ...formData }
    console.log(data)
    if (this.file_store) {
      form_Data.append('file', this.file_store[0]);
    }

    form_Data.append('model', JSON
      .stringify(data));

    this.bookService.addBook(form_Data).subscribe({
      next: (response: any) => {
        this.dialogRef.close();
        this.onAddProduct.emit();
      },
      error: error => {
        this.dialogRef.close();
        console.log(error)
      }
    });

  }

  edit() {
    const form_Data: FormData = new FormData();
    var formData = this.bookForm.value;

    var data = { ...formData }
    console.log(data)
    if (this.file_store) {
      form_Data.append('file', this.file_store[0]);
    }

    form_Data.append('model', JSON
      .stringify(data));

    this.bookService.updateBook(form_Data).subscribe({
      next: (response: any) => {
        this.dialogRef.close();
        this.onEditProduct.emit();
      },
      error: error => {
        this.dialogRef.close();
        console.log(error)
      }
    });
  }
}
