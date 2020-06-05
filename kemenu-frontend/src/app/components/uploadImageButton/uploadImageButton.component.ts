import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '@environments/environment';
import {AuthenticationService} from '@services/authentication/authentication.service';

@Component({
  selector: 'app-upload-image-button',
  templateUrl: './uploadImageButton.component.html',
  styleUrls: ['./uploadImageButton.component.css']
})
export class UploadImageButtonComponent implements OnInit {

  @Input() buttonText: string;
  @Output() uploadFileEvent = new EventEmitter<any>();

  uploadingFile = false;

  constructor(private httpClient: HttpClient,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
  }

  uploadImage(event) {
    const files = event.target.files || (event.dataTransfer && event.dataTransfer.files);
    if (files.length > 0) {
      const file = files[0];
      if (file.type.indexOf('image') !== -1) {
        this.uploadingFile = true;
        const formData = new FormData();
        formData.append('file', file);
        this.httpClient.post<any>(environment.apiBaseUrl + '/web/v1/customer/'
          + this.authService.getUserEmail() + '/upload/image', formData)
          .subscribe(
            (res) => {
              this.uploadFileEvent.emit(res);
              this.uploadingFile = false;
            },
            (err) => {
              console.log('Error: ' + JSON.stringify(err));
              this.uploadFileEvent.emit(undefined);
              this.uploadingFile = false;
            }
          );
      }
    }
  }
}
