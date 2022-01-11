package com.example.pdp_dastur.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pdp_dastur.R
import com.example.pdp_dastur.adapters.KursRvAdapter
import com.example.pdp_dastur.databinding.FragmentKursBinding
import com.example.pdp_dastur.databinding.MyDialogBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Kurs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursFragment : Fragment() {
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

    lateinit var binding: FragmentKursBinding
    lateinit var kursAdapter: KursRvAdapter
    lateinit var kurslist:ArrayList<Kurs>
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKursBinding.inflate(layoutInflater,container,false)

        myDbHelper = MyDbHelper(binding.root.context)

        val kurs = arguments?.getString("kurs")



        kurslist=myDbHelper.getAllCourse()



        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        if (kurs!=null){
            binding.toolbar.inflateMenu(R.menu.add_menu)
            kursAdapter = KursRvAdapter(kurslist,object :KursRvAdapter.OnClickListener{
                override fun OnItemClick(kurs: Kurs, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("kursi",kurs)
                    findNavController().navigate(R.id.kursChildFragment,bundle)
                }

            })


            binding.rvKurs.adapter = kursAdapter
            binding.toolbar.setOnMenuItemClickListener {
                if (it.itemId==R.id.add){
                    val alertDialog = AlertDialog.Builder(binding.root.context)
                    val dialog = alertDialog.create()
                    val dialogView =
                        MyDialogBinding.inflate(LayoutInflater.from(binding.root.context),null,false)


                    var same = false
                    dialogView.saveText.setOnClickListener {
                        val name = dialogView.sarlavha.text.toString().trim()
                        val description = dialogView.matn.text.toString().trim()
                        val kurs = Kurs(name,description)





                        if (name.isNotEmpty() && description.isNotEmpty()){
                            for (i in 0 until kurslist.size){
                                if (kurslist[i].kurs_name == name){
                                    same = true
                                    break
                                }
                            }
                            if (!same) {
                                myDbHelper.insertCourse(kurs)
                                kurslist.add(kurs)
                                kursAdapter.notifyItemInserted(kurslist.size)
                                dialog.dismiss()
                            }else{
                                Toast.makeText(binding.root.context, "Bunday nomli kurs bor!!", Toast.LENGTH_SHORT).show()
                                same=false
                            }


                        }else{
                            Toast.makeText(binding.root.context,"Ma'lumotlarni to'liq kiritmadingizku brat",Toast.LENGTH_SHORT).show()
                        }

                    }

                    dialogView.notText.setOnClickListener {
                        dialog.dismiss()
                    }


                    dialog.setView(dialogView.root)
                    dialog.show()
                }
                true
            }




        }
        val gruppa = arguments?.getString("guruh")
        if (gruppa!=null) {
            kursAdapter = KursRvAdapter(kurslist,object :KursRvAdapter.OnClickListener{
                override fun OnItemClick(kurs: Kurs, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("gruh",kurs)
                    findNavController().navigate(R.id.viewPagerFragment,bundle)
                }

            })


            binding.rvKurs.adapter = kursAdapter
            kursAdapter.notifyItemInserted(kurslist.size)
        }



        val mentor = arguments?.getString("mentor")
        if (mentor!=null){
            kursAdapter = KursRvAdapter(kurslist,object :KursRvAdapter.OnClickListener{
                override fun OnItemClick(kurs: Kurs, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("kurs",kurs)
                    bundle.putInt("position",position)
                    findNavController().navigate(R.id.mentorFragment,bundle)
                }

            })


            binding.rvKurs.adapter = kursAdapter
            kursAdapter.notifyItemInserted(kurslist.size)
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
         * @return A new instance of fragment KursFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}