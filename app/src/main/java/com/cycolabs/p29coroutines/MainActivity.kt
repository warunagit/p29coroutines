package com.cycolabs.p29coroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cycolabs.p29coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    //Couroutines start using .LAUNCH builder. Starts the corouting and returns it as a job
    //**only the original thread that created view, can touch its view.
    //inside coroutines, to switch between Dispatchers, should use withContext() method
    //CoroutineScope(Dispatchers.Main).launch -> withContext(Dispatchers.Main)
    //withContext is a suspend function, cancellable.
    //SuspendFunctions can paused, resumed, execute long running operations and..
    //wait for them to complete without blocking
    //2 routine patterns: serial, parrallel
    //serials just execute one after one
    //parallel methods should mark with "async", and results with the "await" key-word


    private lateinit var binding: ActivityMainBinding
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //with coroutine, it continues
        sayHelloFromMainThred()
        sayHelloFromBackgroundThread()

        //without coroutine, it crashes
        binding.btnCount.setOnClickListener{
            countNumber()
        }

        binding.btnDownload.setOnClickListener {
            downloadBigFile()
        }

        //switch between coroutines
        binding.btnSwitchCoroutine.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                downloadBigFileWithContext()
            }
        }

        binding.btnSerialParallel.setOnClickListener{
            val changePage =Intent(this, serialparralel::class.java)
            startActivity(changePage)
        }
    }

    private fun countNumber() {
        binding.tvTitle.text = counter++.toString()
    }

    private fun sayHelloFromMainThred() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.text1.text = "Hello from ${Thread.currentThread().name}"
        }
    }
    private fun sayHelloFromBackgroundThread() {
        CoroutineScope(Dispatchers.IO).launch {
            binding.text2.text = "Hello from ${Thread.currentThread().name}"
        }
    }

    private fun downloadBigFile() {
        for (i in 0..1000000){
            //Log.i("TAGY", "Downloading in thread $1 in ${Thread.currentThread().name}")
            binding.tvBigDownload.text = "$i in ${Thread.currentThread().name}"
        }
    }

    private suspend fun downloadBigFileWithContext() {
        withContext(Dispatchers.Main){
            for (i in 0..1000000){
                binding.tvBigDownload.text = "$i in ${Thread.currentThread().name}"
            }
        }
    }
}