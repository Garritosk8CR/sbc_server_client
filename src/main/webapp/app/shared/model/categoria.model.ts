export interface ICategoria {
  id?: number;
  nombre?: string;
  etiqueta?: string;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public etiqueta?: string) {}
}
