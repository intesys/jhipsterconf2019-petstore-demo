export interface IPet {
  id?: number;
  name?: string;
  tags?: string;
}

export class Pet implements IPet {
  constructor(public id?: number, public name?: string, public tags?: string) {}
}
