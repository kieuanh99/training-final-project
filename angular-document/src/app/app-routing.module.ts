import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentAddComponent } from './document-add/document-add.component';
import { DocumentDetailComponent } from './document-detail/document-detail.component';
import { DocumentEditComponent } from './document-edit/document-edit.component';
import { DocumentPageComponent } from './document-page/document-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ProfileComponent } from './profile/profile.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { UserPageComponent } from './user-page/user-page.component';

const routes: Routes = [
  { path: 'home', component: HomePageComponent },
  { path: 'signin', component: SignInComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'documents', component: DocumentPageComponent },
  { path: 'addDocument/:type', component: DocumentAddComponent },
  { path: 'viewDocument/:id', component: DocumentDetailComponent },
  { path: 'updateDocument/:id', component: DocumentEditComponent },
  { path: 'users', component: UserPageComponent },

  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
