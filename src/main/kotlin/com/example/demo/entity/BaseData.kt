package com.example.demo.entity

import com.example.demo.common.ErrorCodes

class BaseData<T>(val error: ErrorCodes? = null,
                  val success: T? = null)