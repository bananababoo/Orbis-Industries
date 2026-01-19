package org.orbis.industries

import com.hypixel.hytale.server.core.Message


fun String.message(): Message = Message.raw(this)