import { Moment } from 'moment';
import { IComentario } from 'app/shared/model/comentario.model';
import { TipoPublicacion } from 'app/shared/model/enumerations/tipo-publicacion.model';

export interface IPublicacion {
  id?: number;
  fechaPublicacion?: Moment;
  contenido?: string;
  tipoPublicacion?: TipoPublicacion;
  comentarios?: IComentario[];
  perfilNombre?: string;
  perfilId?: number;
}

export class Publicacion implements IPublicacion {
  constructor(
    public id?: number,
    public fechaPublicacion?: Moment,
    public contenido?: string,
    public tipoPublicacion?: TipoPublicacion,
    public comentarios?: IComentario[],
    public perfilNombre?: string,
    public perfilId?: number
  ) {}
}
