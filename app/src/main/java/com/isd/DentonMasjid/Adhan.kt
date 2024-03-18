package com.isd.DentonMasjid

import com.isd.DentonMasjid.JsonDataClasses.Data

data class Adhan(val code: Int,
                 val `data`: Data,
                 val status: String
)
