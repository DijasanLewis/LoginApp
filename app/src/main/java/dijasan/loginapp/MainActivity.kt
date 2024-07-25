package dijasan.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dijasan.loginapp.ui.theme.LoginAppTheme
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private fun initComponent(){
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()

        loginButton.setOnClickListener {
            login()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, R.string.fieldIsEmpty, Toast.LENGTH_SHORT).show()
            return
        }

        val jsonString = loadJSONFromAsset("users.json")
        val jsonObject = JSONObject(jsonString)
        val usersArray = jsonObject.getJSONArray("users")

        var isLoginSuccessful = false
        for (i in 0 until usersArray.length()) {
            val user = usersArray.getJSONObject(i)
            if (user.getString("username") == username && user.getString("password") == password){
                isLoginSuccessful = true
                break
            }
        }

        if (isLoginSuccessful){
            Toast.makeText(this, R.string.loginSuccessfully, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.loginFailed, Toast.LENGTH_SHORT).show()
        }


    }

   private fun loadJSONFromAsset(fileName : String): String {
       val inputStream = assets.open(fileName)
       val size = inputStream.available()
       val buffer = ByteArray(size)
       inputStream.read(buffer)
       inputStream.close()
       return String(buffer, Charset.forName("UTF-8"))
   }

}
