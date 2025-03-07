package com.cycolabs.p29coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.cycolabs.p29coroutines.databinding.ActivitySerialparralelBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class serialparralel : AppCompatActivity() {

    private lateinit var binding: ActivitySerialparralelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serialparralel)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_serialparralel)

        //serial
        binding.btnSerial.setOnClickListener{
            Log.v("TAGY", "Seial coroutin start")
            CoroutineScope(Dispatchers.IO).launch{
                val one = doSomethingFun1Serial()
                val two = doSomethingFun2Serial()
                val result = one + two
                Log.v("TAGY", "Result is: ${result}")
            }
        }


        //parallel
        binding.btnParallel.setOnClickListener {
            Log.v("TAGY", "Parallel coroutin start")
            CoroutineScope(Dispatchers.IO).launch{
                val one = async{
                    doSomethingFun1Serial()
                }
                val two = async {
                    doSomethingFun2Serial()
                }
                val result = one.await() + two.await()
                Log.v("TAGY", "Result is: ${result}")
            }
        }
    }

    suspend fun doSomethingFun1Serial(): Int{
        delay(5000)
        Log.v("TAGY", "Fun 1 is Done")
        return 11
    }

    suspend fun doSomethingFun2Serial(): Int{
        delay(7000)
        Log.v("TAGY", "Fun 2 is Done")
        return 22
    }

    suspend fun doSomethingFun1Parallel(): Int{
        delay(9000)
        Log.v("TAGY", "Fun 1 is Done")
        return 11
    }

    suspend fun doSomethingFun2Parallel(): Int{
        delay(7000)
        Log.v("TAGY", "Fun 2 is Done")
        return 22
    }
}