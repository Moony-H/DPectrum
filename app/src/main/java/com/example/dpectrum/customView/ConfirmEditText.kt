package com.example.dpectrum.customView

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dpectrum.R
import com.example.dpectrum.databinding.SourceCustomConfirmEditTextBinding

class ConfirmEditText:ConstraintLayout {

    private lateinit var binding: SourceCustomConfirmEditTextBinding
    private var edittextCondition= {_:String->true}
    private var isConfirm=false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        attrs?.let {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ConfirmEditText)
            val title =
                typeArray.getString(R.styleable.ConfirmEditText_custom_confirmEdittext_title)
            title?.let {
                binding.sourceCustomConfirmTitle.text = it
            }

            val hint = typeArray.getString(R.styleable.ConfirmEditText_custom_confirmEdittext_hint)
            hint?.let {
                binding.sourceCustomConfirmContent.hint = it
            }
            val type=typeArray.getInt(R.styleable.ConfirmEditText_android_inputType,EditorInfo.TYPE_NULL)
            if(type!=EditorInfo.TYPE_NULL)
                binding.sourceCustomConfirmContent.inputType=type

            val maxLength=typeArray.getInt(R.styleable.ConfirmEditText_android_maxLength,100)

            val editText = binding.sourceCustomConfirmContent
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

            typeArray.recycle()
        }
    }

    private fun init() {
        binding = SourceCustomConfirmEditTextBinding.inflate(LayoutInflater.from(context), this)
        binding.sourceCustomConfirmContent.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isConfirm=edittextCondition(text.toString())
            }
        })
    }

    fun setErrorMessage(message:String){
        binding.sourceCustomConfirmErrorMessage.text=message
    }

    fun getEdittextContent():String{
        return binding.sourceCustomConfirmContent.text.toString()
    }

    fun setEdittextContentErrorCondition(condition:(String)->Boolean){
        edittextCondition=condition
    }

    fun getConfirmState():Boolean{
        return isConfirm
    }

    fun setConfirmState(state:Boolean){
        isConfirm=state
    }

}