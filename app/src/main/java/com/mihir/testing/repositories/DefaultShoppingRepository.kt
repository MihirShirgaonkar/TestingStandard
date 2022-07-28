package com.mihir.testing.repositories

import androidx.lifecycle.LiveData
import com.mihir.testing.local.ShoppingDao
import com.mihir.testing.local.ShoppingItem
import com.mihir.testing.other.Resource
import com.mihir.testing.remote.PixalbayAPI
import com.mihir.testing.remote.responses.PackageResponse
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val api: PixalbayAPI,
    private val dao: ShoppingDao)
    : ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<PackageResponse> {
        return try {

            val result = api.searchForImages(imageQuery)

            if (result.isSuccessful){

                result.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Null Body",null)

            }else{
                Resource.error("Failed response",null)
            }

        }catch (e : Exception){
            Resource.error("Exception",null)
        }
    }
}