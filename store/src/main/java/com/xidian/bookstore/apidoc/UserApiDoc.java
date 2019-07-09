
/**
 * @api {POST} /user/register Register
 * @apiVersion 1.0.0
 * @apiName Register
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/register
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "email":"abc@163.com",
 *    "password": "123456",
 *    "code":"956142",
 *    "receiveTime":"123456"
 * }
 * @apiParam {String} email the email of user.
 * @apiParam {String} password it will be used for logging.
 * @apiParam {String} code the verificationCode of user.
 * @apiParam {String} receiveTime the receiveTime of verificationCode.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /user/login Login
 * @apiVersion 1.0.0
 * @apiName Login
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/login
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "email": "hcbbox@163.com",
 *    "password": "123456"
 * }
 *
 * @apiParam {String} email the email of user.
 * @apiParam {String} password it will be used for logging.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "userId":"1"
 *     }
 * }
 */

/**
 * @api {PUT} /user/update UpdateInformation
 * @apiVersion 1.0.0
 * @apiName UpdateInformation
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "userId":"1",
 *    "uname": "小王",
 *    "mobile":"13109535503",
 *    "sex":"男",
 *    "image":""
 * }
 * @apiParam {String} userId the id of user.
 * @apiParam {String} [uname] the name of user.
 * @apiParam {String} [mobile] the phone number of user.
 * @apiParam {String} [sex] the gender of user.
 * @apiParam {String} [image] the base64 string of user's image.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {POST} /user/reset  ResetPassword
 * @apiVersion 1.0.0
 * @apiName  ResetPassword
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/reset
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "email":"123@qq.com",
 *    "password":"abcdefg",
 *    "code":"123445",
 *    "receiveTime":"5896631"
 * }
 * @apiParam {String} email the email of user.
 * @apiParam {String} password the password of user.
 * @apiParam {String} code the verificationCode of user.
 * @apiParam {String} receiveTime the receiveTime of verificationCode.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {PUT} /user/modify ModifyPassword
 * @apiVersion 1.0.0
 * @apiName ModifyPassword
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/modify
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "userId":"1",
 *    "oldPassword":"123456",
 *    "newPassword":"456789"
 * }
 * @apiParam {String} userId the id of user.
 * @apiParam {String} oldPassword the old password.
 * @apiParam {String} newPassword the new password.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100"
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /user/get  QueryUser
 * @apiVersion 1.0.0
 * @apiName QueryUser
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/get
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "userId":"1",
 *    "email":"123456"
 * }
 * @apiParam {String} [userId] the id of user.
 * @apiParam {String} [email] the email of user.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *     "data":{
 *          "userId":"2",
 *          "uname": "小李",
 *          "image":"",
 *          "regTime":"",
 *          "mobile":"13109535503",
 *          "email":"abc@163.com",
 *          "sex":"男"
 *    }
 * }
 */


/**
 * @api {POST} /user/resetCode GetResetCode
 * @apiVersion 1.0.0
 * @apiName GetResetCode
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/resetCode
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "email":"1"
 * }
 * @apiParam {String} email the email of user.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /user/registerCode GetRegisterCode
 * @apiVersion 1.0.0
 * @apiName GetRegisterCode
 * @apiGroup UserMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/user/registerCode
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "email":"1"
 * }
 * @apiParam {String} email the email of user.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */
