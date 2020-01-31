package db.migration

import com.joshfermin.anagram.core.sortByCharsAsc
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.UUID
import java.util.zip.GZIPInputStream


class V1580342904__SeedDatabaseWithDictionaryWords() : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        val dictionaryInputStream = javaClass.classLoader.getResourceAsStream("dictionary.txt.gz")
        val gzipStream = GZIPInputStream(dictionaryInputStream)
        val decoder = InputStreamReader(gzipStream, StandardCharsets.UTF_8)
        val buffered = BufferedReader(decoder)
        var line = buffered.readLine()

        while (line != null) {
            context!!.connection.createStatement().use {
                update -> update.execute("INSERT INTO anagram_words VALUES ('${UUID.randomUUID().toString().replace("-", "")}', '${line}', '${line.sortByCharsAsc()}', ${line.length})")
            }
            line = buffered.readLine()
        }
    }
}
