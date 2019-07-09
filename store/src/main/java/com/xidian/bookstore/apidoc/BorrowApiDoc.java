
/**
 * @api {POST} /borrow/add AddBorrow
 * @apiVersion 1.0.0
 * @apiName AddBorrow
 * @apiGroup BorrowMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/borrow/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "bookId": "1",
 *   "amount":"1",
 *   "timeLimit":"38.00"，
 *   "userId":"1",
 *   "addressId":"1"
 * }
 *
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} amount the number of book.
 * @apiParam {String} timeLimit the time of borrow book.
 * @apiParam {String} userId the id of user.
 * @apiParam {String} addressId the id of user's address.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /borrow/update updateBorrow
 * @apiVersion 1.0.0
 * @apiName updateBorrow
 * @apiGroup BorrowMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/borrow/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "borrowId":"1",
 *   "damagedDegree":""
 * }
 * @apiParam {String} borrowId the id of borrow book.
 * @apiParam {String} damagedDegree the degree of damaged book(it can choose one between one and ten).
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *
 * }
 */

/**
 * @api {GET} /borrow/get QueryBorrow
 * @apiVersion 1.0.0
 * @apiName QueryBorrow
 * @apiGroup BorrowMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/borrow/get?userId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "borrow":[
 *              {
 *                  "borrowId":"1",
 *                  "bookId":"1",
 *                  "amount":"2",
 *                  "timeLimit:"30",
 *                  "borrowTime":"",
 *                  "addressId":"1",
 *                  "status":"",
 *                  "damagedDegree":"0"
 *              }
 *          ]
 *     }
 *
 * }
 */


/**
 * @api {GET} /borrow/list QueryBorrows
 * @apiVersion 1.0.0
 * @apiName QueryBorrows
 * @apiGroup BorrowMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/borrow/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "borrows":{
 *              "userId1":[
 *                  {
 *                  "borrowId":"1",
 *                  "bookId":"1",
 *                  "amount":"2",
 *                  "timeLimit:"30",
 *                  "borrowTime":"",
 *                  "addressId":"1",
 *                  "status":"",
 *                  "damagedDegree":"0"
 *                  }
 *              ],
 *              "userId2":[
 *                  {
 *                  "borrowId":"1",
 *                  "bookId":"1",
 *                  "amount":"2",
 *                  "timeLimit:"30",
 *                  "borrowTime":"",
 *                  "addressId":"1",
 *                  "status":"",
 *                  "damagedDegree":"0"
 *                  }
 *              ]
 *          }
 *     }
 * }
 */

/**
 * @api {POST} /borrow/delete DeleteBorrow
 * @apiVersion 1.0.0
 * @apiName DeleteBorrow
 * @apiGroup BorrowMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/store/v1.0/borrow/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "borrowId": ["1","2"]
 * }
 * @apiParam {Array} borrowId the id of borrow.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


