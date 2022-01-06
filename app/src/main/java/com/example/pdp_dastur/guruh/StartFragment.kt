package com.example.pdp_dastur.guruh

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.Group_Rv
import com.example.pdp_dastur.adapters.Mentor_Spinner
import com.example.pdp_dastur.adapters.TimeSpinner
import com.example.pdp_dastur.databinding.FragmentStartBinding
import com.example.pdp_dastur.databinding.MyEditDialogBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Guruh
import com.example.pdp_dastur.models.Kurs
import com.example.pdp_dastur.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Kurs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
        }
    }
    lateinit var binding: FragmentStartBinding
    lateinit var groupRv: Group_Rv
    lateinit var groupList:ArrayList<Guruh>
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Guruh>
    lateinit var mentorSpinner: Mentor_Spinner
    lateinit var list1: ArrayList<Mentor>
    lateinit var mentorList: List<Mentor>
    lateinit var timeSpinner: TimeSpinner
    lateinit var t_List: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        myDbHelper = MyDbHelper(binding.root.context)

        //mentor spinner
        mentorList = myDbHelper.getAllMentors()
        list1 = ArrayList()

        if (mentorList.isNotEmpty()){
            for (mentor in mentorList){
                if (mentor.mentor_kurs_id?.kurs_name == param1?.kurs_name) {
                    list1.add(mentor)
                }
            }
        }
        mentorSpinner = Mentor_Spinner(list1)

        //guruh rv
        groupList=myDbHelper.getAllGroup()
        list = ArrayList()

        if (param1 != null){
            for (guruh in groupList) {
                if (guruh.gr_kurs_id?.kurs_name == param1?.kurs_name && guruh.gr_open == "gone"){
                    list.add(guruh)
                }
            }
        }

        //Time spinner bilan ishlash
        t_List = ArrayList()

        t_List.add("16:30 - 18:30")
        t_List.add("19:00 - 21:00")
        timeSpinner = TimeSpinner(t_List)

        groupRv = Group_Rv(list, object : Group_Rv.OnItemClickListener{
            override fun onItemStartClick(guruh: Guruh, position: Int, button: Button) {
                var bundle = Bundle()
                bundle.putString("start", "start")
                bundle.putSerializable("pro",guruh)
                findNavController().navigate(R.id.proc_ResFragment,bundle)
            }

            override fun onItemEditClick(guruh: Guruh, position: Int, button: Button) {
                val alertDialog = AlertDialog.Builder(binding.root.context)
                val dialog = alertDialog.create()
                val dialogView = MyEditDialogBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    null,
                    false)
                dialogView.vaqt.setSelection(position)
                dialogView.mentor.adapter = mentorSpinner
                dialogView.vaqt.adapter = timeSpinner

                //settext
                dialogView.guruh.setText(guruh.gr_name)



                var indexMentor = -1
                for (i in 0 until list1.size){
                    if (list1[i].mentor_name == guruh.gr_mentor_id?.mentor_name){
                        indexMentor = i
                        break
                    }
                }
                dialogView.mentor.setSelection(indexMentor)



                var indexTime = -1
                for (i in 0 until t_List.size){
                    if (t_List[i] == guruh.gr_time) {
                        indexTime = i
                        break
                    }
                }
                dialogView.vaqt.setSelection(indexTime)

                //update
                dialogView.saveText.setOnClickListener {

                    guruh.gr_name = dialogView.guruh.text.toString()
                    guruh.gr_mentor_id =list1[dialogView.mentor.selectedItemPosition]
                    guruh.gr_time = dialogView.vaqt.selectedItem.toString()
                    myDbHelper.updateGroup(guruh)
                    list[position] = guruh
                    groupRv.notifyDataSetChanged()





                    dialog.dismiss()
                }


                dialog.setView(dialogView.root)
                dialog.show()
            }

            override fun onItemDeleteClick(guruh: Guruh, position: Int, button: Button) {
                myDbHelper.deleteGroup(guruh)
                list.remove(guruh)
                groupRv.notifyItemRemoved(position)
                groupRv.notifyItemRangeChanged(position, list.size)
            }

        })

        binding.startRv.adapter = groupRv
        groupRv.notifyItemInserted(list.size)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Kurs) =
            StartFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}