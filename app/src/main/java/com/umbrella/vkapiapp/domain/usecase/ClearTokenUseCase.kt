package com.umbrella.vkapiapp.domain.usecase

import com.umbrella.vkapiapp.domain.repository.AuthRepository

class ClearTokenUseCase(private val repository: AuthRepository) {

    operator fun invoke() {
        repository.clearToken()
    }
}