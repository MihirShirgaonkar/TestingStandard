package com.mihir.testing.testrepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mihir.testing.local.ShoppingItem
import com.mihir.testing.other.Resource
import com.mihir.testing.remote.responses.PackageResponse
import com.mihir.testing.repositories.ShoppingRepository

class TestRepository : ShoppingRepository {

    private val shopingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shopingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    private fun setshouldReturnNetworkError(boolean: Boolean) {
        shouldReturnNetworkError = boolean
    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shopingItems)
        observableTotalPrice.postValue(getShoppingItems())

    }

    private fun getShoppingItems(): Float {
        return shopingItems.sumByDouble { it.price.toDouble() }.toFloat()

    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shopingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopingItems.remove(shoppingItem)
        refreshLiveData()

    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<PackageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Network Issue Request Failed", null)
        } else {
            Resource.success(PackageResponse("2", 1, 15, listOf(), 100))
        }
    }


}