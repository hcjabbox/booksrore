
/**
 * @api {POST} /address/add AddAddress
 * @apiVersion 1.0.0
 * @apiName AddAddress
 * @apiGroup AddressMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/address/add
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "userId":"1",
 *    "receiver":"小李",
 *    "mobile":"17782476837",
 *    "province":"陕西",
 *    "city":"西安"，
 *    "region":"长安区"，
 *    "addr":"西安电子科技大学南校区",
 *    "flag":"true"
 * }
 * @apiParam {String} userId the id of user.
 * @apiParam {String} receiver the name of consignee.
 * @apiParam {String} mobile the phone number of consignee.
 * @apiParam {String} province the province of consignee.
 * @apiParam {String} city the city of consignee.
 * @apiParam {String} region the region of consignee.
 * @apiParam {String} addr the detailed street of consignee.
 * @apiParam {String} flag it means the address is default or not(it can be true or flase).
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *           "addressId":"1"
 *     }
 * }
 */


/**
 * @api {POST} /address/delete DeleteAddress
 * @apiVersion 1.0.0
 * @apiName DeleteAddress
 * @apiGroup AddressMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/address/delete
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "addressId":["1","2"]
 * }
 * @apiParam {Array} addressId the id of address.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */

/**
 * @api {PUT} /address/update UpdateAddress
 * @apiVersion 1.0.0
 * @apiName UpdateAddress
 * @apiGroup AddressMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/address/update
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'
 * @apiParamExample {json} Request-Body Example:
 * {
 *    "addressId":1,
 *    "receiver":"小李",
 *    "mobile":"17782476837",
 *    "province":"陕西",
 *    "city":"西安"，
 *    "region":"长安区"，
 *    "addr":"西安电子科技大学南校区",
 *    "flag":"true"
 * }
 * @apiParam {Int} addressId the id of consignee.
 * @apiParam {String} [receiver] the name of consignee.
 * @apiParam {String} [mobile] the phone number of consignee.
 * @apiParam {String} [province] the province of consignee.
 * @apiParam {String} [city] the city of consignee.
 * @apiParam {String} [region] the region of consignee.
 * @apiParam {String} [addr] the detailed street of consignee.
 * @apiParam {String} [flag] it means the address is default or not(it can be true or flase).
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful"
 * }
 */


/**
 * @api {GET} /address/list GetAddress
 * @apiVersion 1.0.0
 * @apiName GetAddress
 * @apiGroup AddressMgmt
 * @apiExample {Text} Request-Example:
 *  http://localhost/api/v1.0/address/get?userId=1
 * @apiHeader {String} Content-Type The accept data type, it must be 'application/json'.
 * @apiSuccessExample Success-Response:
 *   HTTP/1.1 200 OK
 * {
 *     "respCode":"100",
 *     "respMsg":"Successful",
 *     "data":{
 *          "addresses":[
 *          {
 *              "addressId":"1",
 *              "receiver":"小李",
 *              "mobile":"17782476837",
 *              "province":"陕西",
 *              "city":"西安"，
 *              "region":"长安区"，
 *              "addr":"西安电子科技大学南校区",
 *              "flag":"true"
 *          },
 *          {
 *              "addressId":"2",
 *              "receiver":"小明",
 *              "mobile":"17782476837",
 *              "province":"陕西",
 *              "city":"西安"，
 *              "region":"雁塔区"，
 *              "addr":"西安电子科技大学北校区",
 *              "flag":"true"
 *          }
 *          ]
 *     }
 * }
 */
