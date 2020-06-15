import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { EditProfileService } from '@services/edit-profile/edit-profile.service';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { AlertsService } from '@services/alerts/alerts.service';
import { TranslateService } from '@ngx-translate/core';
import { UpdateBusiness } from '@models/edit-profile/updateBusiness.model';

@Component({
  selector: 'app-edit-information',
  templateUrl: './edit-information.component.html',
  styleUrls: ['./edit-information.component.css']
})
export class EditInformationComponent implements OnInit {

  public editInformationForm: FormGroup;

  constructor(
    private translate: TranslateService,
    private formBuilder: FormBuilder,
    private location: Location,
    private editProfile: EditProfileService,
    private auth: AuthenticationService,
    private alertService: AlertsService,
  ) { }

  ngOnInit() {
    this.editInformationForm = this.formBuilder.group({
      businessName: ['', Validators.required],
      phone: [''],
      info: ['']
    });
  }
  get form() {
    return this.editInformationForm.controls;
  }


  goBack() {
    this.location.back();
  }

  onSubmit() {
    const updateBusiness: UpdateBusiness = {
      imageURL: '',
      info: '',
      name: '',
      phone: ''
    };
    this.updateInformationAttempt(updateBusiness);

  }

  updateInformationAttempt(updateBusiness: UpdateBusiness) {
    const businessId = '';

    this.alertService.clear();
    this.editProfile.updateBusiness(updateBusiness, businessId)
      .subscribe(() => {
        this.alertService.success(this.translate.instant('Success Change Password'));
      });

  }

}
