import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { EditProfileService } from '@services/edit-profile/edit-profile.service';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { AlertsService } from '@services/alerts/alerts.service';
import { TranslateService } from '@ngx-translate/core';
import { UpdateBusiness } from '@models/edit-profile/updateBusiness.model';
import { MenuService } from '@services/menu/menu.service';

@Component({
  selector: 'app-edit-information',
  templateUrl: './edit-information.component.html',
  styleUrls: ['./edit-information.component.css']
})
export class EditInformationComponent implements OnInit {

  public editInformationForm: FormGroup;
  public businessImageURL = '';
  public businessName = '';
  public info = '';
  public phone = '';
  public businessId = '';
  public color = '';
  public newsletterStatus = '';

  constructor(
    private translate: TranslateService,
    private formBuilder: FormBuilder,
    private location: Location,
    private editProfile: EditProfileService,
    private auth: AuthenticationService,
    private alertService: AlertsService,
    private menuService: MenuService
  ) { }

  ngOnInit() {
    this.getBusinessData();
    this.editInformationForm = this.formBuilder.group({
      businessName: ['', Validators.required],
      phone: [''],
      info: ['']
    });
  }

  getBusinessData() {
    this.menuService.getCustomer(this.auth.getUserEmail())
      .subscribe((response: any) => {
        this.businessName = response.businesses[0].name;
        this.info = response.businesses[0].info;
        this.phone = response.businesses[0].phone;
        this.businessId = response.businesses[0].id;
        this.color = response.businesses[0].color;
        this.businessImageURL = response.businesses[0].imageUrl;
        this.newsletterStatus = response.newsletterStatus;
        this.fillForm();
      });
  }

  private fillForm() {
    this.editInformationForm = this.formBuilder.group({
      businessName: [this.businessName, Validators.required],
      phone: [this.phone],
      info: [this.info]
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
      color: this.color,
      imageUrl: this.businessImageURL,
      info: this.form.info.value,
      name: this.form.businessName.value,
      phone: this.form.phone.value
    };
    this.updateInformationAttempt(updateBusiness);
  }

  updateInformationAttempt(updateBusiness: UpdateBusiness) {
    this.alertService.clear();
    this.editProfile.updateBusiness(updateBusiness, this.businessId)
      .subscribe(() => {
        this.alertService.success(this.translate.instant('Saved Correctly'));
      });

  }

  handleFileUpload(event) {
    if (event) {
      this.businessImageURL = event.url;
    }
  }

  changeNewsletterStatus(event) {
    const newsletterActualStatus = event.currentTarget.checked;    
    if(newsletterActualStatus) {
      this.newsletterStatus = 'ACCEPTED';
    } else {
      this.newsletterStatus = 'REJECTED';
    }
    
    this.editProfile.updateNewsletterStatus(this.newsletterStatus)
      .subscribe(() => {
        this.alertService.success(this.translate.instant('Saved Correctly'), 2000);              
      });

  }

}
