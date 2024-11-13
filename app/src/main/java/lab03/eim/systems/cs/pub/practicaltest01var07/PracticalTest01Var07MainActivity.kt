package lab03.eim.systems.cs.pub.practicaltest01var07

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.random.Random
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07MainActivity : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText

    private var sum: Int = 0
    private var product: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var07_main)

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        editText3 = findViewById(R.id.editText3)
        editText4 = findViewById(R.id.editText4)

        findViewById<Button>(R.id.setButton).setOnClickListener {
            val values = listOf(editText1, editText2, editText3, editText4).mapNotNull {
                it.text.toString().toIntOrNull()
            }

            if (values.size == 4) {
                sum = values.sum()
                product = values.reduce { acc, i -> acc * i }

                Toast.makeText(this, "Sum: $sum, Product: $product", Toast.LENGTH_LONG).show()

                val intent = Intent(this, PracticalTest01Var07SecondaryActivity::class.java).apply {
                    putExtra("value1", values[0])
                    putExtra("value2", values[1])
                    putExtra("value3", values[2])
                    putExtra("value4", values[3])
                }
                startActivityForResult(intent, 1)
            } else {
                Toast.makeText(this, "All fields must contain numbers", Toast.LENGTH_SHORT).show()
            }
        }

        savedInstanceState?.let {
            sum = it.getInt("sum")
            product = it.getInt("product")
        }

        val serviceIntent = Intent(this, PracticalTest01Var07Service::class.java)
        startService(serviceIntent)
        Log.d("PracticalTest01Var07MainActivity", "Service started")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("sum", sum)
        outState.putInt("product", product)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("result")
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("PracticalTest01Var07MainActivity", "Broadcast received")
            val random1 = intent.getIntExtra("random1", 0)
            val random2 = intent.getIntExtra("random2", 0)
            val random3 = intent.getIntExtra("random3", 0)
            val random4 = intent.getIntExtra("random4", 0)

            editText1.setText(random1.toString())
            editText2.setText(random2.toString())
            editText3.setText(random3.toString())
            editText4.setText(random4.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Log.d("PracticalTest01Var07MainActivity", "Broadcast receive")
        registerReceiver(broadcastReceiver, IntentFilter("com.example.broadcast.RANDOM_NUMBERS"),
            RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }


    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(this, PracticalTest01Var07Service::class.java)
        stopService(serviceIntent)
        Log.d("PracticalTest01Var07MainActivity", "Service stopped")
    }
}
