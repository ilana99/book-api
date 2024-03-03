import { AuthService } from './auth.service';
import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.sass',
    imports: [RouterOutlet, RouterLink, CommonModule]
})
export class AppComponent {

  constructor(public authService: AuthService) {}

  title = 'Book';


  
  
}
