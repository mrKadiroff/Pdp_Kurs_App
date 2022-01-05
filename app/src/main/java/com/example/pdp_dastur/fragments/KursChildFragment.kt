package com.example.pdp_dastur.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.databinding.FragmentKursBinding
import com.example.pdp_dastur.databinding.FragmentKursChildBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Kurs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursChildFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursChildFragment : Fragment() {
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

    lateinit var binding: FragmentKursChildBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKursChildBinding.inflate(layoutInflater,container,false)
        val kursss = arguments?.getSerializable("kursi") as Kurs

        binding.descr.text = kursss.kurs_description
        binding.toolbarChild.title = kursss.kurs_name

        binding.toolbarChild.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.addd.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("Qurs", kursss)
            findNavController().navigate(R.id.kursStudentAddFragment,bundle)
        }




        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KursChildFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursChildFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}