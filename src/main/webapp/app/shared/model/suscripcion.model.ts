import { Moment } from 'moment';
import { IPerfil } from 'app/shared/model/perfil.model';
import { EstadoSuscripcion } from 'app/shared/model/enumerations/estado-suscripcion.model';

export interface ISuscripcion {
  id?: number;
  fechaSuscripcion?: Moment;
  estadoSuscripcion?: EstadoSuscripcion;
  suscriptors?: IPerfil[];
}

export class Suscripcion implements ISuscripcion {
  constructor(
    public id?: number,
    public fechaSuscripcion?: Moment,
    public estadoSuscripcion?: EstadoSuscripcion,
    public suscriptors?: IPerfil[]
  ) {}
}
