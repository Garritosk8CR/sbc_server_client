import { Moment } from 'moment';
import { ITarea } from 'app/shared/model/tarea.model';

export interface IHistoriaUsuario {
  id?: number;
  nombre?: string;
  descripcion?: string;
  fechaCreacion?: Moment;
  fechaConclucion?: Moment;
  sprint?: string;
  isEpic?: boolean;
  perfilId?: number;
  tareas?: ITarea[];
}

export class HistoriaUsuario implements IHistoriaUsuario {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public fechaCreacion?: Moment,
    public fechaConclucion?: Moment,
    public sprint?: string,
    public isEpic?: boolean,
    public perfilId?: number,
    public tareas?: ITarea[]
  ) {
    this.isEpic = this.isEpic || false;
  }
}
