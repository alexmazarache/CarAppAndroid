package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.items

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubbcluj.cs.ilazar.mycarapplication196.core.TAG
import ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.Car
import ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.CarRepository

class CarListViewModel : ViewModel() {
    private val mutableItems = MutableLiveData<List<Car>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Car>> = mutableItems
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createItem(position: Int): Unit {
        val list = mutableListOf<Car>()
        list.addAll(mutableItems.value!!)
        list.add(
            Car(
                position.toString(),
                "Car " + position,
                "",
                1,
                false
            )
        )
        mutableItems.value = list
    }

    fun loadItems() {
        viewModelScope.launch {
            Log.v(TAG, "loadItems...");
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableItems.value = CarRepository.loadAll()
                Log.d(TAG, "loadItems succeeded");
                mutableLoading.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItems failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}
