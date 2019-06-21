import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPet } from 'app/shared/model/pet.model';

@Component({
  selector: 'jhi-pet-detail',
  templateUrl: './pet-detail.component.html'
})
export class PetDetailComponent implements OnInit {
  pet: IPet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pet }) => {
      this.pet = pet;
    });
  }

  previousState() {
    window.history.back();
  }
}
