package com.example.asyncandawaitkotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    val TAG = "debugAyush"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"Starting of Main")
        val tvDisplay: TextView = findViewById(R.id.tvDisplay)

        GlobalScope.launch (Dispatchers.IO){ // this will take 3.014 secs
            val time = measureTimeMillis {
                val answer1 = async { networkCall1() }
                val answer2 = async { networkCall2() } // the async will create a new coroutine..

                Log.d(TAG, "Answer1 is ${answer1.await()}") // the await function awaits for completion of this value without blocking a thread and resumes when deferred computation is complete, returning the resulting value
                Log.d(TAG, "Answer2 is ${answer2.await()}")
            }
            Log.d(TAG,"The Time Taken by the Networks Calls is : $time")

        }

//        GlobalScope.launch (Dispatchers.IO){ // This will take 3.038 seconds, but it was very bad practice..!
//            val time = measureTimeMillis {
//                var answer1 :String? = null
//                var answer2 :String? = null
//                val job1 = launch { answer1 = networkCall1() }
//                val job2 = launch { answer2 = networkCall2() }
//                job1.join()
//                job2.join()
//
//                Log.d(TAG, "Answer1 is $answer1")
//                Log.d(TAG, "Answer2 is $answer2")
//            }
//            Log.d(TAG,"The Time Taken by the Networks Calls is : $time")
//
//        }

//        GlobalScope.launch (Dispatchers.IO){ // this will give a time of 6 secs, as one network calls will affect the other network call as both gets executed in the same thread
//            val time = measureTimeMillis {
//                val answer1 = networkCall1()
//                val answer2 = networkCall2()
//
//                Log.d(TAG, "Answer1 is $answer1")
//                Log.d(TAG, "Answer2 is $answer2")
//            }
//            Log.d(TAG,"The Time Taken by the Networks Calls is : $time")
//
//        }
    }

    suspend fun networkCall1() : String{
        delay(3000L)
        return "Answer 1"
    }

    suspend fun networkCall2() : String{
        delay(3000L)
        return "Answer 2"
    }
}

/*
To display Text in the Text VIew Inside A coroutine Dispatcher.IO, one must be need to post it, using .post{}
Example: tvDisplay.post{tvDisplay.text = answer1}
 */