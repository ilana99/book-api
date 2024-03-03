import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter, NgZone } from '@angular/core';
import { ReactiveFormsModule, FormControl, Validators, FormGroup } from '@angular/forms';
import {RouterModule } from '@angular/router';
import { ApiService} from '../api.service';

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './form.component.html',
  styleUrl: './form.component.sass'
})
export class FormComponent {
  
  @Input() type: string = "";
  @Output() formResponse = new EventEmitter<any>();

  bookForm = new FormGroup({
    author : new FormControl('', Validators.required),
    title : new FormControl('', Validators.required),
    synopsis : new FormControl('', Validators.required)
  })

  input = new FormControl('', Validators.required);
  

  inputType: string = 'text';
  addBook: string = '';
  

  constructor(private apiService: ApiService, private zone: NgZone) {}

  ngOnInit(): void {
    this.updateInputType();
  }

  ngOnChanges(): void {
    this.updateInputType();
  }

  getLabel(): string {
  if (this.type === "byId") {
    return "Enter ID";
  } 
  if (this.type === "byAuthor") {
    return "Enter author";
  }  
  if (this.type === "deleteById") {
    return "Enter ID to delete";
  } 
  else {
    return 'no label';
  }
  }

  updateInputType() {
    this.input.setValue("");
    this.inputType = this.type === "byId" || "deleteByID" ? "number" : "text";
  }


  submitForm() {
    if (this.type === 'byId' || this.type === 'byAuthor' || this.type === 'deleteById') {
      const inputValue = this.input.value;
      if (inputValue !== null) {
        if (this.type === "byId") {
          this.apiService.getABookById(inputValue).
          subscribe(response => {
           // console.log("id", response)
            this.formResponse.emit(response);
          } )
        } 
        if (this.type === "byAuthor") {
          this.apiService.getABookByAuthor(inputValue)
          .subscribe(response => {
            //console.log("author", response)
            this.formResponse.emit(response);
          })
        }
        if (this.type === 'deleteById') {
          this.apiService.deleteBook(inputValue)
          .subscribe(response => {
            this.formResponse.emit(response);
          })
        }
      }
      else {
        console.log("error");
      }
    }
    
    if (this.type === 'addBook') {
      const book = this.bookForm.value;
      if (book) {
        this.apiService.addBook(book)
        .subscribe(response => {
          console.log(response);
        })
      }
    } 
  }



}
