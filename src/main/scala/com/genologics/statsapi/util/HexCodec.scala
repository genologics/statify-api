package com.genologics.statsapi.util

/**
 * HexCodec translates between byte array and hex string.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */
object HexCodec {
    /**
     * Converts a hex encoded string (optionally starting with 0x) to a byte array.
     */
    def apply(hex:String) : Array[Byte] = {
        (for { i <- 0 to hex.length-1 by 2 if i > 0 || !hex.startsWith( "0x" )}
             yield hex.substring( i, i+2 ))
                .map( Integer.parseInt( _, 16 ).toByte ).toArray
    }

    /**
     * Converts a byte array to a hex encoded string.
     */
    def apply(bytes: Array[Byte]) : String = {
        def cvtByte( b: Byte ): String = {
            val hex = b & 0xff
            (if (( hex ) < 0x10 ) "0" else "" ) + Integer.toHexString( hex )
        }

        "0x" + bytes.map( cvtByte( _ )).mkString.toUpperCase
    }
}
