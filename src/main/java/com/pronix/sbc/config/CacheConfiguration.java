package com.pronix.sbc.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.pronix.sbc.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.pronix.sbc.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.pronix.sbc.domain.User.class.getName());
            createCache(cm, com.pronix.sbc.domain.Authority.class.getName());
            createCache(cm, com.pronix.sbc.domain.User.class.getName() + ".authorities");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName());
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".historiaUsuarios");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".conversacions");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".notificacions");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".articulos");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".comentarios");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".suscripcions");
            createCache(cm, com.pronix.sbc.domain.CarreraProfesional.class.getName());
            createCache(cm, com.pronix.sbc.domain.CarreraProfesional.class.getName() + ".puestos");
            createCache(cm, com.pronix.sbc.domain.PuestoDeTrabajo.class.getName());
            createCache(cm, com.pronix.sbc.domain.PuestoDeTrabajo.class.getName() + ".requerimientos");
            createCache(cm, com.pronix.sbc.domain.Requisito.class.getName());
            createCache(cm, com.pronix.sbc.domain.Requisito.class.getName() + ".puestoDeTrabajos");
            createCache(cm, com.pronix.sbc.domain.HistoriaUsuario.class.getName());
            createCache(cm, com.pronix.sbc.domain.HistoriaUsuario.class.getName() + ".tareas");
            createCache(cm, com.pronix.sbc.domain.Tarea.class.getName());
            createCache(cm, com.pronix.sbc.domain.Tarea.class.getName() + ".comentarios");
            createCache(cm, com.pronix.sbc.domain.Conversacion.class.getName());
            createCache(cm, com.pronix.sbc.domain.Conversacion.class.getName() + ".mensajes");
            createCache(cm, com.pronix.sbc.domain.Mensaje.class.getName());
            createCache(cm, com.pronix.sbc.domain.Notificacion.class.getName());
            createCache(cm, com.pronix.sbc.domain.Categoria.class.getName());
            createCache(cm, com.pronix.sbc.domain.Articulo.class.getName());
            createCache(cm, com.pronix.sbc.domain.Articulo.class.getName() + ".comentarios");
            createCache(cm, com.pronix.sbc.domain.Publicacion.class.getName());
            createCache(cm, com.pronix.sbc.domain.Publicacion.class.getName() + ".comentarios");
            createCache(cm, com.pronix.sbc.domain.Comentario.class.getName());
            createCache(cm, com.pronix.sbc.domain.Suscripcion.class.getName());
            createCache(cm, com.pronix.sbc.domain.Suscripcion.class.getName() + ".suscriptors");
            createCache(cm, com.pronix.sbc.domain.Perfil.class.getName() + ".cursos");
            createCache(cm, com.pronix.sbc.domain.Curso.class.getName());
            createCache(cm, com.pronix.sbc.domain.Curso.class.getName() + ".articulos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
