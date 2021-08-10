import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as FileSaver from 'file-saver';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class DocService {
  private DOC_API = 'http://localhost:8080/api/test/';

  constructor(private http: HttpClient) {}

  // User

  getDocsByUserId(userId: number): Observable<any> {
    return this.http.get(this.DOC_API + 'documentUser/' + userId);
  }

  // get Document by userid and type
  getDocsByType(userId: number, type: string): Observable<any> {
    return this.http.get(
      this.DOC_API + 'documentUserByType/' + userId + '/' + type
    );
  }

  getDocumentById(docId: number): Observable<any> {
    return this.http.get(this.DOC_API + 'docById/' + docId);
  }

  deleteDocById(id: number): Observable<any> {
    return this.http.delete(this.DOC_API + 'deleteDocument/' + id, {
      responseType: 'text',
    });
  }

  addDocument(
    data: any,
    type: string,
    userId: number,
    release: any
  ): Observable<any[]> {
    return this.http.post<any[]>(
      this.DOC_API + 'addDocument/',
      {
        type: type,
        title: data.title,
        userId: userId,
        updated: release,
        status: 'NEW',
        docData: JSON.stringify(data),
      },
      httpOptions
    );
  }

  updateDocument(data: any, docId: number, updated: any): Observable<any[]> {
    return this.http.put<any[]>(this.DOC_API + 'updateDocument/', {
      title: data.title,
      id: docId,
      updated: updated,
      docData: JSON.stringify(data),
    });
  }

  exportFileById(docId: number): Observable<any> {
    return this.http.get(this.DOC_API + 'exportFileById/' + docId, {
      responseType: 'blob',
    });
  }

  // export file from create form type CAR
  exportFile(data: any): Observable<any> {
    return this.http.post<any[]>(this.DOC_API + 'exportFile/', data, {
      responseType: 'blob' as 'json',
    });
  }

  // export file from create form type CA
  exportFileCA(data: any): Observable<any> {
    return this.http.post<any[]>(this.DOC_API + 'exportFileCA/', data, {
      responseType: 'blob' as 'json',
    });
  }

  // Admin

  getListUser(role: number): Observable<any> {
    return this.http.get(this.DOC_API + 'users/' + role);
  }

  getListDocument(): Observable<any> {
    return this.http.get(this.DOC_API + 'documents/');
  }

  getDocumentsByType(type: string): Observable<any> {
    return this.http.get(this.DOC_API + 'documentAdminByType/' + type);
  }

  updateDocumentStatus(docId: number): Observable<any[]> {
    return this.http.put<any[]>(this.DOC_API + 'updateDocStatus/', {
      id: docId,
      status: 'APPROVED',
    });
  }
}
