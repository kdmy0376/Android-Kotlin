package dw.koo.android.homework.musictracklist.utils

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class Reflector<T : Any>(private val mObject: T) {
    @Throws(NoSuchMethodException::class, IllegalAccessException::class, InvocationTargetException::class)
    operator fun invoke(methodName: String?, vararg args: Any?): Any {
        return Companion.invoke(mObject, mObject.javaClass, methodName, *args as Array<out Any>)
    }

    class Field(private val mReceiver: Any?, private val mClass: Class<*>, private val mFieldName: String) {
        private var mDefaultValue: Any? = null
        private fun fallback(defaultValue: Any): Field {
            mDefaultValue = defaultValue
            return this
        }

        private fun fallback(defaultValue: Int): Field {
            mDefaultValue = defaultValue
            return this
        }

        private fun fallback(defaultValue: Boolean): Field {
            mDefaultValue = defaultValue
            return this
        }

        fun asObject(): Any? {
            try {
                val f = mClass.getField(mFieldName)
                return f[mReceiver]
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mDefaultValue
        }

        fun asString(): String? {
            try {
                val f = mClass.getField(mFieldName)
                return f[mReceiver] as String
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mDefaultValue as String?
        }

        fun asBoolean(): Boolean {
            try {
                val f = mClass.getField(mFieldName)
                return f.getBoolean(mReceiver)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return if (mDefaultValue == null) false else mDefaultValue as Boolean
        }

        fun asInt(): Int {
            try {
                val f = mClass.getField(mFieldName)
                return f.getInt(mReceiver)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return if (mDefaultValue == null) 0 else mDefaultValue as Int
        }

    }

    companion object {
        @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
        operator fun invoke(receiver: Any?, cls: Class<*>, methodName: String?, vararg args: Any): Any {
            val method: Method
            if (args.size > 0) {
                val paramTypes: Array<Class<*>?> = arrayOfNulls(args.size)
                var i = 0
                for (arg in args) {
                    paramTypes[i++] = arg.javaClass
                }
                method = cls.getMethod(methodName!!, *paramTypes)
            } else {
                method = cls.getMethod(methodName!!)
            }
            return method.invoke(receiver, *args)
        }

        fun getField(`object`: Any, fieldName: String): Field {
            return Field(`object`, `object`.javaClass, fieldName)
        }

        fun getStaticField(cls: Class<*>, fieldName: String): Field {
            return Field(null, cls, fieldName)
        }
    }

}