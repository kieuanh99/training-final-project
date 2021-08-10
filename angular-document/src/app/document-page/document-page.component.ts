import { Component, OnInit } from '@angular/core';
import { EType } from '../models/type.model';
import { DocService } from '../_services/doc.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import * as FileSaver from 'file-saver';
import { ETypeDoc } from '../models/typeDoc.model';

@Component({
  selector: 'app-document-page',
  templateUrl: './document-page.component.html',
  styleUrls: ['./document-page.component.css'],
})
export class DocumentPageComponent implements OnInit {
  documents: any[];
  resultSearch: any[];
  resultFilter: any[];

  userId: number;
  isLoggedIn = false;
  length: number;
  showUserPage = false;
  showAdminPage = false;

  private roles: string[];
  types: EType[] = [EType.COM_CA_TPL, EType.COM_CAR_TPL];
  typeDoc: ETypeDoc[] = [ETypeDoc.COM_CA_TPL, ETypeDoc.COM_CAR_TPL];

  chooseType: string;
  typeSelected: string;
  typeValue: string = '';

  name: string;
  p: number = 1;
  constructor(
    private docService: DocService,
    private tokenStorageService: TokenStorageService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.isLoggedIn = !!this.tokenStorageService.getUser();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.userId = user.id;
      this.roles = user.roles;
      this.showUserPage = this.roles.includes('ROLE_USER');
      this.showAdminPage = this.roles.includes('ROLE_ADMIN');
    }
    if (this.showUserPage == true) {
      this.docService.getDocsByUserId(this.userId).subscribe(
        (data) => {
          this.documents = data.sort((a: any, b: any) => {
            return <any>new Date(b.updated) - <any>new Date(a.updated);
          });
          this.length = data.length;
        },
        (err) => {
          this.documents = JSON.parse(err.error).message;
        }
      );
    } else if (this.showAdminPage == true) {
      this.docService.getListDocument().subscribe(
        (data) => {
          this.documents = data.sort((a: any, b: any) => {
            return <any>new Date(b.updated) - <any>new Date(a.updated);
          });
          this.length = data.length;
        },
        (err) => {
          this.documents = JSON.parse(err.error).message;
        }
      );
    }
  }

  search() {
    if (this.name != '') {
      this.documents = this.documents.filter((res) => {
        return res.title
          .toLocaleLowerCase()
          .match(this.name.toLocaleLowerCase());
      });
    } else if (this.name == '' && this.typeValue == '') {
      this.ngOnInit();
    } else if (this.name == '' && this.typeValue == 'All') {
      this.ngOnInit();
    } else if (this.name == '' && this.typeValue == 'COM-CA-TPL') {
      this.onFilter(this.typeValue);
    } else if (this.name == '' && this.typeValue == 'COM-CAR-TPL') {
      this.onFilter(this.typeValue);
    }
  }

  onFilter(type: any) {
    this.typeValue = type;

    if (this.typeSelected == 'All') {
      this.ngOnInit();
    } else if (this.typeSelected != '' && this.showUserPage == true) {
      this.docService.getDocsByType(this.userId, type).subscribe((data) => {
        this.documents = data.sort((a: any, b: any) => {
          return <any>new Date(b.updated) - <any>new Date(a.updated);
        });
      });
    } else if (this.typeSelected != '' && this.showAdminPage == true) {
      this.docService.getDocumentsByType(type).subscribe((data) => {
        this.documents = data.sort((a: any, b: any) => {
          return <any>new Date(b.updated) - <any>new Date(a.updated);
        });
      });
    }
  }

  openModalDel(content: any, docId: number) {
    this.modalService
      .open(content, { ariaLabelledBy: 'deleteModalLabel' })
      .result.then(
        (result) => {
          if (result === 'yes') {
            this.deleteDocument(docId);
          }
        },
        (reason) => {}
      );
  }

  deleteDocument(id: number) {
    this.docService.deleteDocById(id).subscribe((data) => {
      this.loadData();
    });
  }

  openTemplate(type: string) {
    if (type == ETypeDoc.COM_CA_TPL) {
      this.router.navigate(['/addDocument', 'COM-CA-TPL']);
    } else if (type == ETypeDoc.COM_CAR_TPL) {
      this.router.navigate(['/addDocument', 'COM-CAR-TPL']);
    }
  }

  exportDocument(id: number, title: string) {
    this.docService.exportFileById(id).subscribe((data) => {
      FileSaver.saveAs(data, title + '.docx');
    });
  }
}
