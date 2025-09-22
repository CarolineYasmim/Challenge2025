package com.example.challenge2025.model.data

import com.example.challenge2025.R
import com.example.challenge2025.model.tests.AnswerOption
import com.example.challenge2025.model.tests.Question
import com.example.challenge2025.model.tests.TestCategory
import com.example.challenge2025.model.tests.TestItem
import com.example.challenge2025.model.tests.TestStatus
import com.example.challenge2025.model.user.User

object MockData {

    // Lista de testes (para os cards)
    val tests = mutableListOf(
        TestItem(
            "1",
            "Ansiedade",
            "Avalie seu nível de ansiedade",
            5,
            TestStatus.TODO,
            TestCategory.USER,
            R.drawable.ic_burnout
        ),
        TestItem(
            "2",
            "Burnout",
            "Descubra sinais de esgotamento",
            7,
            TestStatus.DONE,
            TestCategory.USER,
            R.drawable.ic_burnout
        ),
        TestItem(
            "3",
            "Depressão",
            "Entenda seu estado emocional",
            6,
            TestStatus.TODO,
            TestCategory.USER,
            R.drawable.ic_depression
        ),
        TestItem(
            "4",
            "Equilíbrio de vida",
            "Pesquisa feita pela empresa",
            8,
            TestStatus.TODO,
            TestCategory.COMPANY,
            R.drawable.ic_company_test
        ),
        TestItem(
            "5",
            "Sono e descanso",
            "Pesquisa feita pela empresa",
            6,
            TestStatus.TODO,
            TestCategory.COMPANY,
            R.drawable.ic_company_test
        ),
        TestItem(
            "6",
            "Estresse no trabalho",
            "Pesquisa feita pela empresa",
            9,
            TestStatus.TODO,
            TestCategory.COMPANY,
            R.drawable.ic_company_test
        )
    )

    fun getTestById(id: String): TestItem? = tests.find { it.id == id }

    // Lista de perguntas (ligadas por testId)
    val questions = listOf(
        // Ansiedade (id = "1") → 5 perguntas
        Question(
            "q1", "1", "Você se sente ansioso(a) frequentemente?",
            options = listOf(
                AnswerOption("o1", "Frequentemente", 3),
                AnswerOption("o2", "Às vezes", 2),
                AnswerOption("o3", "Raramente", 1),
                AnswerOption("o4", "Nunca", 0)
            )
        ),
        Question(
            "q2", "1", "Tem dificuldade de concentração?",
            options = listOf(
                AnswerOption("o5", "Frequentemente", 3),
                AnswerOption("o6", "Às vezes", 2),
                AnswerOption("o7", "Raramente", 1),
                AnswerOption("o8", "Nunca", 0)
            )
        ),
        Question(
            "q3", "1", "Sente-se inquieto(a) ou agitado(a)?",
            options = listOf(
                AnswerOption("o9", "Frequentemente", 3),
                AnswerOption("o10", "Às vezes", 2),
                AnswerOption("o11", "Raramente", 1),
                AnswerOption("o12", "Nunca", 0)
            )
        ),
        Question(
            "q4", "1", "Tem problemas para dormir devido à ansiedade?",
            options = listOf(
                AnswerOption("o13", "Frequentemente", 3),
                AnswerOption("o14", "Às vezes", 2),
                AnswerOption("o15", "Raramente", 1),
                AnswerOption("o16", "Nunca", 0)
            )
        ),
        Question(
            "q5", "1", "Sente tensão muscular ou cansaço constante?",
            options = listOf(
                AnswerOption("o17", "Frequentemente", 3),
                AnswerOption("o18", "Às vezes", 2),
                AnswerOption("o19", "Raramente", 1),
                AnswerOption("o20", "Nunca", 0)
            )
        ),

        // Burnout (id = "2") → 3 perguntas
        Question(
            "q6", "2", "Sente-se esgotado(a) no trabalho?",
            options = listOf(
                AnswerOption("o21", "Frequentemente", 3),
                AnswerOption("o22", "Às vezes", 2),
                AnswerOption("o23", "Raramente", 1),
                AnswerOption("o24", "Nunca", 0)
            )
        ),
        Question(
            "q7", "2", "Tem dificuldade de manter a motivação?",
            options = listOf(
                AnswerOption(
                    "o25",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o26",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o27",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o28",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q8", "2", "Sente que não consegue lidar com a pressão?",
            options = listOf(
                AnswerOption(
                    "o29",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o30",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o31",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o32",
                    "Nunca",
                    0
                )
            )
        ),

        // Depressão (id = "3") → 8 perguntas
        Question(
            "q9", "3", "Tem se sentido triste ou desanimado(a)?",
            options = listOf(
                AnswerOption(
                    "o33",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o34",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o35",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o36",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q10", "3", "Perdeu interesse em atividades que gostava?",
            options = listOf(
                AnswerOption(
                    "o37",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o38",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o39",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o40",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q11", "3", "Tem dificuldades para se concentrar?",
            options = listOf(
                AnswerOption(
                    "o41",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o42",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o43",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o44",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q12", "3", "Sente-se cansado(a) ou sem energia?",
            options = listOf(
                AnswerOption(
                    "o45",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o46",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o47",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o48",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q13", "3", "Tem pensamentos negativos frequentes?",
            options = listOf(
                AnswerOption(
                    "o49",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o50",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o51",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o52",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q14", "3", "Tem problemas para dormir ou dormir demais?",
            options = listOf(
                AnswerOption(
                    "o53",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o54",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o55",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o56",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q15", "3", "Sente-se culpado(a) ou inútil?",
            options = listOf(
                AnswerOption(
                    "o57",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o58",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o59",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o60",
                    "Nunca",
                    0
                )
            )
        ),
        Question(
            "q16", "3", "Tem dificuldades em tomar decisões?",
            options = listOf(
                AnswerOption(
                    "o61",
                    "Frequentemente",
                    3
                ),
                AnswerOption(
                    "o62",
                    "Às vezes",
                    2
                ),
                AnswerOption(
                    "o63",
                    "Raramente",
                    1
                ),
                AnswerOption(
                    "o64",
                    "Nunca",
                    0
                )
            )
        )
    )


    fun getQuestionsForTest(testId: String): List<Question> =
        questions.filter { it.testId == testId }

    // Descrições detalhadas para TestDetail
    val detailedDescriptions = mapOf(
        "1" to "Este teste de ansiedade avalia como você lida com situações de estresse e tensão, e ajuda a identificar padrões de preocupação.",
        "2" to "O teste de burnout identifica sinais de esgotamento físico e emocional relacionados ao trabalho e à rotina diária.",
        "3" to "O teste de depressão analisa sintomas como tristeza persistente, perda de interesse e alterações no sono e apetite.",
        "4" to "Pesquisa sobre equilíbrio de vida feita pela empresa para identificar áreas de melhoria no bem-estar do colaborador.",
        "5" to "Avaliação do sono e descanso para entender padrões e hábitos que impactam na saúde e produtividade.",
        "6" to "Teste de estresse no trabalho, analisando fatores que podem gerar sobrecarga ou desgaste físico e mental."
    )

    val currentUser = User(
        id = "u1",
        name = "Yasmim",
        avatarRes = R.drawable.avatar
    )

    fun updateTestStatus(testId: String, status: TestStatus) {
        val index = tests.indexOfFirst { it.id == testId }
        if (index != -1) {
            val test = tests[index]
            tests[index] = test.copy(status = status)
        }
    }




}