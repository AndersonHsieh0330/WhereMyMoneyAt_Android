package com.AndersonHsieh.wmma_wheremymoneyat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AndersonHsieh.wmma_wheremymoneyat.data.TransactionRepository
import com.AndersonHsieh.wmma_wheremymoneyat.databinding.FragmentHomeBinding
import com.AndersonHsieh.wmma_wheremymoneyat.model.Transaction
import com.AndersonHsieh.wmma_wheremymoneyat.model.TransactionDAO
import com.AndersonHsieh.wmma_wheremymoneyat.util.MyViewModelFactory
import com.AndersonHsieh.wmma_wheremymoneyat.util.RecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private lateinit var dataSet:List<Transaction>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TransactionDAO is here for temperary use
        //TODO(Figure out where TransactionDAO should be initialized)
        val factory = MyViewModelFactory(TransactionRepository.getInstance(TransactionDAO()))
        val viewModel: HomeViewModel by lazy {
            //only initialized on first time use of the variable "viewModel"
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        }

        //update the actual data in view model
        viewModel.getTransactions();
        //register observer of HomeViewModel, which belongs to HomeFragment
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            //here the response would be List<Transaction>
            binding.textDashboard.text = it.toString();
            dataSet = it;

            //fortesting
            initUI();

        })


    }

    private fun initUI(){
        val layoutManager = LinearLayoutManager(this.context)
        val adapter = RecyclerAdapter(dataSet)
        val transactionRecyclerView = binding.homeTransactionRecyclerview
        transactionRecyclerView.adapter = adapter
        transactionRecyclerView.layoutManager = layoutManager

    }    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}