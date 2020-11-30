package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data
import android.util.Log
import ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.remote.CarApi
import ro.ubbcluj.cs.ilazar.mycarapplication196.core.Result
import ro.ubbcluj.cs.ilazar.mycarapplication196.core.TAG

object CarRepository {
    private var cachedItems: MutableList<Car>? = null;

    suspend fun loadAll(): List<Car> {
        Log.i(TAG, "loadAll")
        if (cachedItems != null) {
            return cachedItems as List<Car>;
        }
        cachedItems = mutableListOf()
        val items = CarApi.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Car>
    }

    suspend fun load(itemId: String): Car {
        Log.i(TAG, "load")
        val item = cachedItems?.find { it._id == itemId }
        if (item != null) {
            return item
        }
        return CarApi.service.read(itemId)
    }

    suspend fun save(item: Car): Car{
        Log.i(TAG, "save")
        val createdItem = CarApi.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Car): Car {
        Log.i(TAG, "update")
        val updatedItem = CarApi.service.update(item._id, item)
        val index = cachedItems?.indexOfFirst { it._id == item._id }
        if (index != null) {
            cachedItems?.set(index, updatedItem)
        }
        return updatedItem
    }
}