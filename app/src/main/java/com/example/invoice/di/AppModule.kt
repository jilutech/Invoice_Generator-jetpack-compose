package com.example.invoice.di

import com.example.invoice.data.auth.AuthRepository
import com.example.invoice.data.auth.AuthRepositoryImpl
import com.example.invoice.data.home.repo.CustomerRepositoryImpl
import com.example.invoice.data.home.repo.MyBusinessRepository
import com.example.invoice.data.home.repo.MyBusinessRepositoryImpl
import com.example.invoice.data.home.repo.TaxRepository
import com.example.invoice.data.home.repo.TaxRepositoryImpl
import com.example.invoice.data.home.repo.models.CustomerRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDb(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideCustomerRepo(impl: CustomerRepositoryImpl) : CustomerRepository =impl

    @Provides
    fun provideBusinessRepo(impl : MyBusinessRepositoryImpl) : MyBusinessRepository = impl

    @Provides
    fun provideTaxRepo(impl: TaxRepositoryImpl) : TaxRepository =impl

}