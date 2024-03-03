import { Component } from '@angular/core';
import { ApiService } from '../api.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormComponent } from '../form/form.component';
import { BookDetailsComponent } from '../book-details/book-details.component';



@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, FormComponent, BookDetailsComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.sass'
})
export class DashboardComponent {

  byId: string = "byId";
  byAuthor: string = "byAuthor";
  books: any = '';
  showComponent = false;
  showForm = false;
  formType: string = "";


  constructor(private apiService: ApiService) { }

  onGetAllBooks(): void {
    this.apiService.getAllBooks()
      .subscribe(
        response => {
          this.books = response;
          this.showForm = false;
          this.showComponent = true;
        },
        (error) => {
          console.error('Error fetching books:', error);
        }
      );
  }
  
  toggleForm(type: string) {
    this.clearBookDetails();
    this.formType = type;
    this.showForm = true;
  }

  handleResponse(response: any) {
    this.books = response;
    this.showForm = true;
    this.showComponent = true;
  }

  clearBookDetails() {
    this.books = null;
  }
}