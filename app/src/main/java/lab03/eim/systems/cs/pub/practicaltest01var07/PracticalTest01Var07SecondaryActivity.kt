package lab03.eim.systems.cs.pub.practicaltest01var07

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var07SecondaryActivity : AppCompatActivity() {

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var07_secondary)

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)

        val value1 = intent.getIntExtra("value1", 0)
        val value2 = intent.getIntExtra("value2", 0)
        val value3 = intent.getIntExtra("value3", 0)
        val value4 = intent.getIntExtra("value4", 0)

        textView1.text = value1.toString()
        textView2.text = value2.toString()
        textView3.text = value3.toString()
        textView4.text = value4.toString()

        findViewById<Button>(R.id.sumButton).setOnClickListener {
            val sum = value1 + value2 + value3 + value4
            returnResult("Sum: $sum")
        }

        findViewById<Button>(R.id.productButton).setOnClickListener {
            val product = value1 * value2 * value3 * value4
            returnResult("Product: $product")
        }
    }

    private fun returnResult(result: String) {
        val intent = Intent()
        intent.putExtra("result", result)
        setResult(RESULT_OK, intent)
        finish()
    }
}
