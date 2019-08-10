package com.arash.github.data.model

import com.arash.github.util.Globals

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

data class IssueFilter(var state: String = State.OPEN.state,var page: Int, val perPage: Int = Globals.PAGE_SIZE, var phrase: String = "")