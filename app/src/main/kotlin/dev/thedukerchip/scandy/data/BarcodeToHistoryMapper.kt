package dev.thedukerchip.scandy.data

import dev.thedukerchip.scandy.camera.ScandyBarcode
import dev.thedukerchip.scandy.data.history.ScannedHistoryItem
import dev.thedukerchip.scandy.data.scanner.BarcodeType

object BarcodeToHistoryMapper {

    fun toHistoryItem(barcode: ScandyBarcode): ScannedHistoryItem? {
        return when (barcode) {
            is ScandyBarcode.Link -> ScannedHistoryItem(
                content = barcode.url ?: "",
                type = BarcodeType.Link
            )

            is ScandyBarcode.Text -> ScannedHistoryItem(
                content = barcode.text,
                type = BarcodeType.Text
            )

            ScandyBarcode.Unknown -> null
        }
    }
}