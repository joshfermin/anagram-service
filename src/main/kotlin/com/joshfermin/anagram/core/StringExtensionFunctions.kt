package com.joshfermin.anagram.core

fun String.sortByCharsAsc(): String = this.toCharArray().sorted().joinToString("")
