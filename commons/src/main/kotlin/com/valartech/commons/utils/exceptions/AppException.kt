package com.valartech.commons.utils.exceptions

//todo figure how to genericize this

//package com.valartech.commons.utils
//
//import android.app.Application
//import androidx.annotation.StringRes
//import androidx.collection.ArrayMap
//import com.buyceps.kiosk.KioskApplication
//import com.buyceps.kiosk.R
//import com.squareup.moshi.JsonDataException
//import com.squareup.moshi.JsonEncodingException
//import com.valartech.commons.R
//import retrofit2.HttpException
//import java.net.ConnectException
//import java.net.HttpURLConnection
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//import javax.net.ssl.SSLHandshakeException
//
///**
// * Google common error codes: https://developers.google.com/android/reference/com/google/android/gms/common/api/CommonStatusCodes
// * Google sign in error codes: https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignInStatusCodes.html
// */
////http errors
//const val HTTP_401_UNAUTHORIZED = 401
//const val HTTP_403_FORBIDDEN = 403
//const val HTTP_500_INTERNAL_ERROR = 500
//const val HTTP_502_BAD_GATEWAY = 502
//const val USER_LOGIN_FAILED = 451
////general exceptions
//const val UNKNOWN_ERROR = 1000
//const val NO_ACTIVE_CONNECTION = 1001
//const val TIMEOUT = 1002
//const val UNKNOWN_HTTP_EXCEPTION = 1006
//const val MISSING_DATA = 1007
//const val CANT_CONNECT_TO_SERVER = 1008
//const val MISSING_JSON_DATA = 1009
//const val MALFORMED_JSON = 1010
////user errors
//const val INCORRECT_PASSWORD = 1100
//const val INVALID_PHONE_NUMBER = 1101
////google and firebase errors
////const val DEVELOPER_SETUP_ERROR = 1314
//const val NULL_POINTER = 1315
//const val SSL_HANDSHAKE = 1316
//const val PLAY_SERVICES_UPDATE = 1318
//const val FIREBASE_DATABASE_ERROR = 1321
//const val GOOGLE_GENERIC_ERROR = 1329
//const val INVALID_CREDENTIALS = 1330
//const val TOO_MANY_REQUESTS = 1331
//const val USER_COLLISION = 1332
//const val INVALID_OTP = 1333
//const val GENERIC_FIREBASE_ERROR = 1334
//
//class AppException : RuntimeException {
//
//    companion object {
//        private val errorCodeStringMap = ArrayMap<Int, Int>()
//    }
//
//    init {
//        errorCodeStringMap[HTTP_401_UNAUTHORIZED] = R.string.unauthorized_error
//        errorCodeStringMap[UNKNOWN_ERROR] = R.string.unknown_error
//        errorCodeStringMap[NO_ACTIVE_CONNECTION] = R.string.error_no_internet_available
//        errorCodeStringMap[TIMEOUT] = R.string.error_timeout
//        errorCodeStringMap[HTTP_403_FORBIDDEN] = R.string.internal_error
//        errorCodeStringMap[HTTP_500_INTERNAL_ERROR] = R.string.internal_error
//        errorCodeStringMap[HTTP_502_BAD_GATEWAY] = R.string.internal_error
//        errorCodeStringMap[UNKNOWN_HTTP_EXCEPTION] = R.string.internal_error
//        errorCodeStringMap[USER_LOGIN_FAILED] = R.string.internal_error
//        errorCodeStringMap[CANT_CONNECT_TO_SERVER] = R.string.internal_error
////        errorCodeStringMap[DEVELOPER_SETUP_ERROR] = R.string.login_setup_error
//        errorCodeStringMap[NULL_POINTER] = R.string.internal_error
//        errorCodeStringMap[SSL_HANDSHAKE] = R.string.error_no_internet_available
//        errorCodeStringMap[PLAY_SERVICES_UPDATE] = R.string.play_services_update
//        errorCodeStringMap[FIREBASE_DATABASE_ERROR] = R.string.internal_error
//        errorCodeStringMap[MISSING_DATA] = R.string.internal_error
//        errorCodeStringMap[MISSING_JSON_DATA] = R.string.internal_error
//        errorCodeStringMap[MALFORMED_JSON] = R.string.internal_error
//        errorCodeStringMap[GOOGLE_GENERIC_ERROR] = R.string.internal_error
//        errorCodeStringMap[INVALID_CREDENTIALS] = R.string.invalid_credentials
//        errorCodeStringMap[TOO_MANY_REQUESTS] = R.string.internal_error
//        errorCodeStringMap[USER_COLLISION] = R.string.account_already_linked
//        errorCodeStringMap[INVALID_OTP] = R.string.invalid_otp
//        errorCodeStringMap[INCORRECT_PASSWORD] = R.string.login_auth_failure
//        errorCodeStringMap[INVALID_PHONE_NUMBER] = R.string.invalid_phone_number
//    }
//
//
//    var errorCode: Int = 0
//    private var throwable: Throwable? = null
//    private var errorMessage: String? = null
//    private val context: Application
//
//    constructor(errorCode: Int) : super() {
//        context = KioskApplication.INSTANCE
//        this.errorCode = errorCode
//        errorMessage = if (getErrorCodeMessageRes() == null)
//            super.message
//        else
//            context.getString(getErrorCodeMessageRes()!!)
//    }
//
//    constructor(errorCode: Int, message: String) : super() {
//        context = KioskApplication.INSTANCE
//        this.errorCode = errorCode
//        this.errorMessage = message
//    }
//
//    /**
//     * Using type "in Nothing" here because we only read from baseResponse. We can't write to this
//     * object, we're a consumer.
//     */
//    constructor(baseResponse: BaseResponse<in Nothing>) : super() {
//        context = KioskApplication.INSTANCE
//        this.errorCode = baseResponse.statusCode
//        val errorMessageRes = getErrorCodeMessageRes()
//        errorMessage = if (errorMessageRes != null) {
//            //if we know the error code and have our own string defined, use that
//            context.getString(errorMessageRes)
//        } else {
//            //otherwise use the server message
//            baseResponse.message
//        }
//    }
//
//    constructor(throwable: Throwable?) : super(throwable) {
//        context = KioskApplication.INSTANCE
//        this.throwable = throwable
//
//        /**
//         * Higher priority exceptions should be placed higher in the when statement. If an exception
//         * is a subclass of multiple cases, the first case would be selected.
//         */
//        when (throwable) {
//            is AppException -> {
//                this.errorCode = throwable.errorCode
//                this.errorMessage = throwable.errorMessage
//                this.throwable = throwable.throwable
//            }
////            is ApiException -> when (throwable.statusCode) {
////                CommonStatusCodes.NETWORK_ERROR -> errorCode = NO_ACTIVE_CONNECTION
////                CommonStatusCodes.TIMEOUT -> errorCode = TIMEOUT
////                CommonStatusCodes.ERROR -> {
////                    errorCode = UNKNOWN_ERROR
////                    errorCode = DEVELOPER_SETUP_ERROR
////                }
////                CommonStatusCodes.DEVELOPER_ERROR -> errorCode = DEVELOPER_SETUP_ERROR
////            }
//            is HttpException -> errorCode = when (throwable.code()) {
//                HttpURLConnection.HTTP_UNAUTHORIZED -> HTTP_401_UNAUTHORIZED
//                HttpURLConnection.HTTP_FORBIDDEN -> HTTP_403_FORBIDDEN
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> HTTP_500_INTERNAL_ERROR
//                HttpURLConnection.HTTP_BAD_GATEWAY -> HTTP_502_BAD_GATEWAY
//                else -> UNKNOWN_HTTP_EXCEPTION
//            }
////            is FirebaseNetworkException,
//            is UnknownHostException -> errorCode = NO_ACTIVE_CONNECTION
//            is NullPointerException -> //if a stacktrace shows that an API call leads to this,
//                // we have received a null "data" object in the response, despite it being successful
//                // (having "success" in the status).
//                errorCode = NULL_POINTER
//            is JsonDataException -> errorCode = MISSING_JSON_DATA
//            is JsonEncodingException -> errorCode = MISSING_JSON_DATA
//            is SSLHandshakeException -> errorCode = SSL_HANDSHAKE
//            is SocketTimeoutException -> errorCode = TIMEOUT
//            is ConnectException -> errorCode = CANT_CONNECT_TO_SERVER
////            is FirebaseAuthInvalidCredentialsException -> {
////                val firebaseException: FirebaseAuthInvalidCredentialsException = throwable
////                errorCode = when (firebaseException.errorCode) {
////                    "ERROR_WRONG_PASSWORD" -> INCORRECT_PASSWORD
////                    "ERROR_INVALID_VERIFICATION_CODE" -> INVALID_OTP
////                    "ERROR_INVALID_PHONE_NUMBER" -> INVALID_PHONE_NUMBER
////                    else -> {
////                        Timber.e("New errorCode: ${firebaseException.errorCode}")
////                        INVALID_CREDENTIALS
////                    }
////                }
////            }
////            is FirebaseTooManyRequestsException -> errorCode = TOO_MANY_REQUESTS
////            is FirebaseAuthUserCollisionException -> errorCode = USER_COLLISION
////            is FirebaseException -> {
////                val firebaseException: FirebaseException = throwable
////                errorCode = GENERIC_FIREBASE_ERROR
////                //grab the message given by firebase
////                errorMessage = firebaseException.message
////                return
////            }
//            else -> errorCode = UNKNOWN_ERROR
//        }
//
//        /*
//        Priority of messages:
//        1. Our mapped messages
//        2. Server messages
//        3. Default "unknown error" message.
//         */
//        val errorCodeMessageRes = getErrorCodeMessageRes()
//        if (errorCodeMessageRes != null) {
//            errorMessage = context.getString(errorCodeMessageRes)
//        } else if (errorMessage == null) {
//            errorMessage = context.getString(R.string.unknown_error)
//        }
//    }
//
//    override val cause: Throwable?
//        get() = throwable
//
//    @StringRes
//    private fun getErrorCodeMessageRes(): Int? {
//        return errorCodeStringMap[errorCode]
//    }
//
//    override val message: String
//        get() = String.format(
//            context.getString(R.string.error_message_format),
//            errorMessage,
//            errorCode
//        )
//}
