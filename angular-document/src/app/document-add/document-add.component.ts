import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EStatusReportCA } from '../models/statusReportCA.model';
import { ETypeOfCar } from '../models/typeOfCar.model';
import { DocService } from '../_services/doc.service';
import { TokenStorageService } from '../_services/token-storage.service';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-document-add',
  templateUrl: './document-add.component.html',
  styleUrls: ['./document-add.component.css'],
})
export class DocumentAddComponent implements OnInit {
  typeOfCar: ETypeOfCar[] = [ETypeOfCar.REACTIVE, ETypeOfCar.PROACTIVE];
  statusReport: EStatusReportCA[] = [
    EStatusReportCA.Y,
    EStatusReportCA.N,
    EStatusReportCA.NA,
  ];
  showReportCA = false;
  showSuccessModal = false;
  showReportCAR = false;
  isSuccessful = false;
  formObject = new Object();

  formPlus: any;
  dataarray: any = [];

  form: any = {};
  form2: any = {};
  isCreateFailed = false;
  errorMessage = '';
  userId: number;
  type: string;
  release: string = new Date().toISOString();
  today: string = new Date().toDateString();
  exportCLicked = false;

  public subsription: Subscription;

  constructor(
    private router: Router,
    private docService: DocService,
    private tokenStorage: TokenStorageService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.userId = this.tokenStorage.getUser().id;

    // select Type
    this.subsription = this.activatedRoute.params.subscribe(
      (params: any) => {
        this.type = params['type'];

        if (this.type == 'COM-CA-TPL') {
          this.showReportCA = true;
        } else {
          this.showReportCAR = true;
        }
      },
      (error) => {
        console.log(error);
      }
    );

    this.dataarray.push(this.formObject);
  }

  addFormWhy() {
    this.formObject = new Object();
    this.dataarray.push(this.formObject);
  }

  removeFormWhy(index: any) {
    this.dataarray.splice(index);
  }

  goBack() {
    this.router.navigate(['/documents']);
  }

  onSubmitCA(): void {
    const formTypeCA = {
      dataActivity: this.dataarray || '',

      title: this.form2.titleReportCA || '',
      projectName: this.form2.projectName || '',
      projectId: this.form2.projectId || '',
      auditDate: this.form2.auditDate || '',
      customer: this.form2.customer || '',
      items: this.form2.items || '',
      auditTeam: this.form2.auditTeam || '',
      remarks: this.form2.remarks || '',
      today: this.today,
    };

    this.docService
      .addDocument(formTypeCA, this.type, this.userId, this.release)
      .subscribe(
        (data) => {
          this.isSuccessful = true;
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isCreateFailed = true;
        }
      );
  }
  public exportFileCA(): void {
    const formTypeCA = {
      dataActivity: this.dataarray || '',

      title: this.form2.titleReportCA || '',
      projectName: this.form2.projectName || '',
      projectId: this.form2.projectId || '',
      auditDate: this.form2.auditDate || '',
      customer: this.form2.customer || '',
      items: this.form2.items || '',
      auditTeam: this.form2.auditTeam || '',
      remarks: this.form2.remarks || '',
      today: this.today,
    };
    this.docService
      .addDocument(formTypeCA, this.type, this.userId, this.release)
      .subscribe(
        (data) => {
          this.docService.exportFileCA(formTypeCA).subscribe((dataDocument) => {
            FileSaver.saveAs(dataDocument, this.form2.titleReportCA + '.docx');
            this.router.navigate(['/documents']);
          });
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isCreateFailed = true;
        }
      );
  }

  // Submit form CAR

  onSubmitCAR(): void {
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
      why: this.dataarray || '',
    };

    this.docService
      .addDocument(formTypeCAR, this.type, this.userId, this.release)
      .subscribe(
        (data) => {
          this.isSuccessful = true;
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isCreateFailed = true;
        }
      );
  }

  public exportFileCAR(): void {
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
      why: this.dataarray || '',
    };

    this.docService
      .addDocument(formTypeCAR, this.type, this.userId, this.release)
      .subscribe(
        (data) => {
          this.docService.exportFile(formTypeCAR).subscribe((dataDocument) => {
            FileSaver.saveAs(dataDocument, this.form.title + '.docx');
            this.router.navigate(['/documents']);
          });
        },
        (err) => {
          this.errorMessage = err.error.message;
          this.isCreateFailed = true;
        }
      );
  }

  ngOnDestroy(): void {
    this.subsription.unsubscribe();
  }
}
