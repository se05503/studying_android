package com.example.fastcampus

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SharedPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // SharedPreference 사용법
        findViewById<TextView>(R.id.create).setOnClickListener {
            // Create
            /*
            mode 설명
            - MODE_PRIVATE -> 현재 앱에서만 사용을 한다 = 다른 앱과 데이터를 공유하지 않는다.
            - MODE_WORLD_READABLE -> 다른 앱에서도 사용 가능 (읽기만 가능)
            - MODE_WORLD_WRITEABLE -> 다른 앱에서도 사용 가능 (읽기, 쓰기 가능)
            - MODE_MULTI_PROCESS -> 실제 다른 앱에서 사용중인지 체크하는 것
            - MODE_APPEND -> 기존 preference 에 신규로 데이터를 추가하는 경우
            - 큰 기업이 아니라면 99%는 MODE_PRIVATE 를 많이 사용한다.
             */
            val sharedPreferences = getSharedPreferences("table_name", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =
                sharedPreferences.edit() // 데이터를 새로 저장하는 경우에는 editor 도 필요하다.
            editor.putString("key1", "value1") // key-value 방식
            editor.putString("key2", "value2")
            editor.commit() // 작업 단위의 마침표. commit 을 기점으로 실제로 데이터가 저장이 된다. putString 함수를 썼을 때 데이터가 저장되는 것이 아니다.
        }

        findViewById<TextView>(R.id.read).setOnClickListener {
            // READ
            // 저장한 데이터를 불러올려면 table 이름이 동일해야 한다.
            // defValue (default Value) : key 이름을 잘못 넣었을 때, return 될 데이터
            val sharedPreference = getSharedPreferences("table_name", Context.MODE_PRIVATE)
            val valueOne = sharedPreference.getString("key1", "key1 value isn't exist")
            val valueTwo = sharedPreference.getString("key2", "key2 value isn't exist")
            Log.d("value 1 : ", valueOne!!)
            Log.d("value 2 : ", valueTwo!!)
        }

        findViewById<TextView>(R.id.update).setOnClickListener {
            /*
            update
            - 데이터의 생성 및 변경을 가할 때는 editor가 필요하다.
            - 데이터의 생성 과정과 비슷하다.
            - 동일한 key 에 다른 value 값을 넣어주면 된다.
             */
            val sharedPreferences = getSharedPreferences("table_name", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("key1", "update value1")
            editor.commit()
        }

        findViewById<TextView>(R.id.delete).setOnClickListener {
            // editor.clear() -> 모든 데이터(name: "table_name" 에 해당하는 데이터들만)가 삭제됨.
            val sharedPreferences = getSharedPreferences("table_name", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // "table_name" 에 해당하는 파일 내에 있는 모든 데이터를 삭제함
            editor.commit()
        }
    }
}