package com.aquatic.lucre.utilities

import java.io.* // ktlint-disable no-wildcard-imports
import java.lang.Exception
import javax.json.Json
import javax.json.JsonObject

/**
 * Method for writing JSON string
 * contents to file
 */
fun write(filename: String, data: String) {
    val file = File(filename)
    try {
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(file))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        error { "Error writing to file: $e" }
    }
}

/**
 * Method for reading a JSON file and
 * converting it to a JSON object
 * reference:
 *
 * https://stackoverflow.com/a/39786725
 */
fun read(filename: String): JsonObject? {
    try {
        val inputStream = FileInputStream(filename)
        val reader = Json.createReader(inputStream)
        var jsonObject = reader.readObject()
        reader.close()
        return jsonObject
    } catch (e: FileNotFoundException) {
        error { "Cannot Find file: $e" }
    } catch (e: IOException) {
        error { "Cannot Read file: $e" }
    }
    return null
}

/**
 * Helper method for checking if a file
 * exists
 */
fun fileExists(filename: String): Boolean {
    val file = File(filename)
    return file.exists()
}
