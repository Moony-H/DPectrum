package com.example.dpectrum.util

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.example.dpectrum.databinding.DialogBasicBinding
import com.example.dpectrum.databinding.DialogConfirmBinding
import com.google.gson.internal.bind.TypeAdapters.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL


class Util {
    companion object{

        fun loadImage(url:String, completed:(Bitmap?)->Unit){
            if(url.isEmpty()){
                completed(null)
                return
            }
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap=BitmapFactory.decodeStream(URL(url).openStream())
                    withContext(Dispatchers.Main){
                        completed(bitmap)
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.IO){
                        completed(null)
                    }
                }
            }
        }

        fun dpToPixel(dp: Int,context: Context): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }
        private fun setDialogSize(context: Context, dialog: Dialog, width: Int, height: Int){
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val window = dialog.window
            window?.setLayout(width, height)

        }

        fun openBasicDialog(context: Context?,layoutInflater:LayoutInflater,title:String,content:String){
            context?.let{
                val dialog=Dialog(it)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val dialogBinding= DialogBasicBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)
                dialogBinding.dialogBasicTitle.text=title
                dialogBinding.dialogBasicContent.text=content
                dialogBinding.dialogBasicCloseButton.setOnClickListener {
                    dialog.dismiss()
                }
                setDialogSize(it,dialog,dpToPixel(300,it),Util.dpToPixel(200,it))
                dialog.show()
            }
        }

        fun openConfirmDialog(context: Context?,layoutInflater:LayoutInflater,title:String,content:String,ok:(Dialog)->Unit,cancel:(Dialog)->Unit){
            context?.let{
                val dialog=Dialog(it)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val dialogBinding= DialogConfirmBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)
                dialogBinding.dialogConfirmTitle.text=title
                dialogBinding.dialogConfirmContent.text=content
                dialogBinding.dialogConfirmXButton.setOnClickListener {
                    cancel(dialog)
                }
                dialogBinding.dialogConfirmOButton.setOnClickListener {
                    ok(dialog)
                }
                setDialogSize(it,dialog,dpToPixel(300,it),Util.dpToPixel(200,it))
                dialog.show()
            }
        }
    }
}