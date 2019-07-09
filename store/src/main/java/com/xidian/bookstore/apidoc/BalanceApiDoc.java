
/**
 * @api {PUT} /balance/update  UpdateBalance
 * @apiVersion 1.0.0
 * @apiName UpdateBalance
 * @apiGroup BalanceMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/balance/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "userId":"1",
 *   "money": "200",
 *   "card":"",
 *   "password":""
 * }
 * @apiParam {String} userId the id of user.
 * @apiParam {String} money the number of balance.
 * @apiParam {String} card the number of bank card.
 * @apiParam {String} password the password of bank card.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /balance/get  QueryBalance
 * @apiVersion 1.0.0
 * @apiName QueryBalance
 * @apiGroup BalanceMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/get?userId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "balance":{
 *             "balance":"",
 *             "updateTime":""
 *         }
 *     }
 * }
 */