package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

class GetAllSleepsUseCase @Inject constructor(private val sleepRepository: SleepRepository) {


    // Retourne la liste de tous les sleeps
    suspend fun execute(): List<Sleep> {
        return sleepRepository.getAllSleeps() // La méthode retourne déjà List<Sleep>
    }

}
