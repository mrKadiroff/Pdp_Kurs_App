package com.example.pdp_dastur.fragments

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.ViewPagerAdapter
import com.example.pdp_dastur.databinding.FragmentViewPagerBinding
import com.example.pdp_dastur.models.Kurs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentViewPagerBinding
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(layoutInflater,container,false)
        val kurs = arguments?.getSerializable("gruh") as Kurs

        val adapter=ViewPagerAdapter(childFragmentManager,lifecycle,kurs)
        binding.viewPager.adapter=adapter

        adapter.notifyDataSetChanged()



        binding.tooolbar.title = kurs.kurs_name
        binding.tooolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }



        TabLayoutMediator(binding.tablayout,binding.viewPager){tab,position->
            when(position){
                0->{
                    tab.text="Ochilgan guruhlar"

                }
                1->{
                    tab.text="Ochilayotgan guruhlar"
                }
            }
        }.attach()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1){
                    binding.tooolbar.inflateMenu(R.menu.add_menu)
                }else{
                    binding.tooolbar.menu.clear()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                
            }

        })



        binding.tooolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add){
                var bundle = Bundle()
                bundle.putSerializable("guruhbek",kurs)
                findNavController().navigate(R.id.groupAddFragment,bundle)
            }
            true
        }

        val viewPager2 = view?.findViewById<ViewPager2>(R.id.view_pager)
        val mPagerAdapter = viewPager2?.adapter
        val currentPosition = viewPager2?.currentItem
        mPagerAdapter?.notifyDataSetChanged()
        viewPager2?.adapter = null
        viewPager2?.adapter =mPagerAdapter
        viewPager2?.setCurrentItem(currentPosition!!)





        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        val viewPager2 = view?.findViewById<ViewPager2>(R.id.view_pager)
        val mPagerAdapter = viewPager2?.adapter
        val currentPosition = viewPager2?.currentItem
        mPagerAdapter?.notifyDataSetChanged()
        viewPager2?.adapter = null
        viewPager2?.adapter =mPagerAdapter
        viewPager2?.setCurrentItem(currentPosition!!)
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}