package dijasan.loginapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerUsername: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerConfirmPassword: EditText
    private lateinit var registerButton: Button

    private fun initComponent(){
        registerUsername = findViewById(R.id.registerUsername)
        registerPassword = findViewById(R.id.registerPassword)
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword)
        registerButton = findViewById(R.id.registerSubmitButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initComponent()

        registerButton.setOnClickListener {
            val username = registerUsername.text.toString()
            val password = registerPassword.text.toString()
            val confirmPassword = registerConfirmPassword.text.toString()

            if (password == confirmPassword) {
                if (registerUser(username, password)) {
                    Toast.makeText(this, R.string.registerSuccessfully, Toast.LENGTH_SHORT).show()
                    finish() // Close register activity
                } else {
                    Toast.makeText(this, R.string.registerFailed, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.confirmPasswordFailed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username : String, password : String) : Boolean {
        val json : String?
        try {
            val inputStream = assets.open("users.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            // Create new user JSONObject
            val newUser = JSONObject()
            newUser.put("username", username)
            newUser.put("password", password)

            // Add new user to jsonArray
            jsonArray.put(newUser)

            // Save updated jsonArray to file
            val outputStream : FileOutputStream = openFileOutput("user.json", MODE_PRIVATE)
            outputStream.write(jsonArray.toString().toByteArray())
            outputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}