import { Moment } from 'moment';
import { IComentario } from 'app/shared/model/comentario.model';

export interface IArticulo {
  id?: number;
  titulo?: string;
  contenido?: string;
  fechaCreacion?: Moment;
  perfilId?: number;
  cursoId?: number;
  comentarios?: IComentario[];
}

export class Articulo implements IArticulo {
  constructor(
    public id?: number,
    public titulo?: string,
    public contenido?: string,
    public fechaCreacion?: Moment,
    public perfilId?: number,
    public cursoId?: number,
    public comentarios?: IComentario[]
  ) {}
}
