/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.constant

/**
 * Created by Royal on 3/27/21.
 */
object Const {
    val OP_ADD = 1
    val OP_DELETE = 2
    val OP_UPDATE = 3
    val OP_QUERY = 4
    val OP_COLLECT = 5
    val OP_SUB = 1000
}

enum class State(val key: Int) {
    Normal(0),
    Loading(1),
    Empty(4),
    Disable(8),
    Error(16);
}
