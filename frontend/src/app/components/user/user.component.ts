import { ContentService } from 'src/app/services/content/content.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  contentData: any;

  //TODO: retornar usuário logado
  user: String = 'Nicolas';

  //TODO: Criar serviço que retorne JSON com conteúdos para os cards
  items = [
    { title: 'Card 1', content: 'Content 1' },
    { title: 'Card 2', content: 'Content 2' },
    { title: 'Card 3', content: 'Content 3' },
    { title: 'Card 4', content: 'Content 4' },
    { title: 'Card 5', content: 'Content 5' }
  ];

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.contentService.getContentData().subscribe((data) => {
      this.contentData = data;
    });
  }
}
