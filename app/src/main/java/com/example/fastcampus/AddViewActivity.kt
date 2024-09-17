package com.example.fastcampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.io.File

/*

 */
class AddViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_view)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        var snackList = mutableListOf<Car>()
        for(i in 1..20) {
            // i + "" -> i 가 문자열 변환이 안됨 (오류)
            // "" + i -> i 가 문자열 변환이 됨
            snackList.add(Car(""+i+"번째 자동차", ""+i+"번째 엔진"))
        }
        val container = findViewById<LinearLayoutCompat>(R.id.container)
        // Inflater : xml 을 코드상으로 가져온다.
        val inflater = LayoutInflater.from(this@AddViewActivity)
        snackList.forEach {
            // xml 을 통으로 가져오기. root 를 null 로 넣는 이유는 나중에 addView 함수로 부모 뷰에 붙일 것이라서
            val carItemView = inflater.inflate(R.layout.car_item, null)
            val carImage = carItemView.findViewById<ImageView>(R.id.carImage)
            val carVersion = carItemView.findViewById<TextView>(R.id.nthCar)
            val carEngine = carItemView.findViewById<TextView>(R.id.nthEngine)

            carImage.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_background, this.theme))
            carVersion.text = it.version
            carEngine.text = it.engine

            // 부모 뷰에 붙여야함
            container.addView(carItemView)
        }
    }
}

class Car(val version: String, val engine: String)