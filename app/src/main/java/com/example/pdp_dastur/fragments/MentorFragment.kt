package com.example.pdp_dastur.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.KursRvAdapter
import com.example.pdp_dastur.adapters.mentorAdapter
import com.example.pdp_dastur.databinding.FragmentKursBinding
import com.example.pdp_dastur.databinding.FragmentMentorBinding
import com.example.pdp_dastur.databinding.MentorDialogBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorFragment : Fragment() {
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



    lateinit var binding:FragmentMentorBinding
    lateinit var mentorsAdapter: mentorAdapter
    lateinit var mentorList:ArrayList<Mentor>
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Mentor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorBinding.inflate(layoutInflater,container, false)
        myDbHelper = MyDbHelper(binding.root.context)


        binding.toolbarMentor.inflateMenu(R.menu.add_menu)

        val kurslar = arguments?.getSerializable("kurs") as Kurs
        val position = arguments?.getInt("position")

        binding.toolbarMentor.title = kurslar.kurs_name

        mentorList=myDbHelper.getAllMentors()
        list = ArrayList()

        if (mentorList.isNotEmpty()){
            for (mentor in mentorList) {
                if (mentor.mentor_kurs_id?.kurs_name == kurslar.kurs_name) {
                    list.add(mentor)
                }
            }
        }

        mentorsAdapter = mentorAdapter(list, object : mentorAdapter.OnItemClickListener{
            override fun onEditClick(mentor: Mentor, position: Int, button: Button) {
                val alertDialog = AlertDialog.Builder(binding.root.context)
                val dialog = alertDialog.create()
                val dialogView = MentorDialogBinding.inflate(
                    LayoutInflater.from(binding.root.context),null,false
                )

                dialogView.familya.setText(mentor.mentor_surname)
                dialogView.imya.setText(mentor.mentor_name)
                dialogView.otchest.setText(mentor.mentor_otch)

                dialogView.change.setOnClickListener {
                    mentor.mentor_surname = dialogView.familya.text.toString()
                    mentor.mentor_name = dialogView.imya.text.toString()
                    mentor.mentor_otch = dialogView.otchest.text.toString()
                    myDbHelper.updateMentor(mentor)
                    list[position] = mentor
                    mentorsAdapter.notifyItemChanged(position)

                }

                dialog.setView(dialogView.root)
                dialog.show()
            }

            override fun onDeleteClick(mentor: Mentor, position: Int, button: Button) {
                myDbHelper.deleteMentor(mentor)
                list.remove(mentor)
                mentorsAdapter.notifyItemRemoved(list.size)
                mentorsAdapter.notifyItemRangeChanged(position, list.size)


            }

        })
        binding.rvMentor.adapter=mentorsAdapter
        mentorsAdapter.notifyItemInserted(list.size)
        mentorsAdapter.notifyItemChanged(list.size)

//        mentorList=myDbHelper.getAllMentors()
//        mentorsAdapter = mentorAdapter(mentorList)
//        binding.rvMentor.adapter = mentorsAdapter
//        mentorsAdapter.notifyItemInserted(mentorList.size)


        binding.toolbarMentor.setOnMenuItemClickListener {

            if (it.itemId == R.id.add){
                var bundle = Bundle()
                bundle.putSerializable("kalit",kurslar)
                bundle.putInt("pozit",position!!)
                findNavController().navigate(R.id.mentorAddFragment,bundle)
            }

        true
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
         * @return A new instance of fragment MentorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}