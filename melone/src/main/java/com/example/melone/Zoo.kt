package com.example.melone

fun main() {
    val zoo = Zoo()
    zoo.zooAnimals.forEach { animal ->

    }
}

class Zoo {
    val 사람 = 포유류("안녕~")
    val 고양이 = 포유류("냥냥!")
    val 강아지 = 포유류("멍멍!")

    val 독수리 = 조류(100)
    val 참새 = 조류(200)
    val 펭귄 = 조류(300)

    val 뱀 = 파충류("스큐타")
    val 거북 = 파충류("갑각")
    val 도마뱀 = 파충류("각질 비늘")

    val 개구리 = 양서류(1)
    val 도룡뇽 = 양서류(2)

    val 상어 = 어류("심해")
    val 금붕어 = 어류("바다")
    val 연어 = 어류("강")

    val zooAnimals = listOf<Any>(사람, 고양이, 강아지, 독수리, 참새, 펭귄, 뱀, 거북, 도마뱀, 개구리, 도룡뇽, 개구리, 상어, 금붕어, 연어)
}

class 포유류(sound: String) {
    private val sound: String
    init {
        this.sound = sound
    }
    fun breastfeeding() {}
}

class 사람() {}
class 고양이() {}
class 강아지() {}
class 독수리() {}
class 참새() {}
class 펭귄() {}
class 뱀() {}
class 거북() {}
class 도마뱀() {}
class 개구리() {}
class 도룡뇽() {}
class 상어() {}
class 금붕어() {}
class 연어() {}

class 조류(flyCount: Int): animal {
    private val flyCount: Int
    init {
        this.flyCount = flyCount
    }
    fun hasWings() {}
}

class 파충류(scaleName: String): animal {
    private val scaleName: String
    init {
        this.scaleName = scaleName
    }
    fun shardSkin() {}
}

class 양서류(transformLv: Int): animal {
    private val transformLevel: Int
    init {
        transformLevel = transformLv
    }
    fun metamorphosis() {}
}

class 어류(swimPlace: String): animal {
    var swimPlace : String = ""
    init {
        this.swimPlace = swimPlace
    }
    fun swim() {}
}



