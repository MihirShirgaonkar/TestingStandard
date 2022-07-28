package com.mihir.testing.repositories

import androidx.lifecycle.LiveData
import com.mihir.testing.local.ShoppingItem
import com.mihir.testing.other.Resource
import com.mihir.testing.remote.responses.PackageResponse
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<PackageResponse>
}