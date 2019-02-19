package com.valartech.commons.network.retrofit

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Converts key values in the JSON response to camelCase.
 *
 * Doesn't work.
 */
class CamelCaseConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        //The type which is wrapped
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(type)
            }

            override fun getOwnerType(): Type? {
                return null
            }

            override fun getRawType(): Type {
                return Object::class.java
            }
        }

        //The next converter which needs to handle the response if current was not there.
        //we're using the gson converter that's next here
        val delegate =
            retrofit.nextResponseBodyConverter<ResponseBody>(this, wrappedType, annotations)
        return Converter<ResponseBody, Any> { value: ResponseBody ->
            val jsonObject = JSONObject(value.string())
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                Timber.d("Key: $key")
                if (key[0].isUpperCase()) {
                    key.replaceFirst(key[0], key[0].toLowerCase())
                }
            }
            Timber.d(jsonObject.toString(2))
            delegate.convert(value)
//            val responseObject: Any = delegate.convert(value)
//            if (responseObject is BaseResponse<*>) {
//                if (!responseObject.isSuccessful()) {
//                    throw AppException(responseObject)
//                }
//                //Return BaseResponse object if it is of the type BaseResponse(not a subclass), else return the data.
//                if (type == BaseResponse::class.java) {
//                    responseObject
//                } else {
//                    responseObject.data ?: throw AppException(MISSING_DATA)
//
//                }
//            } else {
//                responseObject
//            }
        }
    }

}
