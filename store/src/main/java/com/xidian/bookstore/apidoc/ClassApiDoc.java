
/**
 * @api {POST} /category/create CreateCategory
 * @apiVersion 1.0.0
 * @apiName CreateCategory
 * @apiGroup CategoryMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/category/create
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "name": "程序设计",
 *   "description": "编程技术类书籍"
 * }
 *
 * @apiParam {String} name the name of category.
 * @apiParam {String} description the description of category.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "categoryId":"1"
 *     }
 * }
 */

/**
 * @api {PUT} /category/update UpdateCategory
 * @apiVersion 1.0.0
 * @apiName UpdateCategory
 * @apiGroup CategoryMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/category/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "categoryId":"1",
 *   "name": "程序设计",
 *   "description": "编程技术类书籍"
 * }
 * @apiParam {String}  categoryId the id of category.
 * @apiParam {String} [name] the name of category.
 * @apiParam {String} [description] the description of category.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /category/list QueryCategories
 * @apiVersion 1.0.0
 * @apiName QueryCategories
 * @apiGroup CategoryMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/category/list
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *         "0":["01","02"],
 *         "1":["11","12"],
 *         "2":["21","22"]
 *     }
 * }
 */

/**
 * @api {POST} /category/delete DeleteCategory
 * @apiVersion 1.0.0
 * @apiName DeleteCategory
 * @apiGroup CategoryMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/category/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "categoryId":["00","10","20"]
 * }
 *
 * @apiParam {Array} categoryId the id of category.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /category/get QueryCategory
 * @apiVersion 1.0.0
 * @apiName QueryCategory
 * @apiGroup CategoryMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/category/get?categoryId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "name":"程序设计",
 *          "description":"编程技术类书籍"
 *     }
 * }
 */


/**
 * @api {POST} /tag/create CreateTag
 * @apiVersion 1.0.0
 * @apiName CreateTag
 * @apiGroup TagMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/tag/create
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "categoryId":"1",
 *   "name": "java",
 *   "description": "java技术类书籍"
 * }
 * @apiParam {String} categoryId the id of category.
 * @apiParam {String} name the name of tag.
 * @apiParam {String} description the description of tag.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "tagId":"1"
 *     }
 * }
 */


/**
 * @api {PUT} /tag/update UpdateTag
 * @apiVersion 1.0.0
 * @apiName UpdateTag
 * @apiGroup TagMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/tag/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "tagId":"1",
 *   "name": "java",
 *   "description": "编程技术类书籍"
 * }
 * @apiParam {String} tagId the id of tag.
 * @apiParam {String} [name] the name of tag.
 * @apiParam {String} [description] the description of tag.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {POST} /tag/delete DeleteTag
 * @apiVersion 1.0.0
 * @apiName DeleteTag
 * @apiGroup TagMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/tag/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *   "tagId":["01","02","03"]
 * }
 *
 * @apiParam {Array} tagId the id of tag.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /tag/get QueryTag
 * @apiVersion 1.0.0
 * @apiName QueryTag
 * @apiGroup TagMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/tag/get?tagId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "name":"java",
 *          "description":"编程技术类书籍"
 *     }
 * }
 */



