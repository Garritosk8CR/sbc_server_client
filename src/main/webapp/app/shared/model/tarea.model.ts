import { Moment } from 'moment';
import { IComentario } from 'app/shared/model/comentario.model';
import { EstadoTarea } from 'app/shared/model/enumerations/estado-tarea.model';

export interface ITarea {
  id?: number;
  nombre?: string;
  descripcion?: string;
  fechaCreacion?: Moment;
  fechaConclucion?: Moment;
  estadoTarea?: EstadoTarea;
  historiaUsuarioId?: number;
  comentarios?: IComentario[];
}

export class Tarea implements ITarea {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public fechaCreacion?: Moment,
    public fechaConclucion?: Moment,
    public estadoTarea?: EstadoTarea,
    public historiaUsuarioId?: number,
    public comentarios?: IComentario[]
  ) {}
}
