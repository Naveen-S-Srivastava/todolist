package com.worldodev.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var item :EditText
    lateinit var add : Button
    lateinit var listView: ListView
    var itemlist = ArrayList<String>()
    var fileHelper = FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.list)

        itemlist = fileHelper.readData(this)
        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemlist)
        listView.adapter = arrayAdapter
        add.setOnClickListener {
            var itemName : String =item.text.toString()
            itemlist.add(itemName)
            item.setText("")
            fileHelper.writeData(itemlist,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }
        listView.setOnItemClickListener { adapterView, view, position, l ->
            var alert =AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete the item from the list ?")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener{dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes",DialogInterface.OnClickListener{dialogInterface ,i->
                itemlist.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemlist,applicationContext)})
            alert.create().show()
        }

    }
}