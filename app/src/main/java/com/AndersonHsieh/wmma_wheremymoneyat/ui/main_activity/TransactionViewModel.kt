package com.AndersonHsieh.wmma_wheremymoneyat.ui.main_activity

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.AndersonHsieh.wmma_wheremymoneyat.data.TransactionRepository
import com.AndersonHsieh.wmma_wheremymoneyat.model.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {
    //this view model is shared among home fragment and main activity
    //Custom ViewModel factory is created in order to pass arguments into view model

    //if any changes are made to the value of this MutableLiveData object
    //the observers will know
    val transactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    val selectedYearMonth: MutableLiveData<Array<Int>> = MutableLiveData()

    val isSelectedAll: MutableLiveData<Boolean> = MutableLiveData()

    var test: String? = null

    fun getTransactions() {
        repository.getTransaction().enqueue(object : Callback<List<Transaction>> {
            override fun onResponse(
                call: retrofit2.Call<List<Transaction>>,
                response: Response<List<Transaction>>
            ) {
                transactions.value = response.body()
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                transactions.value = listOf(Transaction(-1, "failed", -1.0, "RIGHTNOW"))
            }

        })
    }


    fun getSelectedYearMonth() {
        selectedYearMonth.value = repository.getYearMonthFromSharedPreference()
    }

    fun getIsSelectedAll() {
        isSelectedAll.value = repository.getIsSelectedAllFromSharedPreference()
    }

    fun initSharedPreference(context: Context, sharedPreferenceTag: String) {
        repository.initSharedPreference(context, sharedPreferenceTag)

    }

    fun changeSelectedTimeInSharedPreference(year: Int, month: Int, isAll: Boolean) {
        repository.changeSelectedTimeInSharedPreference(year, month, isAll);
    }

}