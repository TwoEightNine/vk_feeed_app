package global.msnthrp.feeed.utils

import android.util.Base64
import java.security.MessageDigest
import java.util.*

fun md5Raw(plain: ByteArray) = MessageDigest
        .getInstance("MD5")
        .digest(plain)

fun md5(plain: String) = md5Raw(plain.toByteArray())
        .map { Integer.toHexString(it.toInt() and 0xff) }
        .map { if (it.length == 2) it else "0$it" }
        .joinToString(separator = "")

fun sha256Raw(plain: ByteArray) = MessageDigest
        .getInstance("SHA-256")
        .digest(plain)

fun sha256(plain: String) = sha256Raw(plain.toByteArray())
        .map { Integer.toHexString(it.toInt() and 0xff) }
        .map { if (it.length == 2) it else "0$it" }
        .joinToString(separator = "")

fun randomString() = UUID.randomUUID().toString().replace("-", "")

fun bytesToHex(bytes: ByteArray) = bytes
        .map { Integer.toHexString(it.toInt() and 0xff) }
        .map { if (it.length == 2) it else "0$it" }
        .joinToString(separator = "")

fun getUiFriendlyHash(hash: String) = hash
        .mapIndexed { index, c -> if (index % 2 == 0) c.toString() else "$c " } // spaces
        .mapIndexed { index, s -> if (index % 16 == 15) "$s\n" else s } // new-lines
        .map { it.toUpperCase() }
        .joinToString(separator = "")

fun toBase64(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.NO_WRAP)

fun fromBase64(text: String) = Base64.decode(text, Base64.NO_WRAP)