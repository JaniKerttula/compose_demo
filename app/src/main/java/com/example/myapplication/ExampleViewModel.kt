package com.example.myapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val exampleRepository: ExampleRepository
): ViewModel() {
    // It is generally good practice not to expose mutable states outside viewModel
    private val _exampleData = MutableStateFlow("")
    val exampleData: StateFlow<String> = _exampleData.asStateFlow()

    // You can do initialization straight to variables, or use the init-function
    init {
        _exampleData.value = exampleRepository.doBackendCallEtc()
    }

    fun setExampleData(text: String) {
        _exampleData.value = text
    }
}