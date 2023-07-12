namespace java com.nju.service

typedef i32 int // we can user typedef to get pretty names for the types we are using

service MultiplicationService
{
    int multiply(1:int n1,2:int n2)
}