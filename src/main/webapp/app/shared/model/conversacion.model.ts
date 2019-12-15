import { Moment } from 'moment';
import { IMensaje } from 'app/shared/model/mensaje.model';

export interface IConversacion {
  id?: number;
  usuario1?: string;
  usuario2?: string;
  fechaDeConversacion?: Moment;
  perfilId?: number;
  mensajes?: IMensaje[];
}

export class Conversacion implements IConversacion {
  constructor(
    public id?: number,
    public usuario1?: string,
    public usuario2?: string,
    public fechaDeConversacion?: Moment,
    public perfilId?: number,
    public mensajes?: IMensaje[]
  ) {}
}
