package com.example.fastcampus

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

/*
- Async -> 안드로이드에서 지원해주는 기능이 있음
 */

class AsyncActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        findViewById<TextView>(R.id.start).setOnClickListener {
            BackgroundAsyncTask(
                findViewById<ProgressBar>(R.id.progressBar),
                findViewById<TextView>(R.id.progressBarText)
            ).execute()
        }
        // thread 의 경우 start 함수로 호출, AsyncTask 는 execute 로 실행
    }
}

class BackgroundAsyncTask(
    val progressbar: ProgressBar,
    val progressText: TextView
) : AsyncTask<Int, Int, Int>() {

    /*
    AsynceTask -> deprecated 됨 (더이상 사용을 권장하지 않음)
    case1) A는 deprecated 되니, 대체로 B를 사용하세요.
    case2) A는 deprecated 될거에요. 하지만, 대체는 없어요. -> AsyncTask 의 경우
    AsyncTask 의 대체 -> 1. RxJava 2. Coroutine -> 보통 2번 방법이 권장됨
    Coroutine: 특정 기능을 뭉친 것. Multi tasking 및 동기/비동기 처리 방법을 묶어서 코루틴이라고 함.
    비동기 방식이 어떻게 동작하는 지 이해하는 것이 제일 중요함. (AsyncTask 의 구현 방식을 이해하는 것보다 중요함)

    AsyncTask<Params, Progress, Result>
    1. Params -> doInBackground
    2. Progress -> onProgressUpdate
    3. Result -> onPostExecute

    함수 네이밍은 중요하다 -> 해당 오버라이딩 함수 네이밍이 잘되어있음
    */

    var percent: Int = 0

    override fun doInBackground(vararg params: Int?): Int {
        // Background(사용자 눈에 보이지 않는 작업) <-> Foreground(앞, 사용자 눈에 보이는 작업, ui)
        while (isCancelled() == false) {
            // 취소되지 않았다 = 작업이 아직 끝나지 않은 경우
            percent++
            if (percent > 100) break
            else {
                // publish: 배포하다 -> 진행도를 배포하다
                // publishProgress 함수의 매개변수 값이 onProgresUpdate 오버라이드 함수의 param 으로 들어온다.
                Log.d("debug", percent.toString())
                publishProgress(percent)
            }
            Thread.sleep(100)
        }
        return percent
    }

    // alt + insert
    // ctrl + O (override method)

    override fun onPreExecute() {
        super.onPreExecute()
        // pre(전) + execute(실행하다)
        // asyncTask를 실행하기 전에 하고싶은 것
        percent = 0
        progressbar.setProgress(percent)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values) // super call 의 의미?
        // 진행 중에 업데이트하고 싶은 부분이 있는 경우
        progressbar.setProgress(values[0] ?: 0) // null 일 경우에는 0을 대입한다. (elvis 연산자)
        progressText.text = "퍼센트: ${values[0]}"
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        // post(이후, 끝) + execute(실행하다)
        // asyncTask 작업이 끝난 다음에 하고싶은 것
        progressText.text = "작업이 완료되었습니다."
    }

    override fun onCancelled() {
        super.onCancelled()
        // 해당 작업이 멈췄을 때 하고 싶은 것
        progressbar.setProgress(0)
        progressText.text = "작업이 취소되었습니다."
    }
}