package com.isd.isd

import com.isd.isd.JsonDataClasses.Data
import com.isd.isd.JsonDataClasses.Meta
import com.isd.isd.JsonDataClasses.Timings
import java.util.Date

data class Adhan(val code: Int,
                 val `data`: Data,
                 val status: String
)
