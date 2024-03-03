import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiUrl = "http://localhost:8080";

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    const url = `${this.apiUrl}/auth/login`
    const body = {"username": username, "password": password};
    return this.http.post(url, body);
  }

  getAllBooks() { 
    const url = `${this.apiUrl}/book/all`;
    return this.http.get(url);
  }

  getABookById(id: string) {
    const url = `${this.apiUrl}/book/${id}`;
    return this.http.get(url);
  }

  getABookByAuthor(author: string) {
    const url = `${this.apiUrl}/book/byAuthor`;
    const params = new HttpParams().set('author', author);
    return this.http.get(url, {params});
  }

  addBook(book: any) {
    const url = `${this.apiUrl}/book/add`;
    const params = new HttpParams().set('user', 'test');
    return this.http.post(url, book, {params});
  }

  deleteBook(id: string) {
    const  url = `${this.apiUrl}/book/${id}`;
    return this.http.delete(url);
  }



}
