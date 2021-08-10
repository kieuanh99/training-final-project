import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EStatusReportCA } from '../models/statusReportCA.model';
import { ETypeOfCar } from '../models/typeOfCar.model';
import { DocService } from '../_services/doc.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-document-edit',
  templateUrl: './document-edit.component.html',
  styleUrls: ['./document-edit.component.css'],
})
export class DocumentEditComponent implements OnInit {
  typeOfCar: ETypeOfCar[] = [ETypeOfCar.REACTIVE, ETypeOfCar.PROACTIVE];
  statusReport: EStatusReportCA[] = [
    EStatusReportCA.Y,
    EStatusReportCA.N,
    EStatusReportCA.NA,
  ];
  showReportCA = false;
  showReportCAR = false;
  isSuccessful = false;
  isUpdateFailed = false;
  formObject = new Object();

  form: any = {};
  form2: any = {};

  errorMessage = '';
  userId: number;
  type: string;
  updated: string = new Date().toISOString();
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

    this.subsription = this.activatedRoute.params.subscribe(
      (params: any) => {
        this.docId = params['id'];
      },
      (error) => {
        console.log(error);
      }
    );
    this.getDocument();

    this.form.why.push(this.formObject);
    this.form2.dataActivity.push(this.formObject);
  }

  getDocument() {
    this.docService.getDocumentById(this.docId).subscribe((data) => {
      if (data.type == 'COM-CA-TPL') {
        this.showReportCA = true;
        this.form2 = JSON.parse(data.docData);
      } else {
        this.showReportCAR = true;
        this.form = JSON.parse(data.docData);
      }
    });
  }

  addFormWhy() {
    this.formObject = new Object();
    this.form.why.push(this.formObject);
  }

  removeFormWhy(index: any) {
    this.form.why.splice(index);
  }

  goBack() {
    this.router.navigate(['/documents']);
  }

  onUpdate(): void {
    const formTypeCAR = {
      carType: this.form.carType || '',
      title: this.form.title,
      correctiveActions: this.form.correctiveActions || '',
      defineTheProblem: this.form.defineTheProblem || '',
      discussedWith: this.form.discussedWith || '',
      impact: this.form.impact || '',
      performedBy: this.form.performedBy || '',
      problemDescription: this.form.problemDescription || '',
      rootCause: this.form.rootCause || '',
      why: this.form.why || '',
    };

    this.docService
      .updateDocument(formTypeCAR, this.docId, this.updated)
      .subscribe(
        (data) => {
          this.router.navigate(['/documents']);
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isUpdateFailed = true;
        }
      );
  }

  addFormActivity() {
    this.formObject = new Object();
    this.form2.dataActivity.push(this.formObject);
  }

  removeFormActivity(index: any) {
    this.form2.dataActivity.splice(index);
  }

  onUpdateCA() {
    const formTypeCA = {
      dataActivity: this.form2.dataActivity || '',

      title: this.form2.title || '',
      projectName: this.form2.projectName || '',
      projectId: this.form2.projectId || '',
      auditDate: this.form2.auditDate || '',
      customer: this.form2.customer || '',
      items: this.form2.items || '',
      auditTeam: this.form2.auditTeam || '',
      remarks: this.form2.remarks || '',
      today: this.form2.today,
    };

    this.docService
      .updateDocument(formTypeCA, this.docId, this.updated)
      .subscribe(
        (data) => {
          this.router.navigate(['/documents']);
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isUpdateFailed = true;
        }
      );
  }
}
