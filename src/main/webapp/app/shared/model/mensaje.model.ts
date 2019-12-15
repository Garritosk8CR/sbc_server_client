import { EstadoMensaje } from 'app/shared/model/enumerations/estado-mensaje.model';

export interface IMensaje {
  id?: number;
  mensaje?: string;
  fechaEmision?: string;
  estadoMensaje?: EstadoMensaje;
  conversacionId?: number;
}

export class Mensaje implements IMensaje {
  constructor(
    public id?: number,
    public mensaje?: string,
    public fechaEmision?: string,
    public estadoMensaje?: EstadoMensaje,
    public conversacionId?: number
  ) {}
}
