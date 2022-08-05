
package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.dpectrum.databinding.FragmentHomeBinding
import com.example.dpectrum.tag.FragmentTag
import com.example.dpectrum.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var _binding:FragmentHomeBinding?=null
    private val binding:FragmentHomeBinding
        get()=_binding!!

    private var backKeyPressedTime=0L

    private val loginViewModel: LoginViewModel by hiltNavGraphViewModels(R.id.loginAndHomeNavigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.fragmentHomeBottomNavigationView.itemIconTintList=null
        loginViewModel.getContents()

        //네비게이션이 클릭되면 클릭된 버튼의 Id를 저장해 놓는다.
        //binding.fragmentHomeBottomNavigationView.setOnItemSelectedListener {
        //    Log.d("HomeFragment","Bottom navi view clicked: $it")
        //    loginViewModel.setCurrentPage(it.itemId)
        //}


        //호스트를 찾아서 nav controller를 넣기
        val navHostFragment=requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navHostFragment?.let {
            binding.fragmentHomeBottomNavigationView.setupWithNavController(it.navController)
            NavigationUI.setupWithNavController(binding.fragmentHomeBottomNavigationView,it.navController)
        }?:run{
            Log.e("HomeFragment","nav fragment not found")
        }



        //저장된 id를 관찰하여, id가 바뀌면(사용자가 클릭하면) Fragment를 전환한다.
        loginViewModel.currentPageTag.observe(viewLifecycleOwner){ it ->
            binding.fragmentHomeBottomNavigationView.selectedItemId
            //changeFragment(it)

        }
        loginViewModel.selectedContent.observe(viewLifecycleOwner){
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_homeDetailFragment)
        }

        //childFragmentManager.commit {
        //    add(R.id.fragment_home_fragment_container,HomeHomeFragment(),FragmentTag.FRAGMENT_HOME_HOME.fragmentTag)
        //}

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(context, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()

            } else {
                ActivityCompat.finishAffinity(requireActivity())
                exitProcess(0)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    //private fun changeFragment(tags: FragmentTag){
//
    //    childFragmentManager.commit {
//
    //        //사용자가 원하는 Fragment를 tag로 받고, 그것이 존재하는지 찾는다.
    //        var targetFragment=childFragmentManager.findFragmentByTag(tags.fragmentTag)
//
    //        targetFragment?:run{
    //            //Fragment add
    //            targetFragment=createFragment(tags)
    //            add(R.id.fragment_home_fragment_container,targetFragment!!,tags.fragmentTag)
    //        }
//
    //        //단순히 show, hide로 사용자가 원하는 Fragment만 보이게 만든다.
    //        show(targetFragment!!)
//
    //        //filterNot으로 show 한 Fragment(사용자가 요청한 Fragment)만 건너 뛰고, 나머지 Fragment들은 hide 한다.
    //        FragmentTag.values()
    //            .filterNot { it==tags }
    //            .forEach { type->
    //                childFragmentManager.findFragmentByTag(type.fragmentTag)?.let{
    //                    hide(it)
    //                }
    //            }
//
    //    }
//
//
    //}

    private fun createFragment(tags:FragmentTag):Fragment{
        return when(tags){
            FragmentTag.FRAGMENT_HOME_HOME -> HomeHomeFragment()
            FragmentTag.FRAGMENT_HOME_TUTOR-> HomeTutorFragment()
            FragmentTag.FRAGMENT_HOME_CHAT->HomeChatFragment()
            FragmentTag.FRAGMENT_HOME_MY->HomeMyFragment()


            else -> throw IllegalArgumentException("bottom navi view not found: fragment tag")
        }
    }

}


