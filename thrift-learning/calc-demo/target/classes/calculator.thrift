include "shared.thrift"

namespace java com.nju

/**
* 定义常量
*/
 const i32 INT32CONSTANTS = 9853
 const map<string,string> MAPCONSTANT = {'hello':'world','goodnight':'moon'}

/**
* 枚举，默认从1开始
*/
enum Operation {
    ADD = 1,
    SUBTRACT = 2,
    MULTIPLY = 3,
    DIVIDE = 4
}

/**
 * optional可以不设置值，没有值就不会序列化
 */
struct Work{
    1: i32 num1 = 0,
    2: i32 num2,
    3: Operation op,
    4: optional string commit,
 }

/**
* Structs can also be exceptions, if they are nasty.
*/
exception InvalidOperation {
    1: i32 what,
    2: string why
}

/**
 * 服务可以只需要一个名字或者可选择地通过extends进行继承
 */
 service Calculator extends shared.SharedService{

  /**
   * A method definition looks like C code. It has a return type, arguments,
   * and optionally a list of exceptions that it may throw. Note that argument
   * lists and exception lists are specified using the exact same syntax as
   * field lists in struct or exception definitions.
   */

   void ping(),

   i32 add(1:i32 num1, 2:i32 num2),

   i32 calculate(1:i32 logid, 2:Work w) throws (1:InvalidOperation ouch),

   /**
    * 单项传送，只能void，不会监听结果
    */
   oneway void zip()
 }
