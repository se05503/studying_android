package com.example.fastcampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Activity 생성할 때마다 하는 작업
1. Edit Configuration 에서 Launcher Activity 바꿔주기
2. manifest 파일에서 Launcher Activity 바꿔주기

room 을 사용하려면 dependency 를 추가해야 한다.
- room 을 사용한다 = SQLite 를 쓴다 = 관계형 데이터베이스를 쓴다.
- 관계형 데이터베이스 -> 행과 열 형태 -> 표를 만들어야 한다.
 */

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
    }
}

/*
- DB 에서 해당 클래스를 Entity 로 사용하겠다.
- 코틀린 코드에서는 camelCase를 지향하지만, 열의 필드 이름으로는 snake_case 가 지향된다 -> @ColumnInfo 사용
- id 값은 unique함 = id 가 동일한 데이터는 없다.
- 데이터 내용이 같아도 이 둘을 구별하려면 id 값을 지정해야 한다.
- id 로 데이터를 구별함 -> Primary Key라고 함 = 데이터를 구별하기 위해 사용하는 유니크 값
- dao : 데이터에 접근하는 객체
*/

@Entity
class UserProfile(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "first_name")
    val firstName: String
)