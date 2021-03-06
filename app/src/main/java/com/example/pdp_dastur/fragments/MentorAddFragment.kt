package com.example.pdp_dastur.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.databinding.FragmentMentorAddBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorAddFragment : Fragment() {
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


    lateinit var binding: FragmentMentorAddBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var mentorList:ArrayList<Mentor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMentorAddBinding.inflate(layoutInflater,container, false)

        var kurss = arguments?.getSerializable("kalit") as Kurs
        var orni = arguments?.getInt("pozit")

        binding.toolbarMentor.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        myDbHelper = MyDbHelper(binding.root.context)
        mentorList=myDbHelper.getAllMentors()

        binding.add.setOnClickListener {
            var same = false
            val familiya = binding.familyasi.text.toString().trim()
            val ismi = binding.ismi.text.toString().trim()
            val otchestva = binding.otchestva.text.toString().trim()
            val mentor = Mentor(familiya,ismi,otchestva,kurss)


            if (familiya.isNotEmpty() && ismi.isNotEmpty() && otchestva.isNotEmpty()){
                for (i in 0 until mentorList.size){
                    if (mentorList[i].mentor_name == ismi){
                        same = true
                        break
                    }
                }
                if (!same){
                    myDbHelper.insertMentor(mentor)
        findNavController().popBackStack()
                    Toast.makeText(binding.root.context, "Ma'lumot saqlandi", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(binding.root.context, "Bunday ismli mentor bor!!", Toast.LENGTH_SHORT).show()
                    same=false
                }

            } else{
                Toast.makeText(binding.root.context, "Malumot to'ldirilishi kerak", Toast.LENGTH_SHORT).show()
            }
        }


//        myDbHelper.insertMentor(mentor)
//        findNavController().popBackStack()


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentorAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}