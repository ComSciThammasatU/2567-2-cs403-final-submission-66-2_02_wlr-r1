package com.example.chatlaung16

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Base64
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.toolbox.StringRequest
import java.util.*

class PaymentActivity : AppCompatActivity() {

    private lateinit var etHouseNumber: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var cbCash: CheckBox
    private lateinit var cbTransfer: CheckBox
    private lateinit var btnUploadImage: Button
    private lateinit var btnSend: Button
    private lateinit var btnBack: Button

    private var imageUri: Uri? = null
    private val scriptUrl = "https://script.google.com/macros/s/AKfycbyCXE6SN9iuWKrR7AnThkQGTlQ5ypFYw1f8weRaZCF-3Zgbw7OniZrusOw2DfECUxJL/exec"
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        etHouseNumber = findViewById(R.id.etHouseNumber)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        cbCash = findViewById(R.id.cbCash)
        cbTransfer = findViewById(R.id.cbTransfer)
        btnUploadImage = findViewById(R.id.btnUpload)
        btnSend = findViewById(R.id.btnSend)
        btnBack = findViewById(R.id.btnBack)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                Toast.makeText(this, "เลือกรูปภาพแล้ว", Toast.LENGTH_SHORT).show()
            }
        }

        btnUploadImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                etDate.setText("%02d/%02d/%04d".format(day, month + 1, year))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                etTime.setText("%02d:%02d".format(hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        btnSend.setOnClickListener { handleSubmit() }

        btnBack.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun handleSubmit() {
        btnSend.isEnabled = false

        val houseNumber = etHouseNumber.text.toString().trim()
        val date = etDate.text.toString().trim()
        val time = etTime.text.toString().trim()
        val paymentTime = "$date $time"

        if (houseNumber.isEmpty() || date.isEmpty() || time.isEmpty()) {
            showToast("กรุณากรอกข้อมูลให้ครบ")
            btnSend.isEnabled = true
            return
        }

        if (!cbCash.isChecked && !cbTransfer.isChecked) {
            showToast("กรุณาเลือกวิธีการจ่ายเงิน")
            btnSend.isEnabled = true
            return
        }

        val method = if (cbCash.isChecked) "เงินสด" else "โอนเงิน"
        val base64Image = if (method == "โอนเงิน") convertImageToBase64(imageUri) else null

        if (method == "โอนเงิน" && base64Image == null) {
            showToast("กรุณาแนบรูปภาพการโอน")
            btnSend.isEnabled = true
            return
        }

        sendDataToSheet(houseNumber, paymentTime, method, base64Image)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun convertImageToBase64(uri: Uri?): String? {
        return try {
            uri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun sendDataToSheet(houseNumber: String, paymentTime: String, method: String, base64Image: String?) {
        val jsonBody = JSONObject().apply {
            put("houseNumber", houseNumber)
            put("paymentTime", paymentTime)
            put("method", method)
            put("imageBase64", base64Image ?: "")
        }

        val request = object : StringRequest(Method.POST, scriptUrl,
            Response.Listener {
                showToast("ส่งข้อมูลสำเร็จ")
                btnSend.isEnabled = true
            },
            Response.ErrorListener {
                showToast("ส่งข้อมูลไม่สำเร็จ")
                Log.e("VolleyError", "เกิดข้อผิดพลาด: ${it.localizedMessage}")
                btnSend.isEnabled = true
            }
        ) {
            override fun getBodyContentType() = "application/json; charset=utf-8"
            override fun getBody() = jsonBody.toString().toByteArray(Charsets.UTF_8)
        }

        request.retryPolicy = DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(this).add(request)
    }
}

