import { IPuestoDeTrabajo } from 'app/shared/model/puesto-de-trabajo.model';

export interface ICarreraProfesional {
  id?: number;
  nombre?: string;
  descripcion?: string;
  puestos?: IPuestoDeTrabajo[];
}

export class CarreraProfesional implements ICarreraProfesional {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public puestos?: IPuestoDeTrabajo[]) {}
}
