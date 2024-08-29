package com.example.fastcampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

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

        // 3번째 파라미터 : 데이터베이스 이름 (테이블 이름이 아니다)
        // allowMainThreadQueries() -> 메인쓰레드에서 쿼리를 날리는 것을 허락한다 = 메인쓰레드에서 데이터베이스 조작을 하겠다.
        // 데이터베이스 조작은 기본적으로 메인쓰레드에서 진행할 수 없다. -> 데이터베이스가 복잡하게 설계된 경우 처리 속도가 길어지는데 메인쓰레드에서 처리하면 처리하는 동안 사용자는 다른 일을 할 수 없기 때문이다.
        // 1. Thread 2. AsyncTask -> 데이터베이스에 접근 및 관련 작업을 하면 됨
        val database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "user-database"
        ).allowMainThreadQueries() // 가능하긴 하지만 좋은 방식은 아님
            .build() // 데이터베이스 생성

        findViewById<TextView>(R.id.save).setOnClickListener {
            // 해당 userProfile 데이터를 데이터베이스에 저장하는 작업을 함
            val userProfile = UserProfile("박세영", 24, 'F')
            database.userProfileDao().insert(userProfile)
        }


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

// KClass -> class.java(x) class(o)
@Database(entities = [UserProfile::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    /*
    - version number : migration 을 위해서 필요하다.
    - UserProfile (entity) 를 MyDatabase 가 관리한다.
    - data base 자체를 여러개 만들어서 각각 관리하는 경우는 많진 않다. (하지만 가능하다.)
     */
    abstract fun userProfileDao(): UserProfileDao
}

@Dao
interface UserProfileDao {
    /*
    - CRUD : 데이터베이스 조작
    - Query : 데이터베이스 조회
     */
    @Insert(onConflict = REPLACE)
    fun insert(userProfile: UserProfile)

    @Delete
    fun delete(userProfile: UserProfile)

    @Query("SELECT * FROM userprofile")
    fun getAll(): List<UserProfile>
    // sql 문은 최적화가 어렵다. sql 문을 효율적으로 쓰기는 생각보다 어렵다.
    // sql 을 따로 배우는건 안드로이드 공부가 어느정도 마무리된 이후에 해보는 것 추천한다.
}

@Entity
class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int, // autoGenerate = true -> id 값이 자동으로 1씩 증가함
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "gender")
    val gender: Char
) {
    // 부생성자
    constructor(fullName: String, age: Int, gender: Char) : this(0, fullName, age, gender)
}

