import { Moment } from 'moment';
import { IArticulo } from 'app/shared/model/articulo.model';
import { Categoria } from 'app/shared/model/enumerations/categoria.model';

export interface ICurso {
  id?: number;
  nombre?: string;
  slug?: string;
  descripcion?: string;
  categoria?: Categoria;
  duracion?: string;
  totalDeArticulos?: string;
  fechaActualizacion?: Moment;
  perfilId?: number;
  articulos?: IArticulo[];
}

export class Curso implements ICurso {
  constructor(
    public id?: number,
    public nombre?: string,
    public slug?: string,
    public descripcion?: string,
    public categoria?: Categoria,
    public duracion?: string,
    public totalDeArticulos?: string,
    public fechaActualizacion?: Moment,
    public perfilId?: number,
    public articulos?: IArticulo[]
  ) {}
}
