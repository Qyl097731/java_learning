namespace java com.nju

typedef i32 int

struct SharedStruct{
    int key,
    string value
}

service SharedService{
  /**
   * A method definition looks like C code. It has a return type, arguments,
   * and optionally a list of exceptions that it may throw. Note that argument
   * lists and exception lists are specified using the exact same syntax as
   * field lists in struct or exception definitions.
   */

    SharedStruct getStruct(int key)
}