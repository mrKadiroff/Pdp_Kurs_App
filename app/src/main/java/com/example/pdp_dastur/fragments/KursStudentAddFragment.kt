package com.example.pdp_dastur.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.Mentor_Spinner
import com.example.pdp_dastur.adapters.mentorAdapter
import com.example.pdp_dastur.databinding.FragmentKursBinding
import com.example.pdp_dastur.databinding.FragmentKursStudentAddBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor
import java.util.*

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
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKursStudentAddBinding.inflate(layoutInflater,container,false)
        myDbHelper = MyDbHelper(binding.root.context)
        val kurs = arguments?.getSerializable("Qurs") as Kurs
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