import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from '@environments/environment';
import { TranslateService } from '@ngx-translate/core';
import emailjs, { EmailJSResponseStatus } from 'emailjs-com';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.scss']
})
export class ContactFormComponent implements OnInit {

  contactForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private translate: TranslateService,
    private toasty: ToastrService
  ) { }

  ngOnInit() {
    this.createContactForm();
  }
  get form() {
    return this.contactForm.controls;
  }

  createContactForm() {
    this.contactForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      details: ['', Validators.required]
    });
  }

  sendEmail(e: Event) {
    e.preventDefault();
    emailjs.send(environment.contactForm.serviceId,
      environment.contactForm.templateId,
      {
        subject: this.form.subject.value,
        email: this.form.email.value,
        details: this.form.details.value,
      },
      environment.contactForm.userId)
      .then((result: EmailJSResponseStatus) => {
        this.showSuccessToasty();
      }, (error) => {
        this.showErrorToasty();
      });
    this.contactForm.reset();
  }


  showSuccessToasty() {
    this.toasty.success(this.translate.instant('Sent Correctly'));
  }

  showErrorToasty() {
    this.toasty.error(this.translate.instant('Sent error'));
  }

}
