package com.ashu.ocotopus.di

import com.ashu.ocotopus.api.dish.DishApiHelper
import com.ashu.ocotopus.api.dish.DishApiHelperImp
import com.ashu.ocotopus.api.dish.DishApiService
import com.ashu.ocotopus.api.user.UserAPiHelper
import com.ashu.ocotopus.api.user.UserApiHelperImp
import com.ashu.ocotopus.api.user.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideDishApiService(retrofit: Retrofit) = retrofit.create(DishApiService::class.java)

    @Singleton
    @Provides
    fun provideDishApiHelper(dishApiHelper: DishApiHelperImp): DishApiHelper = dishApiHelper

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit) = retrofit.create(UserApiService::class.java)

    @Singleton
    @Provides
    fun provideUserApiHelper(userAPiHelper: UserApiHelperImp): UserAPiHelper = userAPiHelper
}