package resa.mario.services.cache

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import kotlin.time.Duration.Companion.minutes

@Single
class DepartamentoCache {
    val refreshTime = 1000 * 60 // 1 minuto

    val cache = Cache.Builder()
        .maximumCacheSize(100)
        .expireAfterAccess(5.minutes)
        .build<Long, Departamento>()
}