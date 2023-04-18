import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [],
  exports: [MatToolbarModule, MatCardModule, MatButtonModule],
})
export class AppMaterialModule {}
