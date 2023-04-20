import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@NgModule({
  declarations: [],
  exports: [MatToolbarModule, MatCardModule, MatButtonModule, MatDividerModule],
})
export class AppMaterialModule {}
