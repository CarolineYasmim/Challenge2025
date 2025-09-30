package com.example.challenge2025.di

import android.content.Context
import com.example.challenge2025.data.local.AuthPreferences
import com.example.challenge2025.data.remote.ApiService
import com.example.challenge2025.data.remote.AuthInterceptor
import com.example.challenge2025.domain.repository.AuthRepository
import com.example.challenge2025.data.repository.AuthRepositoryImpl
import com.example.challenge2025.domain.repository.CheckinRepository
import com.example.challenge2025.data.repository.CheckinRepositoryImpl
import com.example.challenge2025.data.repository.TestRepositoryImpl
import com.example.challenge2025.domain.repository.UserRepository
import com.example.challenge2025.data.repository.UserRepositoryImpl
import com.example.challenge2025.domain.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // Usamos @Binds para dizer ao Hilt qual implementação usar para uma interface.
    // É mais eficiente que o @Provides para este caso.
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository


    @Binds
    @Singleton
    abstract fun bindCheckinRepository(impl: CheckinRepositoryImpl): CheckinRepository

    @Binds
    @Singleton
    abstract fun bindTestRepository(impl: TestRepositoryImpl): TestRepository


    // Usamos um companion object para as funções @Provides, que constroem objetos
    companion object {

        @Provides
        @Singleton
        fun provideAuthPreferences(@ApplicationContext context: Context): AuthPreferences {
            return AuthPreferences(context)
        }

        @Provides
        @Singleton
        fun provideAuthInterceptor(prefs: AuthPreferences): AuthInterceptor {
            return AuthInterceptor(prefs)
        }





        @Provides
        @Singleton
        fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                // COLOQUE A URL BASE DA SUA API AQUI
                .baseUrl("https://sori-production.up.railway.app")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }


}