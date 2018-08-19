package com.agaboardi.parchintasca.common.extensions

import android.app.Dialog

fun Dialog.dismissDialog() {
    if (this.isShowing) {
        this.dismiss()
    }
}