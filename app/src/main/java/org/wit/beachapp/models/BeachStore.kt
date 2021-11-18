package org.wit.beachapp.models

interface BeachStore {
    fun findAll(): List<BeachModel>
    fun create(beach: BeachModel)
    fun update(beach: BeachModel)
}