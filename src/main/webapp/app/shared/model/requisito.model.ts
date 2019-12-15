import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

export interface IRequisito {
  id?: number;
  nombre?: string;
  descripcion?: string;
  tipo?: string;
  puestoDeTrabajos?: IPuestoDeTrabajo[];
}

export class Requisito implements IRequisito {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public tipo?: string,
    public puestoDeTrabajos?: IPuestoDeTrabajo[]
  ) {}
}
