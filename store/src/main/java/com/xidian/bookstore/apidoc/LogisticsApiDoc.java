
/**
 * @api {POST} /logistics/create CreateLogistics
 * @apiVersion 1.0.0
 * @apiName CreateLogistics
 * @apiGroup LogisticsMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/logistics/create
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "orderId":"1",
 *      "companyName": "顺丰",
 *      "logisticsNumber":"269885214103",
 *      "deliverTime":"",
 *      "description":""
 * }
 * @apiParam {String} orderId the id of order.
 * @apiParam {String} companyName the name of logistics company.
 * @apiParam {String} logisticsNumber the unique id of logistics.
 * @apiParam {String} deliverTime the time of starting to deliver.
 * @apiParam {String} [description] the description of logistics status.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {PUT} /logistics/update UpdateLogistics
 * @apiVersion 1.0.0
 * @apiName UpdateLogistics
 * @apiGroup LogisticsMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/logistics/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "logisticsId":"1",
 *      "companyName": "顺丰",
 *      "logisticsNumber":"269885214103",
 *      "deliverTime":"",
 *      "description":""
 * }
 * @apiParam {String} logisticsId the id of logistics.
 * @apiParam {String} [companyName] the name of logistics company.
 * @apiParam {String} [logisticsNumber] the unique id of logistics.
 * @apiParam {String} [deliverTime] the time of starting to deliver.
 * @apiParam {String} [description] the description of logistics status.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /logistics/delete DeleteLogistics
 * @apiVersion 1.0.0
 * @apiName DeleteLogistics
 * @apiGroup LogisticsMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/logistics/delete?logisticsId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "logisticsId":["1"]
 * }
 * @apiParam {Array} logisticsId the id of logistics.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /logistics/list QueryList
 * @apiVersion 1.0.0
 * @apiName QueryList
 * @apiGroup LogisticsMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/logistics/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "logisticsId":["1","2"]
 *     }
 * }
 */


/**
 * @api {GET} /logistics/get QueryLogistics
 * @apiVersion 1.0.0
 * @apiName QueryLogistics
 * @apiGroup LogisticsMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/logistics/get?logisticsId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "logisticsId":"1",
 *          "companyName": "顺丰",
 *          "logisticsNumber":"269885214103",
 *          "deliverTime":"",
 *          "createTime":"",
 *          "description":""
 *     }
 * }
 */
