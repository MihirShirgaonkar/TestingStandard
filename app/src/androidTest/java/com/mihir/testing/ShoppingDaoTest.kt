package com.mihir.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mihir.testing.local.ShoppingDao
import com.mihir.testing.local.ShoppingItem
import com.mihir.testing.local.ShoppingItemDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private  lateinit var shoppingDao: ShoppingDao
    private  lateinit var shoppingItemDatabase: ShoppingItemDatabase
    @Before
    fun setup (){
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
        ShoppingItemDatabase::class.java).allowMainThreadQueries().build()
        shoppingDao = shoppingItemDatabase.shoppingDao()
    }

    @After
    fun traceback(){ shoppingItemDatabase.close()}


    @Test
    fun insertShoppingItem(){
        runBlocking {
            val shoppingItem = ShoppingItem("name",1,2f,"asd",1)
            shoppingDao.insertShoppingItem(shoppingItem)

            val allItems = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

            assertThat(allItems).contains(shoppingItem)
        }
    }

    @Test
    fun deleteShoppingItem(){
        runBlocking {
            val shoppingItem = ShoppingItem("name",1,2f,"asd",1)
            shoppingDao.insertShoppingItem(shoppingItem)

            shoppingDao.deleteShoppingItem(shoppingItem)

            val allItems = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
            assertThat(allItems).doesNotContain(shoppingItem)

        }
    }

    @Test
    fun observeTotalPriceSum() = runBlocking {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)
        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = shoppingDao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f + 0 * 100F)
    }

}