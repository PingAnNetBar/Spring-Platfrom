package hello.entity;

public abstract class Result<T> {
     String status;
     String msg;
     T data;

    public static Result failure(String msg) {
        return LoginResult.failure(msg);
    }

    protected Result(String status, String msg) {
        this(status, msg, null);
    }

    protected Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

}
