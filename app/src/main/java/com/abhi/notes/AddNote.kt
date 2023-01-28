package com.abhi.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.abhi.notes.Models.Note
import com.abhi.notes.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "AddNote"
class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var newNote: Note
    private lateinit var oldNote: Note
    var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        }catch (e: Exception){
            e.printStackTrace()
        }
        binding.imgSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note = binding.etNote.text.toString()

            if(title.isNotEmpty() || note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                if(isUpdate){
                    newNote = Note(oldNote.id, title,note,formatter.format(Date()))
                }else{
                    newNote = Note(
                        null,title,note,formatter.format(Date())
                    )
                }
                Log.i(TAG,"$title , $note , ${formatter.format(Date())}")
                val intent = Intent()
                intent.putExtra("note",newNote)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                Toast.makeText(this@AddNote,"Please fill both fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

    }
}