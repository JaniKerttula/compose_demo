package com.example.myapplication

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    // You can add your Service here as constructor
) {
    private val defaultText: String = "Android"

    fun doBackendCallEtc(): String {
        // Here you can invoke API calls and do some other operation
        return defaultText
    }
}