package com.example.chatlaung16

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var etHomeNumber: EditText
    private lateinit var etDayin: EditText
    private lateinit var btnLogin: Button
    private val CHANNEL_ID = "monthly_channel"
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etHomeNumber = findViewById(R.id.editHouseNumber)
        etDayin = findViewById(R.id.editDate)
        btnLogin = findViewById(R.id.btnLogin)
        createNotificationChannel()
        requestNotificationPermissionAndThen {
            checkExactAlarmPermissionAndSchedule()
        }
        btnLogin.setOnClickListener {
            val homeId = etHomeNumber.text.toString()
            val dayin = etDayin.text.toString()
            if (homeId == "8800" && dayin == "000022") {
                Toast.makeText(this, "เข้าสู่ระบบผู้ดูแล", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminActivity::class.java))
                return@setOnClickListener
            }
            val idInt = homeId.toIntOrNull()
            val expectedPassword = idInt?.let { generatePasswordFromId(it) }
            if (expectedPassword == null) {
                Toast.makeText(this, "ID ไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
            } else if (dayin == expectedPassword) {
                Toast.makeText(this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Toast.makeText(this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generatePasswordFromId(id: Int): String? {
        val baseId = 8801
        val offset = id - baseId
        if (offset < 0 || id > 88168) return null
        val calendar = Calendar.getInstance().apply {
            set(2022, 0, 1)
            add(Calendar.DAY_OF_YEAR, offset)
        }
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        val month = (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val year = "22"
        return "$day$month$year"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Monthly Reminder"
            val descriptionText = "แจ้งเตือนทุกวันที่ 1 ของเดือน"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun requestNotificationPermissionAndThen(afterGranted: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
                return
            }
        }
        afterGranted()
    }
    private fun checkExactAlarmPermissionAndSchedule() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
                return
            }
        }
        scheduleMonthlyNotification()
         //scheduleNotificationIn30Seconds()
    }
    /*
    //แทน scheduleMonthlyNotification()
       private fun scheduleNotificationIn30Seconds() {
           val intent = Intent(this, MonthlyNotificationReceiver::class.java)
           val pendingIntent = PendingIntent.getBroadcast(
               this,
               0,
               intent,
               PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
           )
           val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
           val triggerAtMillis = SystemClock.elapsedRealtime() + 30 * 1000

           alarmManager.setExact(
               AlarmManager.ELAPSED_REALTIME_WAKEUP,
               triggerAtMillis,
               pendingIntent
           )
       }*/

 private fun scheduleMonthlyNotification() {
     val calendar = Calendar.getInstance().apply {
         set(Calendar.DAY_OF_MONTH, 1)
         set(Calendar.HOUR_OF_DAY, 9)
         set(Calendar.MINUTE, 1)
         set(Calendar.SECOND, 0)
         set(Calendar.MILLISECOND, 0)
         if (timeInMillis <= System.currentTimeMillis()) {
             add(Calendar.MONTH, 1)
         }
     }

     val intent = Intent(this, MonthlyNotificationReceiver::class.java)
     val pendingIntent = PendingIntent.getBroadcast(
         this,
         0,
         intent,
         PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
     )
     val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
     alarmManager.setExact(
         AlarmManager.RTC_WAKEUP,
         calendar.timeInMillis,
         pendingIntent
     )
 }

 override fun onRequestPermissionsResult(
     requestCode: Int, permissions: Array<out String>, grantResults: IntArray
 ) {
     super.onRequestPermissionsResult(requestCode, permissions, grantResults)
     if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE &&
         grantResults.isNotEmpty() &&
         grantResults[0] == PackageManager.PERMISSION_GRANTED
     ) {
         checkExactAlarmPermissionAndSchedule()
     }
 }

 override fun onResume() {
     super.onResume()
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
         ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
         == PackageManager.PERMISSION_GRANTED
     ) {
         checkExactAlarmPermissionAndSchedule()
     }
 }
}
