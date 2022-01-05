package com.example.pdp_dastur.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.Mentor_Spinner
import com.example.pdp_dastur.databinding.FragmentGroupAddBinding
import com.example.pdp_dastur.databinding.FragmentViewPagerBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupAddFragment : Fragment() {
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
    lateinit var binding: FragmentGroupAddBinding
    lateinit var mentorSpinner: Mentor_Spinner
    lateinit var mentorList: List<Mentor>
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Mentor>
    lateinit var groupList: ArrayList<Guruh>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupAddBinding.inflate(layoutInflater,container,false)
        myDbHelper = MyDbHelper(binding.root.context)
        val kursa = arguments?.getSerializable("guruhbek") as Kurs
        mentorList = myDbHelper.getAllMentors()
        list = ArrayList()

        binding.toolbarGr.title = kursa.kurs_name


        groupList=myDbHelper.getAllGroup()
        //mentor spinner

        if (mentorList.isNotEmpty()){
            for (mentor in mentorList){
                if (mentor.mentor_kurs_id?.kurs_name == kursa.kurs_name) {
                    list.add(mentor)
                }
            }
        }

        mentorSpinner = Mentor_Spinner(list)
        binding.mentorName.adapter = mentorSpinner

        //vaqt spinner bn ishlash

        val adapter = ArrayAdapter<String>(binding.root.context,R.layout.spinner_group,resources.getStringArray(R.array.vaqt))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timee.adapter = adapter

        //kun spinner
        val adapter_kun = ArrayAdapter<String>(binding.root.context,R.layout.spinner_group,resources.getStringArray(R.array.kunlar))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.days.adapter = adapter_kun

        //add qilish
        binding.saqlash.setOnClickListener {
            val gr_name = binding.grNomi.text.toString()
            val mentor = list[binding.mentorName.selectedItemPosition]
            val time = binding.timee.selectedItem.toString()
            val dayss = binding.days.selectedItem.toString()
            val guruh = Guruh(gr_name,kursa,mentor,time,dayss,"process")
            myDbHelper.insertGroup(guruh)
            groupList.add(guruh)
            findNavController().popBackStack()

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
         * @return A new instance of fragment GroupAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}