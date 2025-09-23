package com.example.challenge2025.model.checkin

import com.example.challenge2025.R
import com.example.challenge2025.model.user.CheckinStatus
import com.example.challenge2025.model.user.Feeling
import com.example.challenge2025.model.user.UserCheckin
import java.time.LocalDate

object Checkin {

    // Lista de sentimentos disponíveis com os novos ícones e cores
    val availableFeelings = listOf(
        Feeling("f1", "Animado", "#FFD93D", R.drawable.animado),
        Feeling("f2", "Ansioso", "#F49E4C", R.drawable.ansioso),
        Feeling("f3", "Cansado", "#6C757D", R.drawable.cansado),
        Feeling("f4", "Estressado", "#FF6B6B", R.drawable.estressado),
        Feeling("f5", "Medo", "#A475FA", R.drawable.medo),
        Feeling("f6", "Motivado", "#81F495", R.drawable.motivado),
        Feeling("f7", "Neutro", "#BDBDBD", R.drawable.neutro),
        Feeling("f8", "Preocupado", "#FFC107", R.drawable.preocupado),
        Feeling("f9", "Satisfeito", "#7584FA", R.drawable.satisfeito),
        Feeling("f10", "Triste", "#546E7A", R.drawable.triste)
    )

    // Check-ins mockados
    private val userCheckins = mutableListOf(
        UserCheckin(
            id = "c1",
            userId = "u1",
            date = LocalDate.now(),
            feelings = listOf(availableFeelings[5], availableFeelings[8]),
            notes = "Dia produtivo no trabalho, consegui finalizar minhas tarefas."
        ),
        UserCheckin(
            id = "c2",
            userId = "u1",
            date = LocalDate.now().minusDays(1),
            feelings = listOf(availableFeelings[1], availableFeelings[2]),
            notes = "Reunião importante e um pouco desgastante."
        ),
        UserCheckin(
            id = "c3",
            userId = "u1",
            date = LocalDate.now().minusDays(3),
            feelings = listOf(availableFeelings[0]),
            notes = "Final de semana relaxante com a família."
        )
    )

    fun getCheckinForDate(date: LocalDate): UserCheckin? {
        return userCheckins.find { it.date == date && it.userId == "u1" }
    }

    fun getCheckinStatus(date: LocalDate): CheckinStatus {
        return if (getCheckinForDate(date) != null) CheckinStatus.COMPLETED
        else CheckinStatus.NOT_DONE
    }

    fun addCheckin(checkin: UserCheckin) {
        userCheckins.removeAll {
            it.date == checkin.date && it.userId == checkin.userId
        }
        userCheckins.add(checkin)
    }

    fun submitCheckin(date: LocalDate, selectedFeelings: List<Feeling>, notes: String? = null) {
        val newCheckin = UserCheckin(
            id = "c${userCheckins.size + 1}",
            userId = "u1",
            date = date,
            feelings = selectedFeelings,
            notes = notes
        )
        addCheckin(newCheckin)
    }
}