package com.example.chatlaung16

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Response

class ContactActivity : AppCompatActivity() {

    private lateinit var etMessage: EditText
    private lateinit var cbPersonal: CheckBox
    private lateinit var cbPublic: CheckBox
    private lateinit var btnSend: Button
    private val scriptUrl = "https://script.google.com/macros/s/AKfycbzJMoeYmifqum9yADJqSkZZuRv1oaXjI3rVVq-KeqcnqjMAXiWyAH5pLx8zz9zJFBHh/exec"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        etMessage = findViewById(R.id.etMessage)
        cbPersonal = findViewById(R.id.cbPersonal)
        cbPublic = findViewById(R.id.cbPublic)
        btnSend = findViewById(R.id.btnSend)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }
        btnSend.setOnClickListener {
            val message = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(this, "กรุณาใส่ข้อความ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val topicList = mutableListOf<String>()
            if (cbPersonal.isChecked) topicList.add("เรื่องส่วนตัว")
            if (cbPublic.isChecked) topicList.add("เรื่องส่วนรวม")

            if (topicList.isEmpty()) {
                Toast.makeText(this, "กรุณาเลือกประเภทของเรื่อง", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jsonBody = JSONObject()
            jsonBody.put("message", message)
            jsonBody.put("type", topicList.joinToString(", "))

            val queue = Volley.newRequestQueue(this)
            val request = object : StringRequest(
                Method.POST, scriptUrl,
                Response.Listener {
                    Toast.makeText(this, "ส่งข้อความสำเร็จ", Toast.LENGTH_SHORT).show()
                    etMessage.setText("")
                    cbPersonal.isChecked = false
                    cbPublic.isChecked = false
                },
                Response.ErrorListener {
                    Toast.makeText(this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
                override fun getBody(): ByteArray {
                    return jsonBody.toString().toByteArray(Charsets.UTF_8)
                }
            }
            queue.add(request)
        }
    }
}
