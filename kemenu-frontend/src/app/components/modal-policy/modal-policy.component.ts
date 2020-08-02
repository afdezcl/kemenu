import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { EditProfileService } from '@services/edit-profile/edit-profile.service';

@Component({
  selector: 'app-modal-policy',
  templateUrl: './modal-policy.component.html',
  styleUrls: ['./modal-policy.component.scss']
})
export class ModalPolicyComponent implements OnInit {

  constructor(
    public bsModalRef: BsModalRef,
    public editProfileService: EditProfileService
  ) { }

  ngOnInit() {
  }

  updateNewsletterStatus(status: string) {
    this.editProfileService.updateNewsletterStatus(status)
      .subscribe(() => {
        this.bsModalRef.hide();
      });
  }

}
