import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DocService } from '../_services/doc.service';
import { TokenStorageService } from '../_services/token-storage.service';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  styleUrls: ['./document-detail.component.css'],
})
export class DocumentDetailComponent implements OnInit {
  showReportCA = false;
  showReportCAR = false;
  showUserPage = false;
  showAdminPage = false;
  private roles: string[];

  form: any = {};
  form2: any = {};
  docStatus: string = '';
  errorMessage = '';
  userId: number;
  docId: number;

  public subsription: Subscription;

  constructor(
    private router: Router,
    private docService: DocService,
    private tokenStorage: TokenStorageService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.userId = this.tokenStorage.getUser().id;

    const user = this.tokenStorage.getUser();
    this.roles = user.roles;
    this.showUserPage = this.roles.includes('ROLE_USER');
    this.showAdminPage = this.roles.includes('ROLE_ADMIN');

    this.subsription = this.activatedRoute.params.subscribe(
      (params: any) => {
        this.docId = params['id'];
      },
      (error) => {
        console.log(error);
      }
    );
    this.getDocument();
  }

  goBack() {
    this.router.navigate(['/documents']);
  }

  getDocument() {
    this.docService.getDocumentById(this.docId).subscribe((data) => {
      if (data.type == 'COM-CA-TPL') {
        this.showReportCA = true;
        this.form2 = JSON.parse(data.docData);
        this.docStatus = data.status;
      } else {
        this.showReportCAR = true;
        this.form = JSON.parse(data.docData);
        this.docStatus = data.status;
      }
    });
  }

  onExport(title: string) {
    this.docService.exportFileById(this.docId).subscribe((data) => {
      FileSaver.saveAs(data, title + '.docx');
      this.router.navigate(['/documents']);
    });
  }

  onUpdateStatus() {
    this.docService.updateDocumentStatus(this.docId).subscribe((data) => {
      this.router.navigate(['/documents']);
    });
  }
}
