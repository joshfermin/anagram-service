package com.joshfermin.anagram.core

fun String.sortByCharsAsc(): String = this.toLowerCase().toCharArray().sorted().joinToString("")
