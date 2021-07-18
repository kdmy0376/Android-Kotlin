package dw.koo.android.lottonumbergenerator

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        findViewById<View>(R.id.button).setOnClickListener {
            val intent = Intent(this@TestActivity, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            val intent = Intent(this@TestActivity, ConstellationActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            val intent = Intent(this@TestActivity, NameInputActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.button4).setOnClickListener {
            val intent = Intent(this@TestActivity, ResultActivity::class.java)
            startActivity(intent)
        }
    }

    fun goConstellation(view: View) {
        val intent = Intent(this@TestActivity, ConstellationActivity::class.java)
        startActivity(intent)
    }

    fun callWeb(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"))
        startActivity(intent)
    }
}