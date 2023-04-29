import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'user', component: UserComponent },
  { path: 'search/:search', component: SearchResultsComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
