package com.example.pdp_dastur.guruh

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.databinding.FragmentGroupStudentAddBinding
import com.example.pdp_dastur.databinding.FragmentMentorAddBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Talaba
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Group_Student_AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Group_Student_AddFragment : Fragment() {
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

    lateinit var binding: FragmentGroupStudentAddBinding
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupStudentAddBinding.inflate(layoutInflater,container, false)
        val guruh = arguments?.getSerializable("talaba") as Guruh
        val qosh = arguments?.getString("qosh")

        myDbHelper = MyDbHelper(binding.root.context)

        //woroking with calendar
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        binding.datee.setOnClickListener {
            val datePickerDialog = DatePickerDialog(binding.root.context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.datee.setText("Date:" + dayOfMonth + "/ " + (month +1) + "/ "+ year)
            },myYear,myMonth,day)
            datePickerDialog.show()
        }

        if (qosh!=null){
            binding.qoshish.setOnClickListener {
                val fam = binding.famil.text.toString().trim()
                val imya = binding.ismiigu.text.toString().trim()
                val otch = binding.otasining.text.toString().trim()
                val sana = binding.datee.text.toString().trim()
                if (fam.isNotEmpty() && imya.isNotEmpty()&&otch.isNotEmpty()&&sana.isNotEmpty()){
                    val talaba = Talaba(fam,imya,otch,sana,guruh)
                    myDbHelper.insertStudent(talaba)
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(binding.root.context,"Malumot to'liq kiritng iltimos",Toast.LENGTH_SHORT).show()
                }

        }

//            val bundle = Bundle()




        }

        val edit = arguments?.getString("edit")
        if (edit!=null){
            val student = arguments?.getSerializable("student") as Talaba
            binding.qoshish.setText("O'zgartirish")

            //settext
            binding.famil.setText(student.talaba_surname)
            binding.ismiigu.setText(student.talaba_name)
            binding.otasining.setText(student.talaba_otch)
            binding.datee.setText(student.talaba_date)

            //update

            binding.qoshish.setOnClickListener {
                val fam = binding.famil.text.toString().trim()
                val imya = binding.ismiigu.text.toString().trim()
                val otch = binding.otasining.text.toString().trim()
                val sana = binding.datee.text.toString().trim()


                if (fam.isNotEmpty() && imya.isNotEmpty()&&otch.isNotEmpty()&&sana.isNotEmpty()){
                    student.talaba_surname = binding.famil.text.toString().trim()
                    student.talaba_name = binding.ismiigu.text.toString().trim()
                    student.talaba_otch = binding.otasining.text.toString().trim()
                    student.talaba_date = binding.datee.text.toString().trim()
                    myDbHelper.updateStudent(student)
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(binding.root.context,"Malumot to'liq kiritng iltimos",Toast.LENGTH_SHORT).show()
                }



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
         * @return A new instance of fragment Group_Student_AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Group_Student_AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}