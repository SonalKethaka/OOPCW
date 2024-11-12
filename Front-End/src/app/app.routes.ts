import { Routes } from '@angular/router';
import { ConfigurationFormComponent } from './configuration-form/configuration-form.component';
import { SystemControlComponent } from './system-control/system-control.component';

export const routes: Routes = [
    { path: '', component: ConfigurationFormComponent },
  { path: 'control', component: SystemControlComponent },
];
