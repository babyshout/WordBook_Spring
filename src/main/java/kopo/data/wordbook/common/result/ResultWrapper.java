//package kopo.data.wordbook.common.result;
//
//
//import java.util.Optional;
//
//public class ResultWrapper<T> {
//    private T value;
//    private ResultStatus resultStatus;
//
//    public ResultWrapper(T value, ResultStatus resultStatus) {
//        this.value = value;
//        this.resultStatus = resultStatus;
//    }
//
//    public <T> ResultWrapper(T value, ResultStatus resultStatus) {
//        Optional
//        this.value = value;
//        this.resultStatus = resultStatus;
//    }
//
//    public static <T> ResultWrapper<T> success(T value) {
//
//
//        return this(value, ResultStatus.SUCCESS);
//    }
//
//    private static enum ResultStatus {
//        SUCCESS, FAIL, EXCEPTION
//    }
//}
