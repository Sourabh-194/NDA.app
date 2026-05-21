package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.api.askGemini
import com.example.data.StudyLog
import com.example.data.StudyLogDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(private val studyLogDao: StudyLogDao) : ViewModel() {
    
    val allLogs: StateFlow<List<StudyLog>> = studyLogDao.getAllLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiResponse = MutableStateFlow<String?>(null)
    val isGenerating = MutableStateFlow(false)

    fun sendChatMessage(message: String) {
        viewModelScope.launch {
            isGenerating.value = true
            val recentLogs = studyLogDao.getRecentLogsSync()
            val logsText = recentLogs.joinToString("\n") { 
                "Topic: ${it.topic}, Duration: ${it.durationMinutes}m, Score: ${it.score ?: "N/A"}" 
            }
            
            val systemPrompt = """
                You are the 'Unified AI Brain' for an NDA (National Defence Academy) Preparation Ecosystem. 
                You are a strict, precise, and supportive military tutor. 
                Keep your responses short, tactical, and formatted clearly.
                Here are the user's recent study logs for context:
                ${if (logsText.isEmpty()) "No logs yet." else logsText}
            """.trimIndent()
            
            aiResponse.value = askGemini(message, systemPrompt)
            isGenerating.value = false
        }
    }

    fun generateQuiz(topic: String) {
        viewModelScope.launch {
            isGenerating.value = true
            val prompt = """
                Generate a 3-question quiz on $topic. 
                Include questions covering conceptual and numerical problems.
                CRITICAL: Use LaTeX notation for all math wrapped in dollar signs (e.g., ${'$'}x^2 + y^2 = r^2${'$'} or ${'$'}${'$'} \int x dx ${'$'}${'$'}).
                Do not include the answers yet.
            """.trimIndent()
            aiResponse.value = askGemini(prompt)
            isGenerating.value = false
        }
    }

    fun generateNotes(topic: String) {
        viewModelScope.launch {
            isGenerating.value = true
            val prompt = """
                Generate concise, bullet-point revision notes for $topic suitable for NDA prep.
                Include key formulas using LaTeX notation wrapped in dollar signs.
            """.trimIndent()
            aiResponse.value = askGemini(prompt)
            isGenerating.value = false
        }
    }

    fun logStudySession(topic: String, durationMinutes: Int, score: Int? = null) {
        viewModelScope.launch {
            studyLogDao.insertLog(StudyLog(topic = topic, durationMinutes = durationMinutes, score = score))
        }
    }
}

class AppViewModelFactory(
    private val studyLogDao: StudyLogDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(studyLogDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
