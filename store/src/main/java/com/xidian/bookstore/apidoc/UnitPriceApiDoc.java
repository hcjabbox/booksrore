/**
 * @api {POST} /unitPrice/add AddPrice
 * @apiVersion 1.0.0
 * @apiName AddPrice
 * @apiGroup UnitPriceMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/unitPrice/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {Binary} Request-Body Example:
 * {
 *      "price":"1",
 *      "status":"borrow"
 * }
 * @apiParam {String} price the single day's price of borrow book.
 * @apiParam {String} status the status of unitPrice(it can be borrow or damage).
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {PUT} /unitPrice/update UpdatePrice
 * @apiVersion 1.0.0
 * @apiName UpdatePrice
 * @apiGroup UnitPriceMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/unitPrice/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {Binary} Request-Body Example:
 * {
 *      "priceId":"1",
 *      "price":"1"
 * }
 * @apiParam {String} price the id of borrowPrice.
 * @apiParam {String} price the unit price of different status.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /unitPrice/list QueryPrice
 * @apiVersion 1.0.0
 * @apiName QueryPrice
 * @apiGroup UnitPriceMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/unitPrice/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "unitPrice":[
 *              {
 *                  "status":"borrow",
 *                  "price":"1",
 *                  "updateTime":""
 *              },
 *              {
 *                  "status":"damage",
 *                  "price":"1",
 *                  "updateTime":""
 *              }
 *         ]
 *     }
 * }
 */