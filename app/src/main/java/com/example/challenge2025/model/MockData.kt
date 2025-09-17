package com.example.challenge2025.model

object MockData {

    // Lista de testes (para os cards)
    val tests = listOf(
        TestItem("1","Ansiedade","Avalie seu nível de ansiedade",5, TestStatus.TODO, TestCategory.USER, com.example.challenge2025.R.drawable.ic_anxiety),
        TestItem("2","Burnout","Descubra sinais de esgotamento",7, TestStatus.DONE, TestCategory.USER, com.example.challenge2025.R.drawable.ic_burnout),
        TestItem("3","Depressão","Entenda seu estado emocional",6, TestStatus.TODO, TestCategory.USER, com.example.challenge2025.R.drawable.ic_depression),
        TestItem("4","Equilíbrio de vida","Pesquisa feita pela empresa",8, TestStatus.TODO, TestCategory.COMPANY, com.example.challenge2025.R.drawable.ic_company_test),
        TestItem("5","Sono e descanso","Pesquisa feita pela empresa",6, TestStatus.TODO, TestCategory.COMPANY, com.example.challenge2025.R.drawable.ic_company_test),
        TestItem("6","Estresse no trabalho","Pesquisa feita pela empresa",9, TestStatus.TODO, TestCategory.COMPANY, com.example.challenge2025.R.drawable.ic_company_test)
    )

    fun getTestById(id: String): TestItem? = tests.find { it.id == id }

    // Lista de perguntas (ligadas por testId)
    val questions = listOf(
        // Ansiedade (id = "1") → 5 perguntas
        Question("q1", "1", "Você se sente ansioso(a) frequentemente?"),
        Question("q2", "1", "Tem dificuldade de concentração?"),
        Question("q3", "1", "Sente-se inquieto(a) ou agitado(a)?"),
        Question("q4", "1", "Tem problemas para dormir devido à ansiedade?"),
        Question("q5", "1", "Sente tensão muscular ou cansaço constante?"),

        // Burnout (id = "2") → 3 perguntas
        Question("q6", "2", "Sente-se esgotado(a) no trabalho?"),
        Question("q7", "2", "Tem dificuldade de manter a motivação?"),
        Question("q8", "2", "Sente que não consegue lidar com a pressão?"),

        // Depressão (id = "3") → 8 perguntas
        Question("q9", "3", "Tem se sentido triste ou desanimado(a)?"),
        Question("q10", "3", "Perdeu interesse em atividades que gostava?"),
        Question("q11", "3", "Tem dificuldades para se concentrar?"),
        Question("q12", "3", "Sente-se cansado(a) ou sem energia?"),
        Question("q13", "3", "Tem pensamentos negativos frequentes?"),
        Question("q14", "3", "Tem problemas para dormir ou dormir demais?"),
        Question("q15", "3", "Sente-se culpado(a) ou inútil?"),
        Question("q16", "3", "Tem dificuldades em tomar decisões?")
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
}
