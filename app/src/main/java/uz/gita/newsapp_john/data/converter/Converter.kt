package uz.gita.newsapp_john.data.converter

import androidx.room.TypeConverter
import uz.gita.newsapp_john.data.model.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source?): String {
        return if (source != null)
            source.name!!
        else
            ""
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}