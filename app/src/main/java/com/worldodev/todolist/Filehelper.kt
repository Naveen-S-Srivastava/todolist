package com.worldodev.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.collections.ArrayList

class FileHelper {
    val FILENAME = "listinfo.dat"

    fun writeData(item: ArrayList<String>, context: Context) {
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(item)
            it.close()
        }
    }
    fun readData(context: Context):ArrayList<String>{


        var itemList : ArrayList<String>
        try{
            var fis : FileInputStream = context.openFileInput(FILENAME)
            var ois = ObjectInputStream(fis)
            itemList = ois.readObject() as ArrayList<String>


        }catch (e :FileNotFoundException){
            itemList = ArrayList()
        }

        return itemList

    }
}
