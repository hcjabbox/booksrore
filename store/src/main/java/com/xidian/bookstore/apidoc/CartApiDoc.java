
/**
 * @api {POST} /cart/add AddCart
 * @apiVersion 1.0.0
 * @apiName AddCart
 * @apiGroup CartMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/cart/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "bookId": "1",
 *   "amount":"1",
 *   "price":"38.00"，
 *   "userId":"1"
 * }
 *
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} amount the number of book.
 * @apiParam {String} price the price of book.
 * @apiParam {String} userId the id of user.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "cartId":"1"
 *     }
 *
 * }
 */

/**
 * @api {PUT} /cart/update UpdateCart
 * @apiVersion 1.0.0
 * @apiName UpdateCart
 * @apiGroup CartMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/cart/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "amount":"1",
 *   "price":"38.00"，
 *   "cartId":"1"
 * }
 * @apiParam {String} amount the number of book.
 * @apiParam {String} price the price of book.
 * @apiParam {String} cartId the id of shoppingCart.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /cart/delete DeleteCart
 * @apiVersion 1.0.0
 * @apiName DeleteCart
 * @apiGroup CartMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/cart/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "cartId":["1","2"]
 * }
 *
 * @apiParam {Array} cartId the id of book.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /cart/list QueryCart
 * @apiVersion 1.0.0
 * @apiName QueryCart
 * @apiGroup CartMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/cart/list?userId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "carts":[
 *          {
 *              "cartId":"1",
 *              "bookId":"1",
 *              "amount":"2",
 *              "price":"38.00",
 *              "createTime":""
 *          },
 *          {
 *              "cartId":"2",
 *              "bookId":"2",
 *              "amount":"1",
 *              "price":"37.00",
 *              "createTime":""
 *          }
 *         ]
 *     }
 * }
 */

