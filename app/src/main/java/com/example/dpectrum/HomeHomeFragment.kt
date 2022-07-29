package com.example.dpectrum

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dpectrum.adapter.ContentAdapter
import com.example.dpectrum.data.TutorContent
import com.example.dpectrum.databinding.FragmentHomeHomeBinding
import com.example.dpectrum.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeHomeFragment:Fragment() {

    private var _binding:FragmentHomeHomeBinding?=null
    private val binding:FragmentHomeHomeBinding
        get()=_binding!!

    private val viewModel:LoginViewModel by hiltNavGraphViewModels(com.example.dpectrum.R.id.loginAndHomeNavigation)


    private val items=arrayOf("전체","시각디자인과","애니메이션과","서양화과","조양학과")

    private lateinit var adapter:ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentHomeHomeBinding.inflate(inflater,container,false)
        val bailiwickSpinner=binding.fragmentHomeHomeBailiwick
        val schoolSpinner=binding.fragmentHomeHomeSchool


        bailiwickSpinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, items)
        schoolSpinner.adapter =ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, items)
        bailiwickSpinner.onItemSelectedListener=listener
        schoolSpinner.onItemSelectedListener=listener
        adapter=ContentAdapter{tutorContent,bitmap->
            Toast.makeText(requireContext(),"상세 이미지 페이지로 이동합니다.",Toast.LENGTH_SHORT).show()
            viewModel.setSelectedContents(tutorContent)
            viewModel.photo=bitmap

        }

        binding.fragmentHomeHomeRecycler.layoutManager=LinearLayoutManager(requireContext())
        binding.fragmentHomeHomeRecycler.adapter=adapter


        viewModel.sortedContents.observe(viewLifecycleOwner){
            Log.d("HomeHomeFragment","observe selectedContents: $it")
            adapter.submitList(it)
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding=null
    }

    private val listener=object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val selected= mutableListOf<TutorContent>()
            Log.d("position","$position")
            if(position==0){
                viewModel.allContents.value?.let { viewModel.setSortedContents(it) }
            }else{
                viewModel.allContents.value?.forEach {
                    if(it.tutorDepartment==items[position])
                        selected.add(it)
                }
                viewModel.setSortedContents(selected)
            }

        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }


}
