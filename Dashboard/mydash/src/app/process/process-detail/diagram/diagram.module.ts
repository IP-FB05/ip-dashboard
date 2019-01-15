import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// Import Models

// Import Components
import { DiagramComponent } from './diagram.component';

// Import Services

@NgModule({
  declarations: [DiagramComponent],
  exports: [DiagramComponent],
  imports: [
    BrowserModule
  ]
})
export class DiagramModule {}