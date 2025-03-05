package com.cycolabs.p29coroutines

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //android use main thread to execute all executions like
    //drawing the views, executing logical code pices in a sequel manner..
    //So, when creating an app, should not block the main thread.
    //Should use multi-threaded approch to perform nework operations and logical operations
    //Multithreading starts with Async task->RxJava->Coroutines
    //Multithreading works in prallel ways
    //Coroutines are light-weight threads, run in parellel, can wait for each other, performance optimized
    //allows asynchronus, synchronus programming
    //allows execution to be suspend and resume
    //3 Levels: Global Scope - starts and ends with the app
    //Lifecycle Scope - stops when activity destroy, but should check activity status and cancel.
    //ViewModel Scope - same like lifecycle scope, but will live as viewmodel alive.
    //3 Dispatcher pool types- Use to specify which thread should be performed.
    //.Main - UI non blocking operations. UI related events android main/ui thread
    //.IO - network and disk operations. netorking or read/write files or any input/output
    //.DEFAULT - if not specified, this will use. sort large list, complex calculations
    //Couroutines start using .LAUNCH builder. Starts the corouting and return it as a job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}