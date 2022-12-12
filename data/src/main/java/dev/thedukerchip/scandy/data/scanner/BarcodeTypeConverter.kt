package dev.thedukerchip.scandy.data.scanner

import androidx.room.TypeConverter

internal class BarcodeTypeConverter {

    @TypeConverter
    fun toBarcodeType(type: String) = enumValueOf<BarcodeType>(type)

    @TypeConverter
    fun toString(type: BarcodeType) = type.ordinal
}