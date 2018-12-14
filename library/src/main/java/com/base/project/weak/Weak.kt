package com.base.project.weak

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


/**
 *
 *Describe:弱引用封装类 -kotlin
 *
 *Created by zhigang wei
 *on 2018/5/4
 *
 *Company :cpx
 */
class Weak<T : Any>(initializer: () -> T?) {
    var weakReference = WeakReference<T?>(initializer())

    constructor() : this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }

}
