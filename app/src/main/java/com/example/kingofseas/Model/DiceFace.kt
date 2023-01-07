package com.example.kingofseas.Model

enum class DiceFace {
    FACE_ONE,
    FACE_TWO,
    FACE_THREE,
    ENERGY,
    HEALTH,
    DAMAGE
}

fun diceFaceToString(face: DiceFace): String {
    return when (face) {
        DiceFace.FACE_ONE -> "1"
        DiceFace.FACE_TWO -> "2"
        DiceFace.FACE_THREE -> "3"
        DiceFace.ENERGY -> "E"
        DiceFace.HEALTH -> "H"
        DiceFace.DAMAGE -> "D"
    }
}

fun diceFaceToInt(face: DiceFace): Int {
    return when (face) {
        DiceFace.FACE_ONE -> 1
        DiceFace.FACE_TWO -> 2
        DiceFace.FACE_THREE -> 3
        DiceFace.ENERGY -> 1
        DiceFace.HEALTH -> 1
        DiceFace.DAMAGE -> -1
    }
}