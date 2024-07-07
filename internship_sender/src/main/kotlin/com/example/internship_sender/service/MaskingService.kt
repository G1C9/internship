package com.example.internship_sender.service

import net.sourceforge.tess4j.ITessAPI
import net.sourceforge.tess4j.Tesseract
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Base64
import javax.imageio.ImageIO

@Service
class MaskingService {

    fun maskData(imageFile: File): ByteArray? {

        val tesseract =
            Tesseract().apply {
                setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata")
                setLanguage("rus")
            }

        val bi: BufferedImage = ImageIO.read(imageFile)
        val words = tesseract.getWords(bi, ITessAPI.TessPageIteratorLevel.RIL_WORD)
        val graph: Graphics2D = bi.createGraphics()
        graph.color = Color.BLACK

        words.forEach {
            if (it.text == "РАСЧЕТОВ") {
                graph.fill(
                    Rectangle(
                        122,
                        460,
                        200,
                        8
                    )
                )
            }
        }
        graph.dispose()

        val os = ByteArrayOutputStream()
        ImageIO.write(bi, "jpg", os)

        return Base64.getEncoder().encode(os.toByteArray())
    }




}