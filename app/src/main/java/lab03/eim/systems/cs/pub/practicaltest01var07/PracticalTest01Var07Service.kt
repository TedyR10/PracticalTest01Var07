package lab03.eim.systems.cs.pub.practicaltest01var07

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import java.util.*
import kotlin.random.Random
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PracticalTest01Var07Service : Service() {

    private val handler = Handler()
    private val random = Random(System.currentTimeMillis())
    private lateinit var broadcastRunnable: Runnable

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        broadcastRunnable = Runnable {
            val broadcastIntent = Intent("com.example.broadcast.RANDOM_NUMBERS").apply {
                putExtra("random1", random.nextInt(100))
                putExtra("random2", random.nextInt(100))
                putExtra("random3", random.nextInt(100))
                putExtra("random4", random.nextInt(100))
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)  // Use LocalBroadcastManager

            handler.postDelayed(broadcastRunnable, 10000) // repeat every 10 seconds
        }
        handler.post(broadcastRunnable)

        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(broadcastRunnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
