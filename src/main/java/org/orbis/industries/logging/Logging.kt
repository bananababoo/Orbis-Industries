package org.orbis.industries.logging

object Log {
    fun info(category: Type,message: String) {
        println("[OrbisIndustries ${category.display}] $message")
    }
    enum class Type(val display: String){
        SCOREBOARD("Scoreboard")
    }
}