package com.example.pdp_dastur.guruh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdp_dastur.R
import com.example.pdp_dastur.databinding.FragmentProcResBinding
import com.example.pdp_dastur.databinding.FragmentProccessBinding
import com.example.pdp_dastur.db.MyDbHelper
import com.example.pdp_dastur.models.Guruh

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Proc_ResFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Proc_ResFragment : Fragment() {
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

    lateinit var binding: FragmentProcResBinding
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProcResBinding.inflate(layoutInflater,container, false)

        val guruuh = arguments?.getSerializable("pro") as Guruh
        myDbHelper = MyDbHelper(binding.root.context)

        binding.groupName.text = guruuh.gr_name
        binding.groupTime.text = guruuh.gr_time

        binding.boshla.setOnClickListener {
            val name = guruuh.gr_name
            val kurss = guruuh.gr_kurs_id
            val mentor = guruuh.gr_mentor_id
            val time = guruuh.gr_time
            val days = guruuh.gr_days
            val open = guruuh.gr_open
            val guruh = Guruh("dalban",kurss,mentor,time,days,"start")
            myDbHelper.updateGroup(guruh)

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
         * @return A new instance of fragment Proc_ResFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Proc_ResFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}