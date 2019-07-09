
/**
 * @api {POST} /pay/create CreatePayment
 * @apiVersion 1.0.0
 * @apiName CreatePayment
 * @apiGroup PaymentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/pay/buy
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "orderId":"1",
 *      "type": "0"
 * }
 * @apiParam {String} orderId the id of order.
 * @apiParam {String} type the type of payment.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /pay/delete DeletePayment
 * @apiVersion 1.0.0
 * @apiName DeletePayment
 * @apiGroup PaymentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/pay/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "paymentId":["1"]
 * }
 * @apiParam {Array} paymentId the id of payment.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /pay/list QueryList
 * @apiVersion 1.0.0
 * @apiName QueryList
 * @apiGroup PaymentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/pay/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *     "data":{
 *          "paymentId":["1","2"]
 *     }
 * }
 */


/**
 * @api {GET} /pay/get QueryPayment
 * @apiVersion 1.0.0
 * @apiName QueryPayment
 * @apiGroup PaymentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/pay/get?paymentId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *     "data":{
 *          "paymentNumber":"222359644420",
 *          "payTime":""
 *          }
 *     }
 * }
 */
