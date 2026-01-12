package io.github.woogiekim.commons.core.extension

import org.springframework.data.repository.CrudRepository

fun <T : Any, ID: Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T = findById(id).get()
