import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'perfil',
        loadChildren: () => import('./perfil/perfil.module').then(m => m.SbcAppPerfilModule)
      },
      {
        path: 'carrera-profesional',
        loadChildren: () => import('./carrera-profesional/carrera-profesional.module').then(m => m.SbcAppCarreraProfesionalModule)
      },
      {
        path: 'puesto-de-trabajo',
        loadChildren: () => import('./puesto-de-trabajo/puesto-de-trabajo.module').then(m => m.SbcAppPuestoDeTrabajoModule)
      },
      {
        path: 'requisito',
        loadChildren: () => import('./requisito/requisito.module').then(m => m.SbcAppRequisitoModule)
      },
      {
        path: 'historia-usuario',
        loadChildren: () => import('./historia-usuario/historia-usuario.module').then(m => m.SbcAppHistoriaUsuarioModule)
      },
      {
        path: 'tarea',
        loadChildren: () => import('./tarea/tarea.module').then(m => m.SbcAppTareaModule)
      },
      {
        path: 'conversacion',
        loadChildren: () => import('./conversacion/conversacion.module').then(m => m.SbcAppConversacionModule)
      },
      {
        path: 'mensaje',
        loadChildren: () => import('./mensaje/mensaje.module').then(m => m.SbcAppMensajeModule)
      },
      {
        path: 'notificacion',
        loadChildren: () => import('./notificacion/notificacion.module').then(m => m.SbcAppNotificacionModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.SbcAppCategoriaModule)
      },
      {
        path: 'articulo',
        loadChildren: () => import('./articulo/articulo.module').then(m => m.SbcAppArticuloModule)
      },
      {
        path: 'publicacion',
        loadChildren: () => import('./publicacion/publicacion.module').then(m => m.SbcAppPublicacionModule)
      },
      {
        path: 'comentario',
        loadChildren: () => import('./comentario/comentario.module').then(m => m.SbcAppComentarioModule)
      },
      {
        path: 'suscripcion',
        loadChildren: () => import('./suscripcion/suscripcion.module').then(m => m.SbcAppSuscripcionModule)
      },
      {
        path: 'curso',
        loadChildren: () => import('./curso/curso.module').then(m => m.SbcAppCursoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SbcAppEntityModule {}
