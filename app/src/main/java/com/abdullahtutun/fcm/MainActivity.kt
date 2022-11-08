package com.abdullahtutun.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.abdullahtutun.fcm.model.NotificationData
import com.abdullahtutun.fcm.model.PushNotification
import com.abdullahtutun.fcm.service.RetrofitObject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val topic = "/topics/genelduyurular"
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
        FirebaseMessaging.getInstance().subscribeToTopic(topic)

        val token = FirebaseMessaging.getInstance().token.addOnSuccessListener {
            val dataMap = hashMapOf<String,String>()
            dataMap.put("token",it)
            dataMap.put("kullanici_adi","pixel3")

            db.collection("User").add(dataMap).addOnSuccessListener {

            }
        }

    }

    fun send(v: View){
        var title = title_et.text.toString()
        var message = message_et.text.toString()

        var data = NotificationData(title, message)
        var notification = PushNotification(data,topic)
        sendNotification(notification)
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitObject.api.postNotification(notification)
            if (response.isSuccessful){
                //Log.d("aaa", Gson().toJson(response))
            } else {
                Log.d("aaa", response.errorBody().toString())
            }


        } catch (e: Exception){

        }
    }
}