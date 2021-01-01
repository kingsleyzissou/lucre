package com.aquatic.lucre.models

/**
 * Abstract class that is used
 * as the base class for all the models.
 *
 * This is to facilitate a more generic CRUDStore,
 * by ensuring that each store has an id element.
 */
interface Model {
    var id: String?
    var userId: String?
}
