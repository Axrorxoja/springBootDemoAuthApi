package com.example.demo.entity

import com.example.demo.common.ErrorCode

class BaseData<T>(val error: ErrorCode? = null,
                  val success: T? = null)