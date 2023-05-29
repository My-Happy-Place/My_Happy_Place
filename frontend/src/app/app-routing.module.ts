import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { ContentPageComponent } from './components/content-page/content-page.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'user', component: UserComponent, title: 'My Happy Place' },
  { path: 'search/:search', component: SearchResultsComponent, title: 'Busca' },
  { path: ':media-type/:id', component: ContentPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
