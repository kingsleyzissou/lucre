package com.aquatic.lucre.models

import javax.json.JsonObject
import javax.json.JsonObjectBuilder

/**
 * Abstract class that is used
 * as the base class for all the models.
 *
 * This is to facilitate a more generic CRUDStore,
 * by ensuring that each store has an id element.
 */
abstract class Model {
    abstract var id: String
    abstract fun updateModel(jsonObject: JsonObject)
    abstract fun toJSON(json: JsonObjectBuilder)
}
