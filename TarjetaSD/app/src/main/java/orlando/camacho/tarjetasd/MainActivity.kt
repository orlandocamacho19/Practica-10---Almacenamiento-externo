package orlando.camacho.tarjetasd

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import orlando.camacho.tarjetasd.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
        }

        binding.btGuardar.setOnClickListener{
            Save(binding.etNuevoDato.text.toString())
            binding.tvContenido.text = Charge()
        }
    }

    fun Save(text: String){
        try {
            val SD = baseContext.getExternalFilesDir(null)?.absolutePath
            val myFolder = File(SD, "data")

            if (!myFolder.exists()) {
                myFolder.mkdir()
            }

            val physicalFile = File(myFolder, "data.txt")
            physicalFile.appendText("$text\n")
        }
        catch (e: Exception){
            Toast.makeText(this, "Unable save file", Toast.LENGTH_LONG).show()
        }
    }

    fun Charge () : String {
        var text = ""
        try {
            val SD = baseContext.getExternalFilesDir(null)?.absolutePath
            val myFolder = File(SD, "data")
            val physicalFile = File (myFolder, "data.txt")
            val file = BufferedReader(InputStreamReader(FileInputStream(physicalFile)))

            text = file.use(BufferedReader::readText)
        } catch (e : Exception){

        }
        return text
    }
}