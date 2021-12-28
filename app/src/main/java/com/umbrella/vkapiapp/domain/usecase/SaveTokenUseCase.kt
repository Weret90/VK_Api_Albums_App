package com.umbrella.vkapiapp.domain.usecase

import com.umbrella.vkapiapp.domain.repository.AuthRepository

class SaveTokenUseCase(private val repository: AuthRepository) {

    operator fun invoke(token: String) {
        repository.saveToken(token)
    }
}