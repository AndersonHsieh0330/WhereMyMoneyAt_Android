package com.AndersonHsieh.wmma_wheremymoneyat.ui.main_activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.AndersonHsieh.wmma_wheremymoneyat.R
import com.AndersonHsieh.wmma_wheremymoneyat.data.TransactionRepository
import com.AndersonHsieh.wmma_wheremymoneyat.databinding.ActivityMainBinding
import com.AndersonHsieh.wmma_wheremymoneyat.ui.month_picker.YearMonthPickerDialog
import com.AndersonHsieh.wmma_wheremymoneyat.util.Constants
import com.AndersonHsieh.wmma_wheremymoneyat.util.MyViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: TransactionViewModel by lazy {
        //only initialized on first time use of the variable "viewModel"
        ViewModelProvider(
            this,
            MyViewModelFactory(TransactionRepository.getInstance(), application)
        )[TransactionViewModel::class.java]
    }

    private lateinit var navView:BottomNavigationView
    private lateinit var monthYearPickerBTN:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        navView.setupWithNavController(navController)


        viewModel.getTransactions()
        viewModel.getSelectedYearMonth()
        viewModel.getIsSelectedAll()

        viewModel.isSelectedAll.observe(this, Observer {
            if (it) {
                binding.MainActivityYearMonthPickerBTN.text = "All"
            } else {
                //this block is only triggered when user unchecks the "select all" checkbox
                //without modifying the year and month
                val monthYear = viewModel.selectedYearMonth.value!!
                val month: String =
                    if (monthYear[1] >= 10) (monthYear[1].toString()) else "0${monthYear[1]}"
                val yearMonthDisplayed = monthYear[0].toString() + "-" + month
                binding.MainActivityYearMonthPickerBTN.text = yearMonthDisplayed
            }
        })

        viewModel.selectedYearMonth.observe(this, Observer {
            if (!viewModel.isSelectedAll.value!!) {
                //only set display button text
                val month: String = if (it[1] >= 10) (it[1].toString()) else "0${it[1]}"
                val yearMonthDisplayed = it[0].toString() + "-" + month
                binding.MainActivityYearMonthPickerBTN.text = yearMonthDisplayed
            } else {
                binding.MainActivityYearMonthPickerBTN.text = "All"
            }
        })



    }

    fun initUI(){
        navView = binding.navView
        monthYearPickerBTN = binding.MainActivityYearMonthPickerBTN
        monthYearPickerBTN.setOnClickListener(View.OnClickListener {
            if(viewModel.isConnectedToInternet()){
                //TODO(waiting to debug)
                val dialog = YearMonthPickerDialog()
            dialog.show(supportFragmentManager, Constants.YEAR_MONTH_PICKER_LAUNCH_TAG)
            }else{
                //user is only allowed to view the collection of transactions cached in SQLite when offline
                Toast.makeText(applicationContext, R.string.offline, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        //once editActivity is closed, update the data by making a GET request
        viewModel.getTransactions()
        Log.d(Constants.LOGGING_TAG, "activity onrestart ")
    }





}