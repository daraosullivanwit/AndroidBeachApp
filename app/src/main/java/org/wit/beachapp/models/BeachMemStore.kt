package org.wit.beachapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BeachMemStore : BeachStore {

    val beaches = ArrayList<BeachModel>()

    override fun findAll(): List<BeachModel> {
        return beaches
    }

    override fun create(beach: BeachModel) {
        beach.id = getId()
        beaches.add(beach)
        logAll()
    }

    override fun update(beach: BeachModel) {
        var foundBeach: BeachModel? = beaches.find { p -> p.id == beach.id }
        if (foundBeach != null) {
            foundBeach.title = beach.title
            foundBeach.description = beach.description
            foundBeach.image = beach.image
            foundBeach.lat = beach.lat
            foundBeach.lng = beach.lng
            foundBeach.zoom = beach.zoom
            logAll()
        }
    }

    override fun delete(beach: BeachModel) {
        beaches.remove(beach)
    }

    private fun logAll() {
        beaches.forEach { i("$it") }
    }
}