import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css'
})
export class ConfigurationFormComponent {
  configForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {
    this.configForm = this.formBuilder.group({
      totalTickets: [0, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [0, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [0, [Validators.required, Validators.min(1)]],
      maxTicketCapacity: [0, [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit() {
    if (this.configForm.valid) {
      this.http.post('/api/config', this.configForm.value)
        .subscribe(response => console.log('Configuration saved', response));
    } else {
      console.log('Form is invalid');
    }
  }
}
