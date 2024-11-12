import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideRouter, Routes } from '@angular/router';
import { ConfigurationFormComponent } from './app/configuration-form/configuration-form.component';
import { SystemControlComponent } from './app/system-control/system-control.component'; 
import './polyfills';

// const routes: Routes = [
  
//   // Add more routes here
// ];

// const appConfig = {
//   providers: [
//     provideRouter(routes), // Provide routing configuration
//     // Other providers if needed
//   ]
// };

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
// bootstrapApplication(AppComponent, {
//   providers: [
//     provideRouter(routes),
//     // Other providers
//   ]
// })
//   .catch((err) => console.error(err));
