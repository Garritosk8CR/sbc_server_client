import { Moment } from 'moment';
import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { IConversacion } from 'app/shared/model/conversacion.model';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { ICurso } from 'app/shared/model/curso.model';
import { IArticulo } from 'app/shared/model/articulo.model';
import { IComentario } from 'app/shared/model/comentario.model';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';

export interface IPerfil {
  id?: number;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string;
  edad?: string;
  estadoCivil?: string;
  sexo?: string;
  telefonoCelular?: string;
  telefonoFijo?: string;
  correoElectronico?: string;
  direccion?: string;
  cedula?: string;
  fechaIngreso?: Moment;
  fechaSalida?: Moment;
  foto?: string;
  carreraProfesionalNombre?: string;
  carreraProfesionalId?: number;
  historiaUsuarios?: IHistoriaUsuario[];
  conversacions?: IConversacion[];
  notificacions?: INotificacion[];
  cursos?: ICurso[];
  articulos?: IArticulo[];
  comentarios?: IComentario[];
  suscripcions?: ISuscripcion[];
}

export class Perfil implements IPerfil {
  constructor(
    public id?: number,
    public nombre?: string,
    public primerApellido?: string,
    public segundoApellido?: string,
    public edad?: string,
    public estadoCivil?: string,
    public sexo?: string,
    public telefonoCelular?: string,
    public telefonoFijo?: string,
    public correoElectronico?: string,
    public direccion?: string,
    public cedula?: string,
    public fechaIngreso?: Moment,
    public fechaSalida?: Moment,
    public foto?: string,
    public carreraProfesionalNombre?: string,
    public carreraProfesionalId?: number,
    public historiaUsuarios?: IHistoriaUsuario[],
    public conversacions?: IConversacion[],
    public notificacions?: INotificacion[],
    public cursos?: ICurso[],
    public articulos?: IArticulo[],
    public comentarios?: IComentario[],
    public suscripcions?: ISuscripcion[]
  ) {}
}
