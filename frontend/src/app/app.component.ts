import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'my-happy-place';
  logged = true; //TODO: Verificar como validar se usuário está logado
  search = '';

  constructor(private router: Router){}

  onEnterPress(search: string) {
    this.router.navigate(['/search', search]);
  }
}
