package com.umbrella.vkapiapp.di

import com.umbrella.vkapiapp.data.local.LocalStorage
import com.umbrella.vkapiapp.data.local.LocalStorageImpl
import com.umbrella.vkapiapp.data.remote.RetroService
import com.umbrella.vkapiapp.data.remote.TokenInterceptor
import com.umbrella.vkapiapp.data.repository.AuthRepositoryImpl
import com.umbrella.vkapiapp.data.repository.PhotosRepositoryImpl
import com.umbrella.vkapiapp.domain.repository.AuthRepository
import com.umbrella.vkapiapp.domain.repository.PhotosRepository
import com.umbrella.vkapiapp.domain.usecase.*
import com.umbrella.vkapiapp.presentation.fragments.AlbumsFragment
import com.umbrella.vkapiapp.presentation.fragments.PhotosFragment
import com.umbrella.vkapiapp.presentation.viewmodels.AlbumsViewModel
import com.umbrella.vkapiapp.presentation.viewmodels.AuthorizationViewModel
import com.umbrella.vkapiapp.presentation.viewmodels.PhotosViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.vk.com/method/"

val viewModelsModule = module {
    viewModel {
        AuthorizationViewModel(
            saveTokenUseCase = get(), getTokenUseCase = get(), clearTokenUseCase = get()
        )
    }

    scope<AlbumsFragment> {
        scoped {
            GetAlbumsUseCase(repository = get())
        }
        viewModel {
            AlbumsViewModel(getAlbumsUseCase = get(), clearTokenUseCase = get())
        }
    }

    scope<PhotosFragment> {
        scoped {
            GetPhotosByAlbumIdUseCase(repository = get())
        }
        viewModel {
            PhotosViewModel(getPhotosByAlbumIdUseCase = get())
        }
    }
}

val retrofitModule = module {

    single {
        OkHttpClient().newBuilder()
            .addInterceptor(TokenInterceptor(getTokenUseCase = get()))
            .build()
    }

    single<RetroService> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(RetroService::class.java)
    }
}

val useCasesModule = module {
    factory {
        GetTokenUseCase(repository = get())
    }
    factory {
        SaveTokenUseCase(repository = get())
    }
    factory {
        ClearTokenUseCase(repository = get())
    }
}

val repositoriesModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(localStorage = get())
    }

    single<PhotosRepository> {
        PhotosRepositoryImpl(api = get())
    }

    single<LocalStorage> {
        LocalStorageImpl(context = get())
    }
}