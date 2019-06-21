import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';

@Component({
  selector: 'jhi-pet-delete-dialog',
  templateUrl: './pet-delete-dialog.component.html'
})
export class PetDeleteDialogComponent {
  pet: IPet;

  constructor(protected petService: PetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.petService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'petListModification',
        content: 'Deleted an pet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pet-delete-popup',
  template: ''
})
export class PetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pet = pet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
