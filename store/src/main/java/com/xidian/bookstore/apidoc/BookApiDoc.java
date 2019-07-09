
/**
 * @api {post} /book/fuzzysearch  SearchBook
 * @apiVersion 1.0.0
 * @apiName SearchBook
 * @apiGroup BookMgmt
 * @apiHeader {String} Accept The accept data type, must is 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "clause":{
 *        "key1": "value1",
 *        "keyn":"valuen"
 *      },
 *    "like":{
 *        "key1": "value1",
 *        "keyn": "valuen"
 *      },
 *    "filter":
 *    {
 *        "page": "0",
 *        "size": "3"
 *
 *    },
 *
 *    "orderBy":
 *    {
 *        "field": "price",
 *        "order": "asc"
 *    }
 *
 * }
 * @apiParam {Object} [clause] Each item is "And" operation().
 * @apiParam {Object} [like] Each item is "OR" fuzzy search operation().
 * @apiParam {Object} filter Filter search Result,we must select filter by paging.
 * @apiParam {String} filter.page Filter search Result by current page.
 * @apiParam {String} filter.size Filter search Result by size of the current page.
 * @apiParam {Object} [orderBy] Sort search Result.
 * @apiParam {String} [orderBy.order]  Sort search Result by DESCending or ASCending("asc" or "desc") default by asc.
 * @apiParam {String} [orderBy.field]  Sort search Result by field (it can be price ,purchaseNumber and browseNumber) default by purchaseNumber.
 *
 *  @apiExample {Text} Request-Example:
 *   http://localhost/api/v1.0/book/fuzzysearch
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *{
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "totalCount": 22,
 *          "totalPage": 3,
 *          "currentCount": 2,
 *          "currentPage": 3,
 *          "books": [
 *          {
 *              "bookId": "1",
 *              "bookName": "MySql必知必会",
 *              "price": "30.00",
 *              "publisherName": "人民邮电出版社",
 *              "author": "Ben Forta",
 *              "inventory": "50"，
 *              "intro": "MySql数据库基础入门必备"，
 *              "image": "",
 *              "browseNumber":"100",
 *              "purchaseNumber":"50"，
 *              "creatime":"2019年4月12日"
 *          },
 *          {
 *              "bookId": "2",
 *              "bookName": "Java程序员面试笔试宝典",
 *              "price": "48.80",
 *              "publisherName": "机械工业出版社",
 *              "author": "何昊 薛鹏 叶向阳",
 *              "inventory": "50"，
 *              "intro": "java面试必备"，
 *              "image": "",
 *              "browseNumber":"100",
 *              "purchaseNumber":"50"，
 *              "creatime":"2019年4月12日"
 *          }
 *      ]
 *    }
 *}
 */

/**
 * @api {POST} /book/upload  UploadBook
 * @apiVersion 1.0.0
 * @apiName UploadBook
 * @apiGroup BookMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/book/upload
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "bookName": "MySql必知必会",
 *   "price": "30.00",
 *   "isbn":"978-7-115-19112-0/TP",
 *   "publisherName": "人民邮电出版社",
 *   "author": "Ben Forta",
 *   "inventory": "50"，
 *   "intro": "MySql数据库基础入门必备"，
 *   "categoryId":"00",
 *   "tagId":"01"
 * }
 *
 * @apiParam {String} bookName the name of book.
 * @apiParam {String} price the price of book.
 * @apiParam {String} isbn the international stand number of book.
 * @apiParam {String} publisherName the name of publisher.
 * @apiParam {String} author the name of author.
 * @apiParam {String} inventory the number of book.
 * @apiParam {String} intro the description of book.
 * @apiParam {String} categoryId the id of book's category.
 * @apiParam {String} tagId the id of book's tag.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "bookId":"1"
 *     }
 * }
 */

/**
 * @api {PUT} /book/update  UpdateBook
 * @apiVersion 1.0.0
 * @apiName UpdateBook
 * @apiGroup BookMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/book/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "bookId":"1",
 *   "bookName": "MySql必知必会",
 *   "price": "30.00",
 *   "isbn":"978-7-115-19112-0/TP",
 *   "publisherName": "人民邮电出版社",
 *   "author": "Ben Forta",
 *   "inventory": "50"，
 *   "intro": "MySql数据库基础入门必备"，
 *   "picture": "",
 *   "categoryId":"00",
 *   "tagId":"01",
 *   "browseNumber":"1",
 *   "purchaseNumber":"1"
 * }
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} [bookName]the name of book.
 * @apiParam {String} [isbn] the international stand number of book.
 * @apiParam {String} [price] the price of book.
 * @apiParam {String} [publisherName] the name of publisher.
 * @apiParam {String} [author] the name of author.
 * @apiParam {String} [inventory] the number of book.
 * @apiParam {String} [intro] the description of book.
 * @apiParam {String} [categoryId] the id of book's category.
 * @apiParam {String} [tagId] the id of book's tag.
 * @apiParam {String} [picture] the photo of book.
 * @apiParam {String} [browseNumber] the number of user browse book.
 * @apiParam {String} [purchaseNumber] the number of user buy book.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /book/delete DeleteBook
 * @apiVersion 1.0.0
 * @apiName DeleteBook
 * @apiGroup BookMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/book/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "bookId":["1","2"]
 * }
 * @apiParam {Array} bookId the id of book.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {POST} /collect/add AddCollect
 * @apiVersion 1.0.0
 * @apiName AddCollect
 * @apiGroup CollectMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/collect/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "userId":"1",
 *    "bookId": "1",
 *    "description":"收藏的描述"
 * }
 *@apiParam {String} userId the id of user.
 * @apiParam {String} bookId the id of book.
 * @apiParam {String} [description] the description of collection.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /collect/get QueryCollect
 * @apiVersion 1.0.0
 * @apiName QueryCollect
 * @apiGroup CollectMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/collect/get?userId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "collects":[
 *          {
 *              "collectId":"1",
 *              "bookId":"1",
 *              "createTime":"",
 *              "description":""
 *          },
 *          {
 *              "collectId":"1",
 *              "bookId":"2",
 *              "createTime":"",
 *              "description":""
 *          },
 *         ]
 *     }
 * }
 */


/**
 * @api {PUT} /collect/update UpdateCollect
 * @apiVersion 1.0.0
 * @apiName UpdateCollect
 * @apiGroup CollectMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/collect/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "collectId":"1",
 *    "description":"收藏的描述"
 * }
 * @apiParam {String} collectId the id of collect.
 * @apiParam {String} description the description of collection.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {POST} /collect/delete DeleteCollect
 * @apiVersion 1.0.0
 * @apiName DeleteCollect
 * @apiGroup CollectMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/collect/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "collectId":["1","2"]
 * }
 * @apiParam {String} collectId the id of collect.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */
