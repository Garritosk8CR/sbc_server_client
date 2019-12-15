import { Moment } from 'moment';
import { EstadoMensaje } from 'app/shared/model/enumerations/estado-mensaje.model';

export interface INotificacion {
  id?: number;
  origen?: string;
  destino?: string;
  tipo?: string;
  fechaEmision?: Moment;
  estadoMensaje?: EstadoMensaje;
  perfilId?: number;
}

export class Notificacion implements INotificacion {
  constructor(
    public id?: number,
    public origen?: string,
    public destino?: string,
    public tipo?: string,
    public fechaEmision?: Moment,
    public estadoMensaje?: EstadoMensaje,
    public perfilId?: number
  ) {}
}
