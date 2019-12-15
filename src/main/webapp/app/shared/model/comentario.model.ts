import { Moment } from 'moment';

export interface IComentario {
  id?: number;
  autor?: string;
  avatar?: string;
  fechaCreacion?: Moment;
  contenido?: string;
  perfilId?: number;
  tareaId?: number;
  articuloId?: number;
  publicacionId?: number;
}

export class Comentario implements IComentario {
  constructor(
    public id?: number,
    public autor?: string,
    public avatar?: string,
    public fechaCreacion?: Moment,
    public contenido?: string,
    public perfilId?: number,
    public tareaId?: number,
    public articuloId?: number,
    public publicacionId?: number
  ) {}
}
