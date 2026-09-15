package com.tp.taskflow.feature.product.data

import com.tp.taskflow.feature.product.domain.Product

fun ProductDto.toDomain(): Product = Product(
    id = product_id,
    name = title,
    category = type,
    price = amount
)

fun ProductDto.toEntity(): ProductEntity = ProductEntity(
    id = product_id,
    name = title,
    category = type,
    price = amount
)

fun ProductEntity.toDomain(): Product = Product(id, name, category, price)
