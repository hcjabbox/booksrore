
/**
 * @api {POST} /order/create CreateOrder
 * @apiVersion 1.0.0
 * @apiName CreateOrder
 * @apiGroup OrderMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/order/create
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "bookId": "1",
 *   "amount":"1",
 *   "price":"38.00"，
 *   "addressId":"1",
 *   "userId":"1",
 *   "description":"订单备注等"
 * }
 *
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} amount the number of book.
 * @apiParam {String} price the price of book.
 * @apiParam {String} addressId the id of address.
 * @apiParam {String} userId the id of user.
 * @apiParam {String} [description] the description of order.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *     "data":{
 *         "orderId":"11102"
 *     }
 * }
 */


/**
 * @api {POST} /order/delete DeleteOrder
 * @apiVersion 1.0.0
 * @apiName DeleteOrder
 * @apiGroup OrderMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/order/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "orderId": ["1","2"]
 * }
 *
 * @apiParam {String} orderId the id of order.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {PUT} /order/update UpdateOrderStatus
 * @apiVersion 1.0.0
 * @apiName UpdateOrderStatus
 * @apiGroup OrderMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/order/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "orderId": "1"
 * }
 *
 * @apiParam {String} orderId the id of order.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /order/get QueryOrder
 * @apiVersion 1.0.0
 * @apiName QueryOrder
 * @apiGroup OrderMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/order/get?status=0
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParam {String} status the status of order("0" means all orders,"1" means non-payment,"2" means to send the goods,""3" means to be confirmed,"4" means to be evaluated).
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "orders":[
 *          {
 *              "orderId":"1",
 *              "bookId": "1",
 *              "amount":"1",
 *              "price":"38.00"，
 *              "addressId":"1",
 *              "status":"1",
 *              "completeTime":"",
 *              "createTime":"",
 *              "logisticsId":"1",
 *              "commentId":"2",
 *              "paymentId":"1",
 *              "addressId":"1"
 *           },
 *          {
 *              "orderId":"1",
 *              "bookId": "1",
 *              "amount":"1",
 *              "price":"38.00"，
 *              "addressId":"1",
 *              "status":"1",
 *              "completeTime":"",
 *              "createTime":"",
 *              "logisticsId":"1",
 *              "commentId":"2",
 *              "paymentId":"1",
 *              "addressId":"1"
 *          }
 *         ]
 *     }
 * }
 */

/**
 * @api {POST} /comment/add CreateComment
 * @apiVersion 1.0.0
 * @apiName CreateComment
 * @apiGroup CommentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/comment/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "orderId":"1",
 *      "bookId": "1",
 *      "userId":"1",
 *      "comment:"好"
 * }
 * @apiParam {String} orderId the id of order.
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} userId the id of user.
 * @apiParam {String} comment the comment of book.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POSt} /comment/get QueryComment
 * @apiVersion 1.0.0
 * @apiName QueryComment
 * @apiGroup CommentMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/comment/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *      "bookId":"1",
 *      "userId": "1"
 * }
 * @apiParam {String} [bookId] the id of book.
 * @apiParam {String} [userId] the id of user.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "comments":[
 *          {
 *              "userId":"1",
 *              "bookId":"1",
 *              "comment":"好",
 *              "createTime":""
 *          },
 *          {
 *              "userId":"1",
 *              "bookId":"2",
 *              "comment":"好",
 *              "createTime":""
 *          }
 *         ]
 *     }
 * }
 */