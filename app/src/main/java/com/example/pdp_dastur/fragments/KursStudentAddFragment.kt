package com.example.pdp_dastur.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.adapters.Group_Spinner
import com.example.pdp_dastur.adapters.Mentor_Spinner
import com.example.pdp_dastur.databinding.FragmentKursStudentAddBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import com.example.pdp_dastur.models.Talaba
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursStudentAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursStudentAddFragment : Fragment() {
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

    lateinit var binding: FragmentKursStudentAddBinding
    lateinit var mentorSpinner: Mentor_Spinner
    lateinit var mentorList: List<Mentor>
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Mentor>
    lateinit var groupSpinner: Group_Spinner
    lateinit var groupList: List<Guruh>
    lateinit var g_list: ArrayList<Guruh>
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKursStudentAddBinding.inflate(layoutInflater,container,false)
        myDbHelper = MyDbHelper(binding.root.context)
        val kurs = arguments?.getSerializable("Qurs") as Kurs

        binding.toolbarChild.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        //woroking with calendar
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)


        binding.sana.setOnClickListener {
            val datePickerDialog = DatePickerDialog(binding.root.context,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.sana.setText("Date:" + dayOfMonth + "/ " + (month +1) + "/ "+ year)
            },myYear,myMonth,day)
            datePickerDialog.show()
        }

        //working with mentor spinner
        mentorList = myDbHelper.getAllMentors()
        list = ArrayList()

        if (mentorList.isNotEmpty()){
            for (mentor in mentorList){
                if (mentor.mentor_kurs_id?.kurs_name == kurs.kurs_name) {
                    list.add(mentor)
                }
            }
        }

        mentorSpinner = Mentor_Spinner(list)
        binding.mentor.adapter = mentorSpinner

        binding.mentor.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(binding.root.context,list[position].mentor_name,Toast.LENGTH_LONG).show()

                groupList = myDbHelper.getAllGroup()
                g_list = ArrayList()


                if (groupList.isNotEmpty()){
                    for (guruh in groupList){
                        if (guruh.gr_kurs_id?.kurs_name==kurs.kurs_name && guruh.gr_mentor_id?.mentor_name==list[position].mentor_name && guruh.gr_open == "process") {
                            g_list.add(guruh)
                        }
                    }
                }

                //working with group spinner



                var mentorr = list[binding.mentor.selectedItemPosition]


                groupSpinner = Group_Spinner(g_list)
                binding.guruh.adapter = groupSpinner

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.save.setOnClickListener {
            val fam = binding.familiya.text.toString().trim()
            val imya = binding.ismii.text.toString().trim()
            val otchestva = binding.otcc.text.toString().trim()
            val sana = binding.sana.text.toString().trim()
            val guruh = g_list[binding.guruh.selectedItemPosition]
            val talaba = Talaba(fam,imya,otchestva,sana,guruh)
            if (fam.isNotEmpty() && imya.isNotEmpty() && otchestva.isNotEmpty() && sana.isNotEmpty()){
                myDbHelper.insertStudent(talaba)
                Toast.makeText(binding.root.context,"Muvaffaqiyatli saqlandi",Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else{
                Toast.makeText(binding.root.context,"Ma'lumotlarni to'liq kiritmadingizku brat",Toast.LENGTH_SHORT).show()
            }


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
         * @return A new instance of fragment KursStudentAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursStudentAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}