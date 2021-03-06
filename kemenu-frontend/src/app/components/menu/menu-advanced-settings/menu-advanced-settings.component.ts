import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { MenuService } from '@services/menu/menu.service';
import { Currency } from '@models/menu/currency.interface';

@Component({
  selector: 'app-menu-advanced-settings',
  templateUrl: './menu-advanced-settings.component.html',
  styleUrls: ['./menu-advanced-settings.component.css']
})
export class MenuAdvancedSettingsComponent implements OnInit {

  public settingsForm: FormGroup;
  public actualCurrency: string;
  public currencies: Currency[];
  @Output() saveSettings = new EventEmitter<string>();

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef,
    private menuService: MenuService
  ) { }

  ngOnInit() {
    this.settingsForm = this.formBuilder.group({
      isoCode: [this.actualCurrency, Validators.required],
    });

    this.getCurrencies();

  }

  getCurrencies() {
    this.menuService.getCurrencies()
      .subscribe((currencies: Currency[]) => {
        this.currencies = currencies;
      });
  }

  get form() {
    return this.settingsForm.controls;
  }

  onSubmit() {
    this.updateCurrency();
  }

  updateCurrency() {
    this.saveSettings.emit(this.form.isoCode.value);
    this.bsModalRef.hide();
  }

}
