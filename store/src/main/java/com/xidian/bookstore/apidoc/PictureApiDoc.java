
/**
 * @api {POST} /picture/upload UploadPicture
 * @apiVersion 1.0.0
 * @apiName UploadPicture
 * @apiGroup PictureMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/picture/upload
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {Binary} Request-Body Example:
 * {
 *      "bookId";"1",
 *      "pictureString":["",""]
 * }
 * @apiParam {String} bookId the id of book.
 * @apiParam {Array} pictureString the base64 string of picture.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {POST} /picture/delete DeletePicture
 * @apiVersion 1.0.0
 * @apiName DeletePicture
 * @apiGroup PictureMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/picture/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {Binary} Request-Body Example:
 * {
 *      "pictureId":["1","2"]
 * }
 * @apiParam {Array} pictureId the id string of pictures.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {GET} /picture/list QueryList
 * @apiVersion 1.0.0
 * @apiName QueryList
 * @apiGroup PictureMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/picture/list?bookId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 *     "data":{
 *          "pictureId1":"pictureBase64String1",
 *          "pictureId2":"pictureBase64String2"
 *     }
 * }
 */




