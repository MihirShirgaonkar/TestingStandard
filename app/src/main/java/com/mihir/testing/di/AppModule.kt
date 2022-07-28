package com.mihir.testing.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mihir.testing.local.ShoppingItemDatabase
import com.mihir.testing.remote.PixalbayAPI
import com.mihir.testing.other.Constants
import com.mihir.testing.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //local
    @Singleton
    @Provides
    fun getShoppingDatabase(@ApplicationContext context: Context) : RoomDatabase{
        return Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATABASE_NAME).
                build()
    }

    @Singleton
    @Provides
    fun getShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()


    //remote
    @Singleton
    @Provides
    fun getRetrofit() : PixalbayAPI{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(PixalbayAPI::class.java)
    }


}