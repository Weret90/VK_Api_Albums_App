package com.umbrella.vkapiapp.domain.usecase

import com.umbrella.vkapiapp.domain.repository.AuthRepository

class GetTokenUseCase(private val repository: AuthRepository) {

    operator fun invoke(): String? {
        return repository.getToken()
    }
}