import { IRequisito } from 'app/shared/model/requisito.model';

export interface IPuestoDeTrabajo {
  id?: number;
  nombre?: string;
  descripcion?: string;
  carreraProfesionalId?: number;
  requerimientos?: IRequisito[];
}

export class PuestoDeTrabajo implements IPuestoDeTrabajo {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public carreraProfesionalId?: number,
    public requerimientos?: IRequisito[]
  ) {}
}
