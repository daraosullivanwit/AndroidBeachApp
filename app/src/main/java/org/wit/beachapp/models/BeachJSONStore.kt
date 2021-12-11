package org.wit.beachapp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.beachapp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

//Describe JSON file
const val JSON_FILE = "beaches.json"
//Utility to serialize java class
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
//Object to help in converting a JSON string to a java collection
val listType: Type = object : TypeToken<ArrayList<BeachModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class BeachJSONStore(private val context: Context) : BeachStore {
    var beaches = mutableListOf<BeachModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BeachModel> {
        logAll()
        return beaches
    }

    override fun create(beach: BeachModel) {
        beach.id = generateRandomId()
        beaches.add(beach)
        serialize()
    }

    override fun update(beach: BeachModel) {
        val beachesList = findAll() as ArrayList<BeachModel>
        var foundBeach: BeachModel? = beachesList.find { p -> p.id == beach.id }
        if (foundBeach != null) {
            foundBeach.title = beach.title
            foundBeach.description = beach.description
            foundBeach.size = beach.size
            foundBeach.walk = beach.walk
            foundBeach.image = beach.image
            foundBeach.lat = beach.lat
            foundBeach.lng = beach.lng
            foundBeach.zoom = beach.zoom
        }
        serialize()
    }

    override fun delete(beach: BeachModel) {
        beaches.remove(beach)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(beaches, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        beaches = gsonBuilder.fromJson(jsonString, listType)
    }

    //Log all beaches.
    private fun logAll() {
        beaches.forEach { Timber.i("$it") }
    }
}

//Parses Uri image property
class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}