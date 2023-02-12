package com.ashu.savemytax.di

import com.ashu.savemytax.api.salary.SalaryApiHelper
import com.ashu.savemytax.api.salary.SalaryApiHelperImp
import com.ashu.savemytax.api.salary.SalaryApiService
import com.ashu.savemytax.api.user.UserAPiHelper
import com.ashu.savemytax.api.user.UserApiHelperImp
import com.ashu.savemytax.api.user.UserApiService
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
    fun provideUserApiService(retrofit: Retrofit) = retrofit.create(UserApiService::class.java)

    @Singleton
    @Provides
    fun provideUserApiHelper(userAPiHelper: UserApiHelperImp): UserAPiHelper = userAPiHelper

    @Singleton
    @Provides
    fun provideSalaryApiService(retrofit: Retrofit) = retrofit.create(SalaryApiService::class.java)

    @Singleton
    @Provides
    fun provideSalaryApiHelper(salaryApiHelper: SalaryApiHelperImp): SalaryApiHelper = salaryApiHelper

}