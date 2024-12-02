import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfigurationService } from '../configuration.service';
import { Configuration } from '../configuration';
import { routes } from '../app.routes';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ButtonModule

  ],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css'
})
export class ConfigurationFormComponent {
  // configForm: FormGroup;

  // constructor(private formBuilder: FormBuilder, private http: HttpClient) {
  //   this.configForm = this.formBuilder.group({
  //     totalTickets: [0, [Validators.required, Validators.min(1)]],
  //     ticketReleaseRate: [0, [Validators.required, Validators.min(1)]],
  //     customerRetrievalRate: [0, [Validators.required, Validators.min(1)]],
  //     maxTicketCapacity: [0, [Validators.required, Validators.min(1)]]
  //   });
  // }

  // onSubmit() {
  //   if (this.configForm.valid) {
  //     this.http.post(`${environment.apiBaseUrl}/api/configuration/save`, this.configForm.value, {responseType: 'text'})
  //       .subscribe(response => console.log('Configuration saved', response));
  //       this.configForm.reset();
  //   } else {
  //     console.log('Form is invalid');
  //   }
  // }

  constructor(private configurationService: ConfigurationService, private router: Router) { }

  public onAddConfiguration(addForm: NgForm): void{

    document.getElementById('add-configuration-form')?.click();

    // this.configurationService.addConfiguration(addForm.value).subscribe(
    //   (response: Configuration) => {
    //     console.log('Configuration saved', response);
    //     addForm.reset();
    //   },
    //     (error: HttpErrorResponse) => {
    //       alert(error.message);
    //       addForm.reset();
    //     }
    // );

    this.configurationService.addConfiguration(addForm.value).subscribe(
    () => this.router.navigate(['/control']),
      (error) => console.error('Error saving configuration', error)
    );
  }
    
}

